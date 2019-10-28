package com.example.snacktracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class SnackActivity extends SingleFragmentActivity {

    public static final String EXTRA_SNACK_ID =
            "com.example.snacktracker.snack_id";

    public static Intent newIntent(Context packageContext, UUID snackId){
        Intent intent = new Intent(packageContext, SnackActivity.class);
        intent.putExtra(EXTRA_SNACK_ID, snackId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID snackId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_SNACK_ID);
        return SnackFragment.newInstance(snackId);
    }
}
