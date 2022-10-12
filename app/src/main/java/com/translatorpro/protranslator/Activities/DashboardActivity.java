package com.translatorpro.protranslator.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.testing.protranslator.R;
import com.translatorpro.protranslator.Utils.AdUtils;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class DashboardActivity extends AppCompatActivity {

    Button translatebtn;

    String GameID = AdUtils.UNITY_GAME_ID;
    String BannerID="Banner_Android";
    Boolean TestMode = true;
    LinearLayout bannerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        translatebtn = (Button) findViewById(R.id.translatebtn);

        if (AdUtils.isConnectionNetworkAvailable(getApplicationContext())){

            bannerAd=findViewById(R.id.layoutBannerAds2);
            UnityAds.initialize(this,GameID,TestMode);
            BannerView view = new BannerView(DashboardActivity.this,BannerID,new UnityBannerSize(320,50));
            view.load();
            bannerAd.addView(view);
        }

        translatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,SelectLanguage.class));
            }
        });
    }
}