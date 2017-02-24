package com.latenightproductions.taggeroo.ui.tags;

import com.latenightproductions.taggeroo.data.model.Tag;
import com.latenightproductions.taggeroo.data.service.TagService;

import java.util.Date;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class TagsPresenter implements TagsContract.Presenter {

    private TagsContract.View view;

    private TagService tagService;
    private Subscription tagsSubscription;
    private Subscription addTagSubscription;
    private Subscription updateSubscription;
    private Subscription querySubscription;

    public TagsPresenter(TagsContract.View view, TagService tagService) {
        this.view = view;
        this.tagService = tagService;
    }


    @Override
    public void loadAllTags() {
        unsubscribe(tagsSubscription);

        tagsSubscription = tagService.listAllTags()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onTagsLoaded,
                        view::onError);
    }

    @Override
    public void createTag(String title) {
        unsubscribe(addTagSubscription);

        Tag tag = new Tag();
        tag.setId(System.currentTimeMillis());
        tag.setText(title);
        tag.setTimestamp(new Date());

        addTagSubscription = tagService.createTag(tag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onTagCreated,
                        view::onError);
    }

    @Override
    public void updateTag(Tag tag) {
        unsubscribe(updateSubscription);

        updateSubscription = tagService.updateTag(tag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updated -> {},
                        view::onError);
    }

    @Override
    public void searchForTags(Observable<String> query) {
        unsubscribe(querySubscription);

        querySubscription = tagService.searchForTags(query.observeOn(AndroidSchedulers.mainThread()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onTagsLoaded,
                        view::onError);
    }

    private void unsubscribe(Subscription s) {
        if (s != null) {
            s.unsubscribe();
            s = null;
        }
    }

    @Override
    public void onViewDestroyed() {
        unsubscribe(tagsSubscription);
        unsubscribe(addTagSubscription);
        unsubscribe(updateSubscription);
        unsubscribe(querySubscription);
    }
}
