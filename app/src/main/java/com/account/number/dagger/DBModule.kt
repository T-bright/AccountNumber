package com.account.number.dagger

import com.account.number.db.DatabaseOpenHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

    @Singleton
    @Provides
    fun provideDatabaseOpenHelper(): DatabaseOpenHelper {
        return DatabaseOpenHelper()
    }

}