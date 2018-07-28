package com.psiarb.go.wordchai;

public class CardDeckBank {


    private int ENG_correct[];
    private int ENG_total[];

    private int PERS_correct[];
    private int PERS_total[];


    public int[] getENG_correct() {
        return ENG_correct;
    }

    public void setENG_correct(int[] ENG_correct) {
        this.ENG_correct = ENG_correct;
    }

    public int[] getENG_total() {
        return ENG_total;
    }

    public void setENG_total(int[] ENG_total) {
        this.ENG_total = ENG_total;
    }

    public int[] getPERS_correct() {
        return PERS_correct;
    }

    public void setPERS_correct(int[] PERS_correct) {
        this.PERS_correct = PERS_correct;
    }

    public int[] getPERS_total() {
        return PERS_total;
    }

    public void setPERS_total(int[] PERS_total) {
        this.PERS_total = PERS_total;
    }



    public CardDeckBank(){}


}
