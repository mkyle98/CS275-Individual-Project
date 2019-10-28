package com.example.snacktracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SnackListFragment extends Fragment {
    private RecyclerView mSnackRecyclerView;
    private SnackAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_snack_list, container, false);
        mSnackRecyclerView = (RecyclerView) view.findViewById(R.id.snack_recycler_view);
        mSnackRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private void updateUI() {
        SnackCloset snackCloset = SnackCloset.get(getActivity());
        List<Snack> snacks = snackCloset.getSnacks();

        mAdapter = new SnackAdapter(snacks);
        mSnackRecyclerView.setAdapter(mAdapter);
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
            Intent intent = SnackActivity.newIntent(getActivity(), mSnack.getmId());
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
    }

}
