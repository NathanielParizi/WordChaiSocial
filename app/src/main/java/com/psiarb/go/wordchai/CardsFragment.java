package com.psiarb.go.wordchai;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardsFragment extends Fragment {

    //**************** VOCABULARY FLASH CARDS

    private static String targetLangDeck[] = new String[25];
    private static String sourceLangDeck[] = new String[5000];

    private static String target = "";
    private static String source = "";

    private static String englishVocab[] = new String[5000];
    private static String persian_to_englishString[] = new String[5000];
    private static String persian_to_englishStringB[] = new String[264];   //Temp[] to append extra remaining Strings


    //****************************************

    CardsFragmentListener cardsFragmentlistener;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static SharedPreferences settings;
    private static final String PREFS_NAME= "myPrefsFile";


    private static int[] ENG_correct = new int[5000];
    private static int[] ENG_total = new int[5000];

    private static TextView targetLang;
    private Button A;

    private static int mStart, mEnd;

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
        load();
        View view = inflater.inflate(R.layout.fragment_cards,container_a,false);



        A = (Button) view.findViewById(R.id.Abtn);

        targetLang = (TextView) view.findViewById(R.id.TargetLang);



        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("**************A button called " + ENG_correct[1]);

                ENG_correct[1]++;
                System.out.println(ENG_correct[1]);

                save();
                load();

            }
        });



        persian_to_english_Cards();
        targetLang.setText(persian_to_englishString[4999]);
        englishTree_Cards();

        return view;

    }

    public void setCardDeck(int start, int end, String target, String source){

        System.out.println("**************setCardDeck CardsFragment called " + ENG_correct[1]);

        this.mStart = start;
        this.mEnd = end;
        this.target = target;
        this.source = source;


        sourceLangDeck = identifyDeck();


        for(int i = mStart-1, j = 0; i <= mEnd-1; i++, j++){

            targetLangDeck[j] = sourceLangDeck[i];
        }
        for(String k : targetLangDeck){
            System.out.println(k);
        }

        System.out.println("target:" + target + " and source:  " + source);

    }

    private String[] identifyDeck() {

        switch( source ){

             case "English":{
            return englishVocab;

             }
             case "Persian":{
                 return persian_to_englishString;
             }
        }

        return null;
    }

    //************************** SharedPreferences Methods for Data Storage
    public void save(){

        final List<String> words = new ArrayList<String>();

        String index = "";
        //Collects all Strings in array

        for(int i = 0; i < ENG_correct.length; i++){

            index = String.valueOf(ENG_correct[i]);
            words.add(index);
        }

        System.out.println("INPREFS" + ENG_correct[1] + "\n" + words.get(1));

        StringBuilder sb = new StringBuilder();
        for(String str : words){
            sb.append(str);
            sb.append(", ");

        }

        settings = this.getActivity().getSharedPreferences("PREFS",  Context.MODE_PRIVATE);   /// POTENTIAL FAULT DETECTED
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("words", sb.toString());
        editor.commit();

    }

    public void load(){

        System.out.println("in load" + ENG_correct[1]);
        //get data back
        SharedPreferences settings =  this.getActivity().getSharedPreferences("PREFS",  Context.MODE_PRIVATE);
        String wordsString = settings.getString("words","");
        String[] itemsWords = wordsString.split(", ");
        List<String> items = new ArrayList<String>();

        for(int i =0; i < itemsWords.length; i++){
            items.add(itemsWords[i]);
        }

        //Convert the Strings into Integers
        for( int i =0; i < 5000; i++){
            ENG_correct[i] = Integer.parseInt(items.get(i));
        }

   System.out.println("*********************" + ENG_correct[1]);

    }

    public static void englishTree_Cards(){

        //****************Parse 5000 strings from EnglishTree
        EnglishTree eng = new EnglishTree();
        eng.englishParse(englishVocab);
        System.out.println("****************************\n" + englishVocab[0]);
    }


    public static void persian_to_english_Cards(){

        //*****************************************************PARSE 5000 STRINGS FROM ARRAY LANGUAGE BANK


        Persian_to_English pers = new Persian_to_English();
        pers.persian_to_English_Parser(persian_to_englishString);

        Persian_to_EnglishB pers2 = new Persian_to_EnglishB();
        pers2.persian_to_English_ParserB(persian_to_englishStringB);

        for(int i = 4737 ; i <= 4999; i++){

            persian_to_englishString[i] = persian_to_englishStringB[i-4737];
        }

        System.out.println("ARRB" + persian_to_englishStringB[262]);
        System.out.println(persian_to_englishString[4999]);


//        Arrays.fill(arr, null);
  //      Arrays.fill(arrB, null);


    }

}