package com.account.number.dagger

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * In Dagger, an unscoped component cannot depend on a scoped component. As
 * [AppComponent] is a scoped component (`@Singleton`, we create a custom
 * scope to be used by all teacherFragment components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ActivityScoped
