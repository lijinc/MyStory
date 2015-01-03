package com.lijin.kahani.mystory;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.lijin.kahani.backend.bookApi.BookApi;
import com.lijin.kahani.backend.bookApi.model.Book;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by LIJIN on 1/3/2015.
 */
public class StoryListEndpointsAsyncTask extends AsyncTask<Void, Void, List<Book>> {
    private BookApi myApiService = null;
    private Context context;
    private Fragment fragment;
    private String fragmentTag;
    StoryListEndpointsAsyncTask(Context context, Fragment fragment, String fragmentTag) {
        this.context = context;
        this.fragment=fragment;
        this.fragmentTag=fragmentTag;
    }
    @Override
    protected List<Book> doInBackground(Void... params) {
        if(myApiService == null) { // Only do this once
            BookApi.Builder builder = new BookApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://carbide-pilot-811.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

        try {
            return myApiService.list().execute().getItems();
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);
        if(fragmentTag.equals("HOME")){
            HomeFragment homeFragment = (HomeFragment) fragment;
            homeFragment.onHomeListLoad(books);
        }
    }
}
