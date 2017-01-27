package com.latenightproductions.taggeroo.ui.tags;

import com.latenightproductions.taggeroo.data.model.Tag;
import com.latenightproductions.taggeroo.data.service.TagService;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class TagsPresenter implements TagsContract.Presenter {

    TagsContract.View view;

    TagService tagService;
    Subscription tagsSubscription;
    Subscription addTagSubscription;

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
    public void createTag(Tag tag) {
        unsubscribe(addTagSubscription);

        addTagSubscription = tagService.createTag(tag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onTagCreated,
                        view::onError);
    }

    @Override
    public void updateTag(Tag tag) {

    }

    private void unsubscribe(Subscription s) {
        if (s != null) {
            s.unsubscribe();
        }
    }

    @Override
    public void onViewDestroyed() {
        unsubscribe(tagsSubscription);
    }
}
