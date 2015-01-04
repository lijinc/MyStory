package com.lijin.kahani.mystory;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.lijin.kahani.backend.bookApi.BookApi;
import com.lijin.kahani.backend.bookApi.model.Book;
import com.lijin.kahani.backend.bookApi.model.CollectionResponseBook;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by LIJIN on 1/3/2015.
 */
public class BookListEndpointsAsyncTask extends AsyncTask<String, Void, CollectionResponseBook> {
    private BookApi myApiService = null;
    private Context context;
    private Fragment fragment;
    private String fragmentTag;
    private ProgressDialog dialog;
    BookListEndpointsAsyncTask(Context context, Fragment fragment, String fragmentTag) {
        this.context = context;
        this.fragment=fragment;
        this.fragmentTag=fragmentTag;
    }
    @Override
    protected CollectionResponseBook doInBackground(String... params) {
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
            if(params.length==0)
                return myApiService.list().execute();
            else
                return myApiService.list().setCursor(params[0]).execute();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog= new ProgressDialog(context);
        dialog.setMessage("Please wait");
        dialog.show();
    }

    @Override
    protected void onPostExecute(CollectionResponseBook booksPair) {
        super.onPostExecute(booksPair);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if(fragmentTag.equals("HOME")){
            HomeFragment homeFragment = (HomeFragment) fragment;
            homeFragment.onHomeListLoad(booksPair);
        }
    }
}
