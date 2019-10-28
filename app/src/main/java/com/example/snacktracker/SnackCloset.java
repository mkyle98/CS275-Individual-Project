package com.example.snacktracker;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SnackCloset {
    private static SnackCloset sSnackCloset;

    private List<Snack> mSnacks;



    public static SnackCloset get(Context context){
        if (sSnackCloset == null){
            sSnackCloset = new SnackCloset(context);
        }
        return sSnackCloset;
    }

    private SnackCloset(Context context){
        mSnacks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Snack snack = new Snack();
            snack.setmTitle("Snack #" + i);
            snack.setmFound(i % 2 == 0);
            mSnacks.add(snack);
        }
    }

    public List<Snack> getSnacks(){
        return mSnacks;
    }

    public Snack getSnack(UUID id){
        for (Snack snack : mSnacks){
            if(snack.getmId().equals(id)){
                return snack;
            }
        }
        return null;
    }

}
