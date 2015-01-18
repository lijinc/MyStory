package com.lijin.kahani.mystory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ChapterMainActivity extends ActionBarActivity {
    Long bookId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_main);
        Intent intent =getIntent();
        bookId=intent.getLongExtra("BOOKID",new Long(0));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chapter_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_chapter) {
            Intent intent =new Intent(this,AddChapterActivity.class);
            intent.putExtra("BOOKID",bookId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
