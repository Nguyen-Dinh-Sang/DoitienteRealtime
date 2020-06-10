package com.ptuddd.doitien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ptuddd.doitien.model.CurrencyModel;
import com.ptuddd.doitien.model.RssModel;
import com.ptuddd.doitien.server.RssCurrencyManager;
import com.ptuddd.doitien.server.RssManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String TAG ="nhatnhat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RssManager.getInstance().RssReader("https://usd.fxexchangerate.com/rss.xml", new RssManager.RssManagerListener() {
//            @Override
//            public void onLoadRssSuccess(List<RssModel> rssModels) {
//                for (RssModel s:
//                     rssModels) {
//                    Log.d(TAG, s.getRssTitle()+" : "+s.getRssDescription());
//                }
//            }
//
//            @Override
//            public void onLoadRssFail(String error) {
//                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
//            }
//        });
//        RssCurrencyManager.getInstance().RssReader("https://usd.fxexchangerate.com/rss.xml", new RssCurrencyManager.RssCurrencyManagerListener() {
//            @Override
//            public void onGetCurrencyFromRssSuccess(List<CurrencyModel> currencyModels) {
//                for (CurrencyModel c:
//                     currencyModels) {
//                    Log.d(TAG, c.getName() +" : "+c.getRate());
//                }
//            }
//
//            @Override
//            public void onGetCurrencyFromRssFail(String error) {
//                Log.d(TAG, "onGetCurrencyFromRssFail: "+error);
//
//            }
//        });

        RssCurrencyManager.getInstance().RssReader("https://usd.fxexchangerate.com/rss.xml", new RssManager.RssManagerListener() {
            @Override
            public void onLoadRssSuccess(List<RssModel> rssModels) {
                for (RssModel s:
                     rssModels) {
                    Log.d(TAG, s.getRssTitle()+" : "+s.getRssDescription());
                }
            }

            @Override
            public void onLoadRssFail(String error) {
                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
