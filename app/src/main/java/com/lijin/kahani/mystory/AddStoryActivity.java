package com.lijin.kahani.mystory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.commonsware.cwac.richedit.RichEditText;
import com.lijin.kahani.backend.bookApi.model.Book;
import com.lijin.kahani.mystory.database.BookDbmsHelper;


public class AddStoryActivity extends ActionBarActivity {
    RichEditText storyEditText;
    EditText titleEditText;
    private BookDbmsHelper booksDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        booksDb = new BookDbmsHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);
        storyEditText=(RichEditText)findViewById(R.id.story_richtext);
        titleEditText=(EditText)findViewById(R.id.edit_title_text);
        storyEditText.enableActionModes(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_story, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        booksDb = new BookDbmsHelper(this);
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            Book book =new Book();
            book.setAuthor("L.I.J.I.N");
            book.setAvgRating(0);
            book.setNumberOfViews(0L);
            book.setImage("url");
            book.setTitle(titleEditText.getText().toString());
            book.setDescription(storyEditText.getText().toString());
            book.setDate(System.currentTimeMillis() + "");
            booksDb.insertBooks(book);
            Intent intent = new Intent(this, AddChapterActivity.class);
            startActivity(intent);
            //Twist
        }

        return super.onOptionsItemSelected(item);
    }

    public void bookAdded(Long bookId){
        Intent i= new Intent(this,ChapterMainActivity.class);
        i.putExtra("BOOKID",bookId);
        startActivity(i);
    }
}
