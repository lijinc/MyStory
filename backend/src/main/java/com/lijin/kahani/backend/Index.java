package com.lijin.kahani.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by LIJIN on 1/4/2015.
 */
@Entity
public class Index {
    @Id
    Long id;
    Long bookId;
    String chapterTitle;
    String chapterContent;
    int chapterNo;

    public Index() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public int getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }
}
