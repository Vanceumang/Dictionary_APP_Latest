package com.dictionary.mc.mydic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class DictionaryFragment extends Fragment {


    private String value = "Hello World";
    private FragmentListener listener;
    ArrayAdapter<String> adapter;
    ListView dicList;

    public DictionaryFragment() {
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
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
      /*  Button myButton = (Button)view.findViewById(R.id.myBtn);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null)
                   listener.onItemClick(value);
            }
        });
        */

        dicList = view.findViewById(R.id.dictionaryList);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,getListofWords());
        dicList.setAdapter(adapter);
        dicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(listener!=null)
                    listener.onItemClick(getListofWords() [position]);
            }
        });
    }



    public void filterValue (String value){
        //adapter.getFilter().filter(value);

        int size = adapter.getCount();
        for (int i = 0; i<size;i++){
            if(adapter.getItem(i).startsWith(value)){
                dicList.setSelection(i);
                break;
            }
        }
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
                ,"bag"
                ,"bank"
                ,"beg"
                ,"beginning"
                ,"before"
                ,"cake"
                ,"car"
                ,"dog"
                ,"dough"
                ,"drunk"
                ,"ever"
                ,"pretty"
                ,"King"
                ,"queen"
        };
        return source;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    public void setOnFragmentListener(FragmentListener listener){
        this.listener = listener;
    }

}
