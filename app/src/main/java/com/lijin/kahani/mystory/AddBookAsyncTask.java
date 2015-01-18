package com.lijin.kahani.mystory;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.lijin.kahani.backend.bookApi.BookApi;
import com.lijin.kahani.backend.bookApi.model.Book;
import com.lijin.kahani.backend.bookApi.model.CollectionResponseBook;
import com.lijin.kahani.backend.indexApi.model.Index;

import java.io.IOException;
import java.util.List;

/**
 * Created by LIJIN on 1/17/2015.
 */
public class AddBookAsyncTask extends AsyncTask<Book, Void, Long> {
    AddStoryActivity addStoryActivity;
    private BookApi myApiService = null;
    public AddBookAsyncTask(AddStoryActivity addStoryActivity) {
        this.addStoryActivity=addStoryActivity;
    }

    @Override
    protected Long doInBackground(Book... params) {
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
            if(params.length!=0)
                return myApiService.insert(params[0]).execute().getId();
            else
                return new Long(-1);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Long bookID) {
        super.onPostExecute(bookID);
        addStoryActivity.bookAdded(bookID);
    }
}
