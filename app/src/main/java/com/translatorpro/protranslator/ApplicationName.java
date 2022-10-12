package com.translatorpro.protranslator;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.translatorpro.protranslator.Utils.AppUtils;


public class ApplicationName extends Application {

    private static ApplicationName mInstance;
    Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        try {
            context = this;
        } catch (Exception e) {
            e.printStackTrace();
        }

        AdjustConfig config = new AdjustConfig(this, AppUtils.TP_ADJUST_TOKEN,
                AdjustConfig.ENVIRONMENT_PRODUCTION);

        Adjust.addSessionCallbackParameter(AppUtils.TP_USER_UUID,
                AppUtils.AppGenerateUserUUID(getApplicationContext()));
        try {
            FirebaseAnalytics.getInstance(context)
                    .getAppInstanceId()
                    .addOnCompleteListener(task -> {
                        AdjustEvent adjustEvent = new AdjustEvent(AppUtils.TP_FIREBASE_INSTANCE_ID);
                        adjustEvent.addCallbackParameter(AppUtils.TP_EVENT_VALUE,
                                task.getResult());
                        adjustEvent.addCallbackParameter(AppUtils.TP_USER_UUID,
                                AppUtils.AppGenerateUserUUID(getApplicationContext()));
                        Adjust.trackEvent(adjustEvent);

                        Adjust.addSessionCallbackParameter(AppUtils.TP_KEY_FIREBASE_INSTANCE_ID,
                                task.getResult());

                        Adjust.sendFirstPackages();
                        AppUtils.setAppFirebaseInstanceId(context, task.getResult());
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        config.setOnAttributionChangedListener(attribution -> {
            AppUtils.setAppReceivedAttribution(getApplicationContext(),
                    attribution.toString());



            AppUtils.setCampaign(context, attribution.campaign);
        });
        config.setOnDeeplinkResponseListener(deeplink -> {
            AppUtils.setReferringLink(getApplicationContext(), deeplink.toString());
            return false;
        });


        config.setDelayStart(1.5);
        Adjust.onCreate(config);
        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());

    }

    private static final class AdjustLifecycleCallbacks
            implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(@NonNull Activity activity,
                                      @Nullable Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity,
                                                @NonNull Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
        }
    }

    public void onTerminate() {
        super.onTerminate();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static ApplicationName getInstance() {
        return mInstance;
    }


}