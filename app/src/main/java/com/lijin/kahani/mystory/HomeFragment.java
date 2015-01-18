package com.lijin.kahani.mystory;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lijin.kahani.backend.bookApi.model.Book;
import com.lijin.kahani.backend.bookApi.model.CollectionResponseBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LIJIN on 1/4/2015.
 */
public class HomeFragment extends Fragment {
    CardAdapter cardAdapter=null;
    List<Book> bookList;
    String nextPageToken=null;
    Boolean endOfList=false;
    private boolean loading = true;
    Context mActivity;
    HomeFragment homeFragment;
    int pastVisiblesItems;
    int visibleItemCount;
    int totalItemCount;

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
        mActivity=getActivity();
        homeFragment=this;
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recList = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookList=new ArrayList<Book>();
        cardAdapter=new CardAdapter(bookList,getActivity());
        recList.setLayoutManager(linearLayoutManager);
        recList.setAdapter(cardAdapter);
        //Todo update only on scrolldown
    /*    recList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                            loading = false;
                            new BookListEndpointsAsyncTask(mActivity, homeFragment, "HOME").execute(nextPageToken);
                        }
                    }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                loading=true;
            }
        });*/

        new BookListEndpointsAsyncTask(getActivity(),this,"HOME").execute();
        return view;
    }

    public void onHomeListLoad(CollectionResponseBook bookListPair) {
        if(bookListPair!=null) {
            if(bookListPair.getItems()!=null){
                for (Book book:bookListPair.getItems()){
                    bookList.add(book);
                }
                cardAdapter.notifyDataSetChanged();
            }
            if(bookListPair.getNextPageToken()==nextPageToken){
                endOfList=true;
            }
            else {
                nextPageToken=bookListPair.getNextPageToken();
            }
        }

    }
}
