package com.dictionary.mc.mydic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vmang on 6/2/19.
 */

public class BookmarkAdapter extends BaseAdapter {
    private ListitemListener listenerBtnDelete;
    private ListitemListener listener;
    Context mContext;
    ArrayList<String>mSource;
    public BookmarkAdapter(Context context, String [] source){

        this.mSource = new ArrayList<>(Arrays.asList(source));
        this.mContext = context;
    }


    @Override
    public int getCount(){

        return mSource.size();
    }
    @Override
    public Object getItem(int i){

        return mSource.get(i);
    }

    @Override
    public  long getItemId(int i){

        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.bookmark_layout_item, viewGroup, false);
            viewHolder.textView = view.findViewById(R.id.tvword); // if error cast (TextView)
            viewHolder.btnDelete = view.findViewById(R.id.btnDelete); // if error cast (ImageView)

            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(mSource.get(i));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener !=null)
                listener.onItemClick(i);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listenerBtnDelete !=null)
                    listenerBtnDelete.onItemClick(i);
            }
        });
        return view;
    }

    public void removeItem(int position){
        mSource.remove(position);
    }

    class ViewHolder {

        TextView textView;
        ImageView btnDelete;
    }

    public void setOnItemClick(ListitemListener listitemListener){
        this.listener = listitemListener;
    }
    public void setOnItemDeleteClick(ListitemListener listitemListener){
        this.listenerBtnDelete = listitemListener;
    }


}
