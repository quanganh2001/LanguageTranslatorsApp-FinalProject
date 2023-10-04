package com.example.englishhindidictionary.langauge_translator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.englishhindidictionary.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class SplashScreen extends AppCompatActivity {
    static SharedPreferences.Editor editor;
    public static InterstitialAd mInterstitialAdMob;

    private String f212gm;
    int f213i = 0;
    private SharedPreferences f214sp;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_splash);
        setStoreToken(getResources().getString(R.string.app_name));
        mInterstitialAdMob = loadInit();


        goNext();
    }

    private void setStoreToken(String str) {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), 0);
        this.f214sp = sharedPreferences;
        String string = sharedPreferences.getString("gm", "");
        this.f212gm = string;
        if (this.f213i == 0 && string.equals("")) {
            SharedPreferences.Editor edit = this.f214sp.edit();
            edit.putString("gm", "0");
            edit.commit();
            this.f212gm = this.f214sp.getString("gm", "");
        }

    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void goNext() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashScreen.this.startActivity(new Intent(SplashScreen.this, Home.class));
                SplashScreen.this.finish();
            }
        }, 3000);
    }

    private InterstitialAd loadInit() {

        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.admobInit));
        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                Log.e("ads-->>" , "open");
            }
            public void onAdOpened() {
            }

            public void onAdClosed() {
                mInterstitialAdMob = loadInit();
            }
        });
        interstitialAd.loadAd(new AdRequest.Builder().build());
        return interstitialAd;
    }





    public static void showAdmobInterstitial() {

        if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
            mInterstitialAdMob.show();
        }
    }
}
