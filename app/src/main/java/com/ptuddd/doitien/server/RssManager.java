package com.ptuddd.doitien.server;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.ptuddd.doitien.model.RssModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssManager {
    private AsyncRssReader asyncRssReader;

    public void RssReader(String url,RssManagerListener rssManagerListener){
        asyncRssReader = new AsyncRssReader(url,rssManagerListener);
        asyncRssReader.execute();
    }
    private class AsyncRssReader extends AsyncTask<Void, Void, String> {

        private String urlLink;
        private List<RssModel> rssModelList;
        private RssManagerListener rssManagerListener;
        public AsyncRssReader(String urlLink,RssManagerListener rssManagerListener){
            this.urlLink=urlLink;
            this.rssManagerListener =rssManagerListener;
        }



        @Override
        protected String doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return "Empty Link";

            try {
                if(!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                rssModelList = parseXml(inputStream);
                Log.d("nhatnhat", "doInBackground: "+rssModelList.size());
                return "";
            } catch (IOException | XmlPullParserException e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equals("")) {
                rssManagerListener.onLoadRssSuccess(rssModelList);
            } else {
                rssManagerListener.onLoadRssFail(result);
            }
        }
    }
    protected List<RssModel> parseXml(InputStream inputStream) throws XmlPullParserException,
            IOException {
        String title = null;
        String link = null;
        String description = null;
        List<RssModel> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                String name = xmlPullParser.getName();

                if(name == null)
                    continue;
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                }

                if (title != null && link != null && description != null) {
                        RssModel item = new RssModel(title, link, description);
                        items.add(item);
                    title = null;
                    link = null;
                    description = null;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }


    public interface RssManagerListener{
        void onLoadRssSuccess(List<RssModel> rssModels);
        void onLoadRssFail(String error);
    }
}
