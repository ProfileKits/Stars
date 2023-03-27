package com.predictor.library.net.lib.annotation;

import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_2G;
import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_3G;
import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_4G;
import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_MOBILE;
import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_NO;
import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_UNKNOWN;
import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_WIFI;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IntDef({NETWORK_NO, NETWORK_WIFI, NETWORK_2G, NETWORK_3G, NETWORK_4G, NETWORK_MOBILE, NETWORK_UNKNOWN})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface NetworkType {
}
