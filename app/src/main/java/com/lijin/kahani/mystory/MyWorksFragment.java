package com.lijin.kahani.mystory;





import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lijin.kahani.backend.bookApi.model.Book;
import com.lijin.kahani.mystory.database.BookDbmsHelper;

import java.util.List;

/**
 * Created by vr on 1/18/2015.
 */
public class MyWorksFragment extends Fragment {
    BookDbmsHelper booksDb;
    CardAdapter cardAdapter = null;
    List<Book> bookList;
    Context mActivity;
    MyWorksFragment myWorksFragment;

    public static MyWorksFragment newInstance() {
        MyWorksFragment myWorksFragment = new MyWorksFragment();
        return myWorksFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        myWorksFragment = this;
        booksDb = new BookDbmsHelper(mActivity);
        View view = inflater.inflate(R.layout.fragment_my_works, container, false);
        final RecyclerView recMyWorksList = (RecyclerView) view.findViewById(R.id.my_works_list);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookList = booksDb.getAllBooks();

        cardAdapter = new CardAdapter(bookList, getActivity());
        recMyWorksList.setLayoutManager(linearLayoutManager);
        recMyWorksList.setAdapter(cardAdapter);
        return view;
    }

}

