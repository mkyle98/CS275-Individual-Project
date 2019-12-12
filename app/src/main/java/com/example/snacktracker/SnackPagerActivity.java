package com.example.snacktracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class SnackPagerActivity extends AppCompatActivity {
    private static final String EXTRA_SNACK_ID =
            "com.example.snacktracker.snack_id";

    private ViewPager mViewPager;
    private List<Snack> mSnacks;

    public static Intent newIntent(Context packageContext, UUID snackId){
        Intent intent = new Intent(packageContext, SnackPagerActivity.class);
        intent.putExtra(EXTRA_SNACK_ID, snackId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_pager);

        UUID snackId = (UUID) getIntent()
                        .getSerializableExtra(EXTRA_SNACK_ID);

        mViewPager = (ViewPager) findViewById(R.id.snack_view_pager);

        mSnacks = SnackCloset.get(this).getSnacks();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            
            @Override
            public Fragment getItem(int position) {
                Snack snack = mSnacks.get(position);
                return SnackFragment.newInstance(snack.getmId());
            }

            @Override
            public int getCount() {
                return mSnacks.size();
            }
        });
        for (int i = 0; i < mSnacks.size(); i++){
            if (mSnacks.get(i).getmId().equals(snackId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }


}
