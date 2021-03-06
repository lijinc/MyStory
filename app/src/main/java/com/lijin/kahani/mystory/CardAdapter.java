package com.lijin.kahani.mystory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lijin.kahani.backend.bookApi.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LIJIN on 1/4/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.StoryViewHolder> {
    List<Book> bookList;
    public Context context;

    public CardAdapter(List<Book> bookList,Context context) {
        this.bookList = bookList;
        this.context=context;
    }

    @Override
    public int getItemCount()
    {
        if(bookList==null){
            return 0;
        }
        return bookList.size();
    }


    @Override
    public void onBindViewHolder(StoryViewHolder storyViewHolder, final int i) {
        final Book book = bookList.get(i);
        storyViewHolder.vTitle.setText(book.getTitle());
        storyViewHolder.vAuthor.setText(book.getAuthor());
        storyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,IndexViewActivity.class);
                i.putExtra("BOOKTITLE",book.getTitle());
                i.putExtra("BOOKID", book.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new StoryViewHolder(itemView);
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected TextView vAuthor;
        public StoryViewHolder(View v) {
            super(v);
            vTitle =  (TextView) v.findViewById(R.id.card_title_text);
            vAuthor = (TextView)  v.findViewById(R.id.card_author_text);
        }
    }

}
