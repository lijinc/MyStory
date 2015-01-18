package com.lijin.kahani.mystory.database;

/**
 * Created by vr on 1/18/2015.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lijin.kahani.backend.bookApi.model.Book;
import com.lijin.kahani.mystory.Utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class bookDbmsHelper extends SQLiteOpenHelper {


    // Table Columns
    public static final String dbBookId = "_id";
    public static final String dbBookAuthor = "author";
    public static final String dbBookTitle = "title";
    public static final String dbBookDescription = "description";
    public static final String dbBookAvgRating = "AvgRating";
    public static final String dbBookImgUrl = "ImgUrl";
    public static final String dbBookNumberOfViews = "numberOfViews";
    public static final String dbBookDate = "date";

    private static final String DATABASE_NAME = Constants.DB_BOOK_NAME;
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + Constants.TABLE_BOOKS + "(" + dbBookId
            + " INTEGER , " + dbBookAuthor
            + " TEXT NOT NULL , " + dbBookTitle
            + " TEXT NOT NULL , " + dbBookDescription
            + " INTEGER , " + dbBookAvgRating
            + " TEXT NOT NULL , " + dbBookImgUrl
            + " INTEGER , " + dbBookNumberOfViews
            + " TEXT NOT NULL , " + dbBookDate
            + " TEXT NOT NULL  " + ");";

    public bookDbmsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(bookDbmsHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_BOOKS);
        onCreate(db);
    }

    public boolean insertBooks(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(dbBookId, book.getId());
        contentValues.put(dbBookAuthor, book.getAuthor());
        contentValues.put(dbBookTitle, book.getTitle());
        contentValues.put(dbBookDescription, book.getDescription());
        contentValues.put(dbBookAvgRating, book.getAvgRating());
        contentValues.put(dbBookImgUrl, book.getImage());
        contentValues.put(dbBookNumberOfViews, book.getNumberOfViews());
        contentValues.put(dbBookDate, book.getDate());


        db.insert(Constants.TABLE_BOOKS, null, contentValues);
        return true;
    }

    public List<Book> getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Book> booksList = new ArrayList<Book>();

        Cursor CurAllBooks = db.rawQuery("SELECT * FROM " + Constants.TABLE_BOOKS, null);
        CurAllBooks.moveToFirst();
        Book book = new Book();
        while (CurAllBooks.isAfterLast() == false) {
            book.setId((long) (CurAllBooks.getInt(CurAllBooks.getColumnIndex(dbBookId))));
            book.setAuthor(CurAllBooks.getString(CurAllBooks.getColumnIndex(dbBookAuthor)));
            book.setAuthor(CurAllBooks.getString(CurAllBooks.getColumnIndex(dbBookAuthor)));
            //TODO make full
            booksList.add(book);
            CurAllBooks.moveToNext();
        }
        return booksList;
    }

    public int numberOfBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Constants.TABLE_BOOKS);
        return numRows;
    }

    public boolean updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbBookId, book.getId());
        contentValues.put(dbBookAuthor, book.getAuthor());
        contentValues.put(dbBookTitle, book.getTitle());
        contentValues.put(dbBookDescription, book.getDescription());
        contentValues.put(dbBookAvgRating, book.getAvgRating());
        contentValues.put(dbBookImgUrl, book.getImage());
        contentValues.put(dbBookNumberOfViews, book.getNumberOfViews());
        contentValues.put(dbBookDate, book.getDate());
//        TODO
        db.update(Constants.TABLE_BOOKS, contentValues, "_id = ? ", new String[]{book.getId() + ""});
        return true;
    }

    public Integer deleteBook(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Constants.TABLE_BOOKS,
                "_id = ? ",
                new String[]{Integer.toString(id)});
    }


}
