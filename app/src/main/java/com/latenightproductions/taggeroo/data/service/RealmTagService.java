package com.latenightproductions.taggeroo.data.service;

import com.latenightproductions.taggeroo.data.model.Tag;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.Sort;
import io.realm.exceptions.RealmException;
import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;

public class RealmTagService implements TagService {

    private Realm realm;
    
    public RealmTagService() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<Tag> getTag(long tagId) {
        return realm.where(Tag.class).equalTo("id", tagId).findAll().first().asObservable();
    }

    @Override
    public Observable<List<Tag>> listAllTags() {
        return realm.where(Tag.class)
                .findAll()
                .sort("timestamp", Sort.DESCENDING)
                .asObservable()
                .map(realm::copyFromRealm);
    }

    @Override
    public Observable<List<Tag>> searchForTags(Observable<String> query) {
        return query
                .distinctUntilChanged()
                .switchMap(queryString ->
                        realm.where(Tag.class)
                                .contains("text", queryString, Case.INSENSITIVE)
                                .findAll()
                                .sort("timestamp", Sort.DESCENDING)
                                .asObservable().map(realm::copyFromRealm));
    }

    @Override
    public Observable<Tag> createTag(Tag tag) {
        return doAsync(tag, realm::copyToRealmOrUpdate);
    }

    @Override
    public Observable<Tag> updateTag(Tag tag) {
        return doAsync(tag, realm::copyToRealmOrUpdate);
    }

    @Override
    public Observable<Void> deleteTag(Tag tag) {
        return doAsync(tag, o ->
                realm.where(Tag.class)
                        .equalTo("id", o.getId())
                        .findAll()
                        .first()
                        .deleteFromRealm())
                .map(delete -> null);
    }



    private Observable<Tag> doAsync(Tag object, Action1<Tag> action1) {
        return Observable.fromEmitter(e -> {
            realm.beginTransaction();
            try {
                action1.call(object);
                realm.commitTransaction();
            } catch (RuntimeException exception) {
                realm.cancelTransaction();
                e.onError(new RealmException("Error during transaction.", exception));
                return;
            } catch (Error error) {
                realm.cancelTransaction();
                e.onError(error);
                return;
            }
            e.onNext(object);
            e.onCompleted();
        },
                Emitter.BackpressureMode.BUFFER);
    }

}
