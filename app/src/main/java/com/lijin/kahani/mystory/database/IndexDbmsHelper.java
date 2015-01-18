package com.lijin.kahani.mystory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lijin.kahani.backend.indexApi.model.Index;
import com.lijin.kahani.mystory.Utilities.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by vr on 1/18/2015.
 */



public class IndexDbmsHelper extends SQLiteOpenHelper {
    // Table Columns
    public static final String dbChapterId = "_id";
    public static final String dbChapterBookId = "bookId";
    public static final String dbChapterTitle = "chapterTitle";
    public static final String dbChapterContent = "chapterContents";
    public static final String dbChapterNo = "chapterNo";

    private static final String DATABASE_NAME = Constants.DB_BOOK_NAME;
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + Constants.TABLE_CHAPTER + "(" + dbChapterId
            + " INTEGER NOT NULL , " + dbChapterBookId
            + " INTEGER NOT NULL , " + dbChapterTitle
            + " TEXT NOT NULL , " + dbChapterContent
            + " TEXT , " + dbChapterNo +" INTEGER NOT NULL "+ ");";

    public IndexDbmsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(BookDbmsHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_CHAPTER);
        onCreate(db);
    }

    public boolean insertChapter(Index chapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(dbChapterId, chapter.getId());
        contentValues.put(dbChapterBookId, chapter.getBookId());
        contentValues.put(dbChapterTitle, chapter.getChapterTitle());
        contentValues.put(dbChapterContent, chapter.getChapterContent());
        contentValues.put(dbChapterNo, chapter.getChapterNo());

        db.insert(Constants.TABLE_CHAPTER, null, contentValues);
        return true;
    }

    public List<Index> getAllChapters() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Index> chapterList = new ArrayList<Index>();

        Cursor curserAllChapter = db.rawQuery("SELECT * FROM " + Constants.TABLE_CHAPTER + " ORDER BY "+dbChapterNo+"ASC", null);
        curserAllChapter.moveToFirst();

        while (curserAllChapter.isAfterLast() == false) {
            Index chapter = new Index();
            chapter.setId((long)curserAllChapter.getInt(curserAllChapter.getColumnIndex(dbChapterId)));
            chapter.setBookId((long)curserAllChapter.getInt(curserAllChapter.getColumnIndex(dbChapterBookId)));
            chapter.setChapterNo(curserAllChapter.getInt(curserAllChapter.getColumnIndex(dbChapterNo)));
            chapter.setChapterTitle(curserAllChapter.getString(curserAllChapter.getColumnIndex(dbChapterContent)));
            chapter.setChapterContent(curserAllChapter.getString(curserAllChapter.getColumnIndex(dbChapterContent)));
            chapterList.add(chapter);
            curserAllChapter.moveToNext();
        }
        return chapterList;
    }

    public int numberOfChapters() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Constants.TABLE_CHAPTER);
        return numRows;
    }

    public boolean updateBook(Index chapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbChapterId, chapter.getId());
        contentValues.put(dbChapterBookId, chapter.getBookId());
        contentValues.put(dbChapterTitle, chapter.getChapterTitle());
        contentValues.put(dbChapterContent, chapter.getChapterContent());
        contentValues.put(dbChapterNo, chapter.getChapterNo());
        db.update(Constants.TABLE_CHAPTER, contentValues, "_id = ? ", new String[]{chapter.getId() + ""});
        return true;
    }

    public Integer deleteChapter(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Constants.TABLE_CHAPTER,
                "_id = ? ",
                new String[]{Integer.toString(id)});
    }


}