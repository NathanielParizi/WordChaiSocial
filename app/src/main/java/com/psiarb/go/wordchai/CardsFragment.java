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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardsFragment extends Fragment {

    //**************** VOCABULARY FLASH CARDS



    private static String tempLangDeck[] = new String[25];
    private static String tempSourceLangDeck[] = new String[25];
    private static String targetLangDeck[] = new String[5000];
    private static String sourceLangDeck[] = new String[5000];

    private static String target = "";
    private static String source = "";

    //EnglishTree
    private static String englishVocab[] = new String[5000];
    private static String persian_to_englishString[] = new String[5000];
    private static String persian_to_englishStringB[] = new String[264];   //Temp[] to append extra remaining Strings

    //PersianTree
    private static String persianVocab[] = new String[5000];
    private static String persianVocabB[] = new String[1931];
    private static String english_to_persianString[] = new String[5000];




    //****************************************

    private static Random rand = new Random();

    CardsFragmentListener cardsFragmentlistener;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static SharedPreferences settings;
    private static final String PREFS_NAME= "myPrefsFile";


    private static int[] ENG_correct = new int[5000];
    private static int[] ENG_total = new int[5000];

    private static TextView targetLang;
    private Button OK, switchBtn, speakerBtn;
    private Button A, B, C, D;
    private static int correctCard;
    private static boolean pressOK;
    private static boolean reverseShuffle;


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
//        load();
        View view = inflater.inflate(R.layout.fragment_cards,container_a,false);



        OK = (Button) view.findViewById(R.id.OKBtn);
        switchBtn = (Button) view.findViewById(R.id.switchBtn);
        speakerBtn = (Button) view.findViewById(R.id.speakerBtn);
        A = (Button) view.findViewById(R.id.A);
        B = (Button) view.findViewById(R.id.B);
        C = (Button) view.findViewById(R.id.C);
        D = (Button) view.findViewById(R.id.D);

        targetLang = (TextView) view.findViewById(R.id.TargetLang);



        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reverseShuffle = false;
                answerRestart();


                shuffleDeck();





//
//                System.out.println("**************A button called " + ENG_correct[1]);
//
//
//


            }
        });

        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                answerComplete();
                if(correctCard == 0){


                    correctCardSelected();
                }
            }
        });

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answerComplete();
                if(correctCard == 1){

                    correctCardSelected();
                }
            }
        });

        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answerComplete();
                if(correctCard == 2){

                    correctCardSelected();
                }

            }
        });

        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answerComplete();
                if(correctCard == 3){

                    correctCardSelected();
                }

            }
        });



        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                reverseShuffle = true;


                answerRestart();
                shuffleDeckReverse();

            }
        });

        speakerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TTS
                speakIdentifier();

            }
        });

        //Call all vocabulary parsing methods to store card data
        Tree_Cards();

        persian_to_english_Cards();
        targetLang.setText(persian_to_englishString[4999]);
        English_to_persian_Cards();


        shuffleDeck();

        return view;

    }

    private void speakIdentifier() {

        if(target.equals("English") || (source.equals("English") && reverseShuffle) ){   //

            Toast.makeText(getContext(),"SPEAKING ENGLISH",LENGTH_SHORT).show();

        }else{
            Toast.makeText(getContext(),"not speaking english",LENGTH_SHORT).show();
        }
    }


    //initializeSourceDeck

    public void initializeSourceDeck(String target){

      this.target = target;
        System.out.println("*********************SUCCESS " + this.target);

        }


    public void setCardDeck(int start, int end, String target, String source){

        System.out.println("**************setCardDeck CardsFragment called " + ENG_correct[1]);

        this.mStart = start;
        this.mEnd = end;
        this.target = target;
        this.source = source;


        sourceLangDeck = identifySource();
        targetLangDeck = identifyTarget();




        //Extract Strings from Bank of 5000 and load them into a deck of 25
        for(int i = mStart-1, j = 0; i <= mEnd-1; i++, j++){



            try{
                tempLangDeck[j] = targetLangDeck[i];
                tempSourceLangDeck[j] = sourceLangDeck[i];

            }catch(Exception e){
                System.out.println("FINISH THE THAAANG :)");
            }


        }
        int testCheck =0;
        for(String k : tempLangDeck){
            testCheck++;
            System.out.println(testCheck + ": " + k);
        }

        System.out.println("JOEL PARIZI's STRING: " + english_to_persianString[2798]);

        System.out.println("target:" + target + " and source:  " + source + " \t " + mStart + "-" + mEnd) ;

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
//            ENG_correct[i] = Integer.parseInt(items.get(i));
        }

   System.out.println("*********************" + ENG_correct[1]);

    }

    public static void Tree_Cards(){

        //****************Parse 5000 strings from EnglishTree
        EnglishTree eng = new EnglishTree();
        eng.englishParse(englishVocab);

        PersianTree per = new PersianTree();
        per.persianParse(persianVocab);
        PersianTreeB per2 = new PersianTreeB();
        per2.persianParseB(persianVocabB);

        for(int i = 3070 ; i <= 4999; i++){

            persianVocab[i] = persianVocabB[i-3070];
        }


        System.out.println("*****************PERSIAN VOCAB " + persianVocab[4999]);
    }

    public static void English_to_persian_Cards(){

        English_To_Persian engpers = new English_To_Persian();
        engpers.english_to_persianParser(english_to_persianString);
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


    private String[] identifyTarget(){

        //Load deck with this language's unique high frequency vocabulary list

        switch( target ){

            case "English":{
                return englishVocab;

            }
            case "Persian":{
                return persianVocab;
            }
        }

        return null;
    }

    private String[] identifySource() {

        //If user is learning English
        if(target.equals("English")){

            switch( source ){

                case "Persian":{

                    return persian_to_englishString;
                }


                case "English":{
                    return englishVocab;
                }


            }
        }

        //If user is learning Persian
        if(target.equals("Persian")){

            switch( source ){

                case "English":{
                    return english_to_persianString;

                }

                case "Persian":{
                    return persianVocab;
                }


            }
        }




        return null;
    }

    public void shuffleDeck(){

        int randA, randB, randC, randD = 0;
        int randCard =0;


        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<25; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<5; i++) {




            System.out.println(list.get(i));

        }
        randA = list.get(0);
        randB = list.get(1);
        randC = list.get(2);
        randD = list.get(3);
        randCard = list.get(4);



        System.out.println(randA + " " + randB + " " + randC + " " + randD);


        correctCard = rand.nextInt(4);

        switch (correctCard) {
            case 0:
                randA = randCard;
                A.setText(tempSourceLangDeck[randA]);
                break;
            case 1:
                randB = randCard;
                B.setText(tempSourceLangDeck[randB]);
                break;
            case 2:
                randC = randCard;
                C.setText(tempSourceLangDeck[randC]);
                break;
            case 3:
                randD = randCard;
                D.setText(tempSourceLangDeck[randD]);
        }

        targetLang.setText(tempLangDeck[randCard]);
        A.setText(tempSourceLangDeck[randA]);
        B.setText(tempSourceLangDeck[randB]);
        C.setText(tempSourceLangDeck[randC]);
        D.setText(tempSourceLangDeck[randD]);

    }

    private void shuffleDeckReverse() {

        int randA, randB, randC, randD = 0;
        int randCard =0;


        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<25; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<5; i++) {




            System.out.println(list.get(i));

        }
        randA = list.get(0);
        randB = list.get(1);
        randC = list.get(2);
        randD = list.get(3);
        randCard = list.get(4);



        System.out.println(randA + " " + randB + " " + randC + " " + randD);


        correctCard = rand.nextInt(4);

        switch (correctCard) {
            case 0:
                randA = randCard;
                A.setText(tempLangDeck[randA]);
                break;
            case 1:
                randB = randCard;
                B.setText(tempLangDeck[randB]);
                break;
            case 2:
                randC = randCard;
                C.setText(tempLangDeck[randC]);
                break;
            case 3:
                randD = randCard;
                D.setText(tempLangDeck[randD]);
        }

        targetLang.setText(tempSourceLangDeck[randCard]);
        A.setText(tempLangDeck[randA]);
        B.setText(tempLangDeck[randB]);
        C.setText(tempLangDeck[randC]);
        D.setText(tempLangDeck[randD]);
    }



    private void answerComplete(){
        A.setEnabled(false);
        B.setEnabled(false);
        C.setEnabled(false);
        D.setEnabled(false);
    }
    private void answerRestart(){
        A.setEnabled(true);
        B.setEnabled(true);
        C.setEnabled(true);
        D.setEnabled(true);
    }

    public void correctCardSelected(){


        Toast.makeText(getContext(),"Yep thats it",LENGTH_SHORT).show();

        answerComplete();

        pressOK = true;

//        if(target == "Persian"){
//
//            Arrays.asList(tempLangDeck).contains("gets");
//
//            ENG_correct[1]++;
//            ENG_total[]++;



     //   System.out.println(ENG_correct[1]);

        save();
        load();

    }

}