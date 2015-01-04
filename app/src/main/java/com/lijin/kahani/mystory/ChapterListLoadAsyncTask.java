package com.lijin.kahani.mystory;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.lijin.kahani.backend.indexApi.IndexApi;
import com.lijin.kahani.backend.indexApi.model.Index;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by LIJIN on 1/4/2015.
 */
public class ChapterListLoadAsyncTask extends AsyncTask<Long, Void, List<Index>> {
    IndexViewActivity indexViewActivity;
    IndexApi myApiService;
    public ChapterListLoadAsyncTask(IndexViewActivity indexViewActivity) {
        this.indexViewActivity=indexViewActivity;
    }

    @Override
    protected List<Index> doInBackground(Long... params) {
        if(myApiService == null) { // Only do this once
            IndexApi.Builder builder = new IndexApi.Builder(AndroidHttp.newCompatibleTransport(),
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
                return null;
            else
                return myApiService.listChapters(params[0]).setLimit(200).execute().getItems();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Index> indexes) {
        super.onPostExecute(indexes);
        indexViewActivity.onChaptersRecived(indexes);
    }
}
