package com.predictor.library.net.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnNetworkTypeChangedTo {
    @NetworkType
    int type();
    boolean notifyOnAppStart() default true;
}
