package com.predictor.library.utils;

import com.tencent.bugly.crashreport.CrashReport;

public class CNBugly {
    public static void testCrash(){
        CrashReport.testJavaCrash();
    }
}
