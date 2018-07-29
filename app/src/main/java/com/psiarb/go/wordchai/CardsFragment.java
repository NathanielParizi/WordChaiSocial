package com.psiarb.go.wordchai;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardsFragment extends Fragment {

    CardsFragmentListener cardsFragmentlistener;




    private static TextView targetLang;
    private Button A;


    private static int mStart, mEnd;


    private int ENG_correct[] = new int[4999];
    private int ENG_total[] = new int[4999];

    private int PERS_correct[];
    private int PERS_total[];



    public interface CardsFragmentListener{

        public void passCardData(int[] correct, int[] total);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            cardsFragmentlistener = (CardsFragmentListener) activity;
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public CardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container_a,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cards,container_a,false);

        ENG_total[1] = 100;

        A = (Button) view.findViewById(R.id.Abtn);
        targetLang = (TextView) view.findViewById(R.id.TargetLang);

//*****************************************************PARSE 5000 STRINGS FROM ARRAY LANGUAGE BANK
//        String arr[] = new String[5000];
//        Persian_to_English pers = new Persian_to_English();
//        pers.englishParse(arr);
//
//        System.out.println(arr[0]);





        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ENG_correct[1]++;
                ENG_total[1]++;

                cardsFragmentlistener.passCardData(ENG_correct, ENG_total);

            }
        });


        return view;

    }


    public void setCardDeck(int start, int end){



        this.mStart = start;
        this.mEnd = end;

        targetLang.setText(mStart + " " + mEnd);

    }

    public void retrieveData(int[] correct, int[] total){

        this.ENG_correct = correct;
        this.ENG_total = total;

        System.out.println("*************ENG_correct: " + ENG_correct[1] + " ** and + " +  ENG_total[1] + "******");
    }



}
