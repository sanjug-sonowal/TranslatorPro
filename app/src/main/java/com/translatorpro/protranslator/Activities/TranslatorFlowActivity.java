package com.translatorpro.protranslator.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.testing.protranslator.R;
import com.translatorpro.protranslator.Utils.AppUtils;


public class TranslatorFlowActivity extends AppCompatActivity {

    private WebView proflow;
    LinearLayout layoutNotFound;
    Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_flow);
        initGlobalView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initGlobalView() {
        proflow = findViewById(R.id.proflow);
        layoutNotFound = findViewById(R.id.layoutNotFound);
        proflow.getSettings().setJavaScriptEnabled(true);
        proflow.getSettings().setUseWideViewPort(true);
        proflow.getSettings().setLoadWithOverviewMode(true);
        proflow.getSettings().setDomStorageEnabled(true);
        proflow.getSettings().setPluginState(WebSettings.PluginState.ON);

        CookieManager.getInstance().setAcceptCookie(false);

        proflow.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        proflow.getSettings().setAppCacheEnabled(false);
        proflow.clearHistory();
        proflow.clearCache(true);

        proflow.clearFormData();
        proflow.getSettings().setSavePassword(false);

        proflow.getSettings().setSaveFormData(false);
        proflow.setWebChromeClient(new WebChromeClient());
        proflow.setVisibility(View.VISIBLE);

        proflow.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                String url = request.getUrl().toString();

                if (!url.startsWith("http")) {
                    Intent i1 = new Intent(TranslatorFlowActivity.this, DashboardActivity.class);
                    startActivity(i1);
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        finish();
                        return;
                    } catch (Exception ignored) {
                        finish();
                        return;
                    }
                }

                super.onReceivedError(view, request, error);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        checkConnectionView();
    }

    public void viewLayoutNotFound() {
        layoutNotFound.setVisibility(View.VISIBLE);
        btnRetry = findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(view -> {
            layoutNotFound.setVisibility(View.GONE);
            checkConnectionView();
        });
    }

    protected void checkConnectionView() {
        if (AppUtils.isConnectionAvailable(this)) {
            proflow.loadUrl(AppUtils.AppGeneratePremiumLink(TranslatorFlowActivity.this));
        } else {
            viewLayoutNotFound();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        proflow.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        proflow.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        proflow.loadUrl("about:blank");
    }

    @Override
    public void onBackPressed() {
    }
}