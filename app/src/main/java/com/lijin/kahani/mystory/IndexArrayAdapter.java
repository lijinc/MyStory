package com.lijin.kahani.mystory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lijin.kahani.backend.indexApi.model.Index;

import java.util.List;

/**
 * Created by LIJIN on 1/4/2015.
 */
public class IndexArrayAdapter extends ArrayAdapter {
    List<Index> indexList;
    Context context;
    public IndexArrayAdapter(Context context,  List<Index> indexList) {
        super(context,R.layout.index_layout,indexList);
        this.context=context;
        this.indexList=indexList;
    }

    @Override
    public int getCount() {
        if(indexList==null){
            return 0;
        }
        return indexList.size();
    }

    public void setList(List<Index> indexList){
        this.indexList=indexList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.index_layout,
                    null, false);
            holder = new ViewHolder();
            holder.indexNoText = (TextView) view.findViewById(R.id.index_no_text);
            holder.indexTitleText = (TextView) view.findViewById(R.id.index_title_text);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Index index = indexList.get(position);
        holder.indexNoText.setText(index.getChapterNo().toString());
        holder.indexTitleText.setText(index.getChapterTitle());

        return view;
    }
    static class ViewHolder {
        TextView indexNoText;
        TextView indexTitleText;
    }
}
