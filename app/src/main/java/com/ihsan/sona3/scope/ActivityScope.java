/*
 * Last modified 8/7/21 9:16 PM
 */

package com.ihsan.sona3.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Ahmed Shehatah
 * ~~~~~~~ This Code Is For Testing ~~~~~~~~
 */


@Scope
@Documented
@Retention(RUNTIME)
public @interface ActivityScope {
}
