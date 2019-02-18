package com.example.leonardo.alkanzatest


import android.app.Application
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import io.realm.RealmConfiguration


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()


        Realm.init(applicationContext)

        // create realm configuration
        val defaultConfig = RealmConfiguration.Builder()
            .schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .name("alkanza")
            .build()
        Realm.setDefaultConfiguration(defaultConfig)

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build());
    }



}