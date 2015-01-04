package com.lijin.kahani.mystory;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.lijin.kahani.backend.indexApi.model.Index;

import java.util.ArrayList;
import java.util.List;


public class IndexViewActivity extends ActionBarActivity {
    ListView indexListView;
    Long bookId;
    List<Index> indexList;
    IndexArrayAdapter indexArrayAdapter;
    String bookTitle;
    TextView storyText;
    Context context;
    private ViewFlipper mViewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_view);
        Intent intent=getIntent();
        context=this;
        bookId=intent.getLongExtra("BOOKID",new Long(0));
        bookTitle=intent.getStringExtra("BOOKTITLE");
        mViewFlipper=(ViewFlipper)findViewById(R.id.story_view_flipper);
        if( android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                    | View.INVISIBLE);
        }
        new ChapterListLoadAsyncTask(this).execute(bookId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index_view, menu);
        return true;
    }

    public void onChaptersRecived(List<Index> indexList){
        for (Index index:indexList) {
            String chapter=index.getChapterContent();
            if(chapter==null){
                continue;
            }
            int noOfPages=chapter.length()/1000;
            for(int j=0;j<=noOfPages;j++){
                LayoutInflater inflater = (LayoutInflater) this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.story_flipper_container, null);
                mViewFlipper.addView(view);
                storyText = (TextView) view.findViewById(R.id.story_text_view);
                if(chapter.length()>=1000){
                    storyText.setText(chapter.substring(0,999));
                    chapter=chapter.substring(1000);
                }
                else {
                    storyText.setText(chapter);
                }

            }
        }
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(4000);
        mViewFlipper.startFlipping();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
