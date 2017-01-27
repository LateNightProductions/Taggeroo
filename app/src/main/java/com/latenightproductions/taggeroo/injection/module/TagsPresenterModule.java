package com.latenightproductions.taggeroo.injection.module;

import com.latenightproductions.taggeroo.data.service.TagService;
import com.latenightproductions.taggeroo.ui.tags.TagsContract;
import com.latenightproductions.taggeroo.ui.tags.TagsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class TagsPresenterModule {

    private TagsContract.View view;

    public TagsPresenterModule(TagsContract.View view) {
        this.view = view;
    }

    @Provides
    TagsContract.Presenter providePresenter(TagService tagService) {
        return new TagsPresenter(view, tagService);
    }


}
