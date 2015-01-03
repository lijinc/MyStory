package com.lijin.kahani.backend;

/**
 * Created by LIJIN on 1/3/2015.
 */

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
/**
 * Created by LIJIN on 1/3/2015.
 */
@Entity
public class Book {
    @Id
    Long id;
    String author;
    String title;
    int avgRating;
    String image;
    long numberOfViews;
    public Book() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public long getNumberofviews() {
        return numberOfViews;
    }

    public void setNumberofviews(long numberOfViews) {
        this.numberOfViews = numberOfViews;
    }
}