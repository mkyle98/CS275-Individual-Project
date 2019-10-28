package com.example.snacktracker;

import androidx.fragment.app.Fragment;

public class SnackListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new SnackListFragment();
    }

}
