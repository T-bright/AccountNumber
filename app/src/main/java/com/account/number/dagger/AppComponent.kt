package com.account.number.dagger

import android.app.Application
import com.account.number.MyApplication
import com.account.number.db.DBManager
import com.account.number.db.DatabaseOpenHelper
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import dagger.android.AndroidInjectionModule


@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ActivityModule::class,
        DBModule::class,
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class]
)
public interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    public interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}