package com.ptuddd.doitien.server;

import com.ptuddd.doitien.model.CurrencyModel;
import com.ptuddd.doitien.model.RssModel;

import java.util.ArrayList;
import java.util.List;

public class RssCurrencyManager extends RssManager {

    public static RssCurrencyManager instance;
    public static RssCurrencyManager getInstance() {
        if(instance==null)
            instance= new RssCurrencyManager();
        return instance;
    }

    public void getCurrencysFromRss(String url, final RssCurrencyManagerListener rssCurrencyManagerListener){
        final List<CurrencyModel> currencyModels = new ArrayList<>();
        RssReader(url, new RssManagerListener() {
            @Override
            public void onLoadRssSuccess(List<RssModel> rssModels) {
                for (RssModel rss:rssModels) {
                    String des = rss.getRssDescription();
                    String temp =des.substring(des.indexOf('='));
                    String[] subs = temp.split(" ");
                    Double rate = Double.parseDouble(subs[0]);
                    String currencyName ="Trống";
                    for (int i = 1; i <subs.length ; i++) {
                        currencyName+=subs[i];
                    }
                    currencyModels.add(new CurrencyModel(currencyName,rate));
                }
                rssCurrencyManagerListener.onGetCurrencyFromRssSuccess(currencyModels);
            }

            @Override
            public void onLoadRssFail(String error) {
                rssCurrencyManagerListener.onGetCurrencyFromRssFail(error);
            }
        });
    }
    public interface RssCurrencyManagerListener{
        void onGetCurrencyFromRssSuccess(List<CurrencyModel> currencyModels);
        void onGetCurrencyFromRssFail(String error);
    }
}
