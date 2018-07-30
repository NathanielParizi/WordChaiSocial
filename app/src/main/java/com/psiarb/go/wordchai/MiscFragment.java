package com.psiarb.go.wordchai;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 */
public class MiscFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    private static SharedPreferences settings;
    private static final String PREFS_NAME= "myPrefsFile";

    private static String countParser;
    private static StringBuilder sb = new StringBuilder();

    private static int mStart;
    private static int mEnd;

    private static Boolean[] levelBool = new Boolean[5];

    private Button loadBtn;

    private static String mSource, mTarget;




    MiscFragmentListener miscFragmentListener;
    private Typeface typeface;
    private Spinner spinnerSource, spinnerTarget, spinnerLevel;
    TextView sourceTxt, targetTxt, levelTxt, decksTxt;

    private GridView gridView;
    private int lettersIcon[] = {};    // These are the icons that will populate the gridview  // ("R.drawable.coolpics.png")
    private String letterList[] = {"1-25","25-50","50-75","75-100","100-125","125-150","150-175","175-200","200-225",
                            "225-250","250-275","275-300","300-325","325-350","350-375","375-400","400-425",
                            "425-450","450-475","475-500","500-525","525-550","550-575","575-600","625-550",
                            "550-575","575-600","600-625","625-650","650-675","675-700","700-725","725-750",
                            "750-775","775-800","800-825","825-850","850-875","875-900","900-925","925-950",
                            "950-975","975-1000"};






    public interface MiscFragmentListener{

        public void createCardDeck(int start, int beginning, String target, String source);
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

     //   setSourceTargetLanguages(mTarget);
        save();
        load();


        //Spinners****************************************

        spinnerSource = (Spinner) view.findViewById(R.id.spinnerSource);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity().
                getBaseContext(),R.array.languages, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSource.setAdapter(adapter3);
        spinnerSource.setOnItemSelectedListener(this);


        spinnerTarget = (Spinner) view.findViewById(R.id.spinnerTarget);
        ArrayAdapter<CharSequence> adapter2 =  ArrayAdapter.createFromResource(getActivity().
                getBaseContext(),R.array.languages,  android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTarget.setAdapter(adapter2);
        spinnerTarget.setOnItemSelectedListener(this);


        spinnerLevel = (Spinner) view.findViewById(R.id.spinnerLevel);
        ArrayAdapter<CharSequence> adapter4 =  ArrayAdapter.createFromResource(getActivity().
                getBaseContext(),R.array.Levels,  android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(adapter4);
        spinnerLevel.setOnItemSelectedListener(this);

        //*********************************************************************


        typeface = Typeface.createFromAsset(getContext().getAssets(),"chibi.ttf");
        sourceTxt = (TextView) view.findViewById(R.id.sourceTxt);
        targetTxt = (TextView) view.findViewById(R.id.targetTxt);
        levelTxt = (TextView) view.findViewById(R.id.levelTxt);
        decksTxt = (TextView) view.findViewById(R.id.decksTxt);
        sourceTxt.setTypeface(typeface);
        targetTxt.setTypeface(typeface);
        levelTxt.setTypeface(typeface);
        decksTxt.setTypeface(typeface);
        loadBtn = (Button) view.findViewById(R.id.loadBtn);

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mEnd == 0){
                    Toast.makeText(getContext(),"Please select deck to load",LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"Deck loaded",LENGTH_SHORT).show();
                }
                buttonClicked();
            }
        });


        gridView = (GridView) view.findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(view.getContext(),lettersIcon,letterList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //letterList is basically an array of each deck of cards

                Toast.makeText(getContext(),letterList[i], LENGTH_SHORT).show();
                countParser  = letterList[i];
                System.out.println(countParser.indexOf("-"));

                int dash = countParser.indexOf("-");

                for(int j = 0; j < dash; j++){

                    System.out.println(countParser.charAt(j));
                    sb.append(countParser.charAt(j));

                }


                mStart = Integer.parseInt(sb.toString());

                sb.setLength(0);

                for(int j = dash+1; j < countParser.length(); j++){

                    System.out.println(countParser.charAt(j));
                    sb.append(countParser.charAt(j));

                }


                mEnd = Integer.parseInt(sb.toString());

                sb.setLength(0);
            }
        });




        // Inflate the layout for this fragment
        return view;
    }

    public void buttonClicked(){

        int start = mStart;  //mStart
        int end = mEnd;  //mStart+24

        if(mStart >= 25){
            start+=1;

        }
        if(mEnd >= 50){
            end+=1;

        }
        System.out.println("mSTart: " + mStart);
        System.out.println("mEnd: " + mEnd);



        if(levelBool[1]){
            start+=1000;
            end+=1000;
        }
        if(levelBool[2]){
            start+=2000;
            end+=2000;
        }
        if(levelBool[3]){
            start+=3000;
            end+=3000;
        }
        if(levelBool[4]){
            start+=4000;
            end+=4000;
        }

        String source = mSource;
        String target = mTarget;

        miscFragmentListener.createCardDeck(start, end, target, source);

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();


//        mTarget = text;
//        save();
//        load();

        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.spinnerTarget)
        {
            //do this

            mTarget = text;





        }
        else if(spinner.getId() == R.id.spinnerSource)
        {
            //do this
            mSource = text;

        }
        else if(spinner.getId() == R.id.spinnerLevel)
        {
            //do this
          switch(text){
              case "Advanced":

                 Arrays.fill(levelBool, Boolean.FALSE);
                 levelBool[4] = true;
                 break;
              case "Intermediate-Hi":

                  Arrays.fill(levelBool, Boolean.FALSE);
                  levelBool[3] = true;
                  break;
              case "Intermediate-Med":

                  Arrays.fill(levelBool, Boolean.FALSE);
                  levelBool[2] = true;
                  break;
              case "Intermediate":

                  Arrays.fill(levelBool, Boolean.FALSE);
                  levelBool[1] = true;
                  break;
              case "Elementary":

                  Arrays.fill(levelBool, Boolean.FALSE);
                  levelBool[0] = true;
                  break;
          }




        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setSourceTargetLanguages(String target){

     //   this.source = source;


    //    this.mTarget = target;

        if(target != null) {
            spinnerTarget.setSelection(getTargetIndex());
            System.out.println("**********test**********\n" + target);
        }
        save();
        load();
    }

    private static int getTargetIndex(){

        int index = 0;

      switch(mTarget){
          case "Persian":{
              index = 1;
              break;
          }
          case "English":{
              index = 0;
              break;
          }
          default:{
              index = 0;
              break;
          }


      }
         return index;
    }




    //************************** SharedPreferences Methods for Data Storage
    public void save(){

        settings = this.getActivity().getSharedPreferences("PREFS",  Context.MODE_PRIVATE);   /// POTENTIAL FAULT DETECTED
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("mTarget", mTarget);
        editor.putString("mSource", mSource);
        editor.commit();

    }

    public void load() {

        //get data back
        SharedPreferences settings = this.getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        mTarget = settings.getString("mTarget", "");
        mSource = settings.getString("mSource", "");

        System.out.println("*************Retreived Successfully " + mTarget);


    }


}
