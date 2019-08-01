package com.dictionary.mc.mydic;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;


public class BookmarkFragment extends Fragment {
    private FragmentListener listener;
    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        ListView bookmarkList = (ListView) view.findViewById(R.id.bookmarkList);
        final BookmarkAdapter adapter = new BookmarkAdapter(getActivity(),getListofWords());
        bookmarkList.setAdapter(adapter);

        adapter.setOnItemClick(new ListitemListener() {
            @Override
            public void onItemClick(int position) {
                if (listener!=null)
                    listener.onItemClick(String.valueOf(adapter.getItem(position)));
            }
        });

        adapter.setOnItemDeleteClick(new ListitemListener() {
            @Override
            public void onItemClick(int position) {
                String value = String.valueOf(adapter.getItem(position));
                adapter.removeItem(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void setOnFragmentListener(FragmentListener listener){
        this.listener = listener;
    }

    String [] getListofWords(){
        String [] source = new String []{
                "a"
                ,"abandon"
                ,"abandoned"
                ,"ability"
                ,"able'"
                ,"able"
                ,"about"
                ,"above"
                ,"abroad"
                ,"absence"
                ,"absent'"
                ,"absolute"
                ,"absolutely"
                ,"absorb"
                ,"abuse"
                ,"academic"
                ,"accent"
                ,"accept"
                ,"acceptable"
                ,"access"
                ,"accident"
                ,"accidental"
                ,"accidentally"
                ,"accommodation"
                ,"accompany"
        };
        return source;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_click, menu);

    }
}


