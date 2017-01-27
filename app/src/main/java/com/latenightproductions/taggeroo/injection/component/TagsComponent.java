package com.latenightproductions.taggeroo.injection.component;

import com.latenightproductions.taggeroo.injection.module.TagsPresenterModule;
import com.latenightproductions.taggeroo.ui.tags.TagsActivity;

import dagger.Component;

@Component(modules = TagsPresenterModule.class,
        dependencies = ServicesComponent.class)
public interface TagsComponent {
    void inject(TagsActivity view);
}
