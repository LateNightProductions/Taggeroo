package com.latenightproductions.taggeroo.ui.tags;

import com.latenightproductions.taggeroo.data.model.Tag;
import com.latenightproductions.taggeroo.data.service.TagService;

import java.util.Date;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class TagsPresenter implements TagsContract.Presenter {

    private TagsContract.View view;

    private TagService tagService;
    private Subscription tagsSubscription;
    private Subscription addTagSubscription;
    private Subscription updateSubscription;

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
