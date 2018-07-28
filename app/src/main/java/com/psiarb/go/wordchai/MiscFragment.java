package com.psiarb.go.wordchai;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class MiscFragment extends Fragment {

    EditText strEditText;


    MiscFragmentListener miscFragmentListener;




    public interface MiscFragmentListener{

        public void createCardDeck(int start, int beginning);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            miscFragmentListener = (MiscFragmentListener) activity;
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public MiscFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerB,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_misc,containerB,false);

        strEditText = (EditText) view.findViewById(R.id.strEditText);
        final Button btn = (Button) view.findViewById(R.id.btn);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            buttonClicked(view);

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void buttonClicked(View view){


        int start = 7;
        int end = 10;

        miscFragmentListener.createCardDeck(start, end);

    }

}
