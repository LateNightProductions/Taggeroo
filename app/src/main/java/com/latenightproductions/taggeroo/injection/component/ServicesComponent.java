package com.latenightproductions.taggeroo.injection.component;

import com.latenightproductions.taggeroo.data.service.TagService;
import com.latenightproductions.taggeroo.injection.module.ServicesModule;

import dagger.Component;

@Component(modules = ServicesModule.class)
public interface ServicesComponent {
    TagService tagService();
}
