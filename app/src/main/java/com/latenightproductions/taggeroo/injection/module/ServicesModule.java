package com.latenightproductions.taggeroo.injection.module;

import com.latenightproductions.taggeroo.data.service.RealmTagService;
import com.latenightproductions.taggeroo.data.service.TagService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

    @Provides
    TagService provideTagService() {
        return new RealmTagService();
    }

}
