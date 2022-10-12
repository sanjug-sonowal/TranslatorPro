package com.translatorpro.protranslator.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.adjust.sdk.Adjust;
import com.testing.protranslator.R;
import com.translatorpro.protranslator.Utils.AppUtils;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private long TOTAL_TIME = 16;
    private long DEEPLINK_TIME = 10;
    ScheduledExecutorService scheduleTaskExecutor;
    int second = 0;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        try {
            Adjust.getGoogleAdId(this, googleAdId -> {
                try {
                    AppUtils.setGPSADId(SplashScreenActivity.this,
                            googleAdId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        startSchedulerTask();
    }

    public void startSchedulerTask() {
        try {
            if (!AppUtils.isSecondTime(SplashScreenActivity.this)) {
                AppUtils.setSecondTime(SplashScreenActivity.this, true);
                scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
                scheduleTaskExecutor.scheduleAtFixedRate(() -> {
                    second = second + 1;
                    Log.e("second =", String.valueOf(second));

                    if (AppUtils.getReferringLink(SplashScreenActivity.this) != null &&
                            !AppUtils.getReferringLink(SplashScreenActivity.this).isEmpty()) {
                        try {
                            scheduleTaskExecutor.shutdown();
                        } catch (Exception ignored) {
                        }

                        startActivity(new Intent(SplashScreenActivity.this,
                                TranslatorFlowActivity.class));
                        finish();

                    } else if (second >= DEEPLINK_TIME) {
                        if (!AppUtils.getAppReceivedAttribution(SplashScreenActivity.this).isEmpty()) {
                            try {
                                scheduleTaskExecutor.shutdown();
                            } catch (Exception ignored) {

                            }
                            if (!AppUtils.getCampaign(SplashScreenActivity.this).isEmpty()
                            && AppUtils.isValidCampaign(SplashScreenActivity.this)){
                                startActivity(new Intent(SplashScreenActivity.this,
                                        TranslatorFlowActivity.class));
                                finish();
                            }else{
                                checkNetConnection();
                            }

                        }else if (second >= TOTAL_TIME) {
                            Log.e("second1 =", String.valueOf(second));
                            try {
                                scheduleTaskExecutor.shutdown();
                            } catch (Exception ignored) {

                            }
                            checkNetConnection();
                        }
                    }

                }, 0, 500, TimeUnit.MILLISECONDS);
            } else {
                if (AppUtils.getReferringLink(SplashScreenActivity.this) != null &&
                        !AppUtils.getReferringLink(SplashScreenActivity.this).isEmpty()) {
                    startActivity(new Intent(SplashScreenActivity.this,
                            TranslatorFlowActivity.class));
                    finish();
                    return;
                }
                if (!AppUtils.getAppReceivedAttribution(SplashScreenActivity.this).isEmpty()
                        && !AppUtils.getCampaign(SplashScreenActivity.this).isEmpty()
                        && AppUtils.isValidCampaign(SplashScreenActivity.this)) {
                    startActivity(new Intent(SplashScreenActivity.this,
                            TranslatorFlowActivity.class));
                    finish();
                    return;
                }
                checkNetConnection();

            }
        }catch (Exception e){
            checkNetConnection();
        }
    }


    private void checkNetConnection() {
        if (!AppUtils.isConnectionAvailable(SplashScreenActivity.this)) {
            dialogNetConnection();
        } else {
            gotoDashboard();
        }
    }

    public void dialogNetConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.AppDialogTheme);
        builder.setTitle(R.string.alertTitle);
        builder.setMessage(R.string.alertMsg);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.alertRetryBtn, (dialog, which) -> {
            dialog.dismiss();
            dialogDismiss();
        });
        builder.show();
    }

    private void dialogDismiss() {
        new Handler(Looper.getMainLooper()).postDelayed(this::checkNetConnection, 230);
    }

    public void gotoDashboard() {
        Intent intent = new Intent(SplashScreenActivity.this,
                DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
