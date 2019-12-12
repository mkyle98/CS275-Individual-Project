package com.example.snacktracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SnackListFragment extends Fragment {
    private RecyclerView mSnackRecyclerView;
    private SnackAdapter mAdapter;
    private boolean mSubtitleVisible;

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_snack_list, container, false);
        mSnackRecyclerView = (RecyclerView) view.findViewById(R.id.snack_recycler_view);
        mSnackRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();

        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_snack_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.new_snack:
                Snack snack = new Snack();
                SnackCloset.get(getActivity()).addSnack(snack);
                Intent intent = SnackPagerActivity
                        .newIntent(getActivity(), snack.getmId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateSubtitle(){
        SnackCloset snackCloset = SnackCloset.get(getActivity());
        int snackCount = snackCloset.getSnacks().size();
        String subtitle = getString(R.string.subtitle_format, snackCount);
        if(!mSubtitleVisible){
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        SnackCloset snackCloset = SnackCloset.get(getActivity());
        List<Snack> snacks = snackCloset.getSnacks();
        if (mAdapter == null){
            mAdapter = new SnackAdapter(snacks);
            mSnackRecyclerView.setAdapter(mAdapter);
        } else{
            mAdapter.setSnacks(snacks);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class SnackHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private TextView mNameTitleView;
        private TextView mDateTextView;
        private ImageView mFoundImageView;
        private Snack mSnack;

        public void bind (Snack snack){
            mSnack = snack;
            mNameTitleView.setText(mSnack.getmTitle());
            mDateTextView.setText(mSnack.getmDate().toString());
            mFoundImageView.setVisibility(snack.ismFound() ? View.VISIBLE : View.GONE);
        }


        public SnackHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_snack, parent, false));
            itemView.setOnClickListener(this);
            mNameTitleView = (TextView) itemView.findViewById(R.id.snack_name);
            mDateTextView = (TextView) itemView.findViewById(R.id.snack_date);
            mFoundImageView = (ImageView) itemView.findViewById(R.id.snack_found);
        }
        @Override
        public void onClick(View view){
            Intent intent = SnackPagerActivity.newIntent(getActivity(), mSnack.getmId());
            startActivity(intent);
        }


    }



    private class SnackAdapter extends RecyclerView.Adapter<SnackHolder>{

        private List<Snack> mSnacks;

        public SnackAdapter(List<Snack> snacks){
            mSnacks = snacks;
        }


        @NonNull
        @Override
        public SnackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SnackHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SnackHolder holder, int position) {
            Snack snack = mSnacks.get(position);
            holder.bind(snack);
        }

        @Override
        public int getItemCount() {
            return mSnacks.size();
        }

        public void setSnacks(List<Snack> snacks){
            mSnacks = snacks;
        }
    }

}
