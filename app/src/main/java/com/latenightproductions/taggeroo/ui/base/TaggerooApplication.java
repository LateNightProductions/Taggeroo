package com.latenightproductions.taggeroo.ui.base;

import android.app.Application;

import com.latenightproductions.taggeroo.injection.component.DaggerServicesComponent;
import com.latenightproductions.taggeroo.injection.component.ServicesComponent;
import com.latenightproductions.taggeroo.injection.module.ServicesModule;

import io.realm.Realm;

public class TaggerooApplication extends Application {

    private ServicesComponent servicesComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        servicesComponent = DaggerServicesComponent
                .builder().servicesModule(new ServicesModule())
                .build();

        Realm.init(this);
    }

    public ServicesComponent getServicesComponent() {
        return servicesComponent;
    }
}
