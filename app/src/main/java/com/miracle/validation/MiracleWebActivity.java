package com.miracle.validation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MiracleWebActivity extends AppCompatActivity implements MiracleWebView.Listener, SwipeRefreshLayout.OnRefreshListener {

    private static final String URL = "https://www.google.co.in/";
    private MiracleWebView mWebView;
    private ConnectivityManager.NetworkCallback networkCallback;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advance_web);

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(MiracleWebActivity.this);

        mWebView = findViewById(R.id.ll_webview);
        mWebView.setListener(this, this);
        mWebView.setGeolocationEnabled(false);
        mWebView.setMixedContentAllowed(false);
        mWebView.setCookiesEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("MIRACLE", "Finished loading: ");
//                Toast.makeText(MiracleWebActivity.this, "Finished loading", Toast.LENGTH_SHORT).show();
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e("MIRACLE", "title: " + title);
//                Toast.makeText(MiracleWebActivity.this, title, Toast.LENGTH_SHORT).show();
            }

        });
        mWebView.addHttpHeader("X-Requested-With", "");

        if (isNetworkAvailable()) {
            mWebView.loadUrl(URL);
        } else {
            mWebView.loadUrl("file:///android_asset/offline.html");
        }

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                runOnUiThread(() -> {
                    if (!mWebView.getUrl().startsWith("file:///android_asset")) {
                        mWebView.loadUrl(URL);
                    }
                });
            }

            @Override
            public void onLost(Network network) {
                runOnUiThread(() -> {
                    if (mWebView.getUrl() != null) {
                        mWebView.loadUrl("file:///android_asset/offline.html");
                    }
                });
            }
        };
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    public void onRefresh() {
        if (isNetworkAvailable()) {
            mWebView.loadUrl(URL);
        } else {
            mWebView.loadUrl("file:///android_asset/offline.html");
        }
        refreshLayout.setRefreshing(false);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network nw = connectivityManager.getActiveNetwork();
        if (nw == null) return false;
        NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
        return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN));
    }

    @Override
    protected void onDestroy() {
        if (networkCallback != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
        mWebView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return;
        }
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit!!")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MiracleWebActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        mWebView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPageFinished(String url) {
        mWebView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        Log.e("MIRACLE", "onPageError(errorCode = " + errorCode + ",  description = " + description + ",  failingUrl = " + failingUrl + ")");
//        Toast.makeText(MiracleWebActivity.this, "onPageError(errorCode = " + errorCode + ",  description = " + description + ",  failingUrl = " + failingUrl + ")", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        Log.e("MIRACLE", "onDownloadRequested(url = " + url + ",  suggestedFilename = " + suggestedFilename + ",  mimeType = " + mimeType + ",  contentLength = " + contentLength + ",  contentDisposition = " + contentDisposition + ",  userAgent = " + userAgent + ")");
//        Toast.makeText(MiracleWebActivity.this, "onDownloadRequested(url = " + url + ",  suggestedFilename = " + suggestedFilename + ",  mimeType = " + mimeType + ",  contentLength = " + contentLength + ",  contentDisposition = " + contentDisposition + ",  userAgent = " + userAgent + ")", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onExternalPageRequest(String url) {
        Log.e("MIRACLE", "onExternalPageRequest(url = " + url + ")");
//        Toast.makeText(MiracleWebActivity.this, "onExternalPageRequest(url = " + url + ")", Toast.LENGTH_SHORT).show();
    }

}
