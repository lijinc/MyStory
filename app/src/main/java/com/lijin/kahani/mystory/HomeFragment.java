package com.lijin.kahani.mystory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lijin.kahani.backend.bookApi.model.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LIJIN on 1/4/2015.
 */
public class HomeFragment extends Fragment {
    CardAdapter cardAdapter=null;
    List<Book> bookList;
    // newInstance constructor for creating fragment with arguments
    public static HomeFragment newInstance() {
        HomeFragment fragmentHome = new HomeFragment();
        return fragmentHome;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //Todo implement Swipe to
    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recList = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookList=new ArrayList<Book>();
        cardAdapter=new CardAdapter(bookList,getActivity());
        recList.setLayoutManager(linearLayoutManager);
        recList.setAdapter(cardAdapter);
        new StoryListEndpointsAsyncTask(getActivity(),this,"HOME").execute();
        return view;
    }

    public void onHomeListLoad(List<Book> bookList) {
        cardAdapter.setList(bookList);
    }
}
