package com.example.snacktracker;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

import static android.widget.CompoundButton.*;

public class SnackFragment extends Fragment {
    private static final String ARG_SNACK_ID = "snack_id";

    private Snack mSnack;
    private EditText mNameField;
    private Button mDateButton;
    private CheckBox mFoundCheckBox;

    public static SnackFragment newInstance(UUID snackId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_SNACK_ID, snackId);

        SnackFragment fragment = new SnackFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID snackId = (UUID) getArguments().getSerializable(ARG_SNACK_ID);
        mSnack = SnackCloset.get(getActivity()).getSnack(snackId);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause(){
        super.onPause();

        SnackCloset.get(getActivity())
                .updateSnack(mSnack);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_snack, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_delete_snack:
                UUID snackId = (UUID) getArguments().getSerializable(ARG_SNACK_ID);
                SnackCloset snackCloset = SnackCloset.get(getActivity());
                mSnack = snackCloset.getSnack(snackId);
                snackCloset.deleteSnack(mSnack);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_snack, container, false);
        mNameField = (EditText) v.findViewById(R.id.snack_name);
        mNameField.setText(mSnack.getmTitle());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSnack.setmTitle(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //blank

            }
        });
        mDateButton = (Button) v.findViewById(R.id.snack_date);
        mDateButton.setText(mSnack.getmDate().toString());
        mDateButton.setEnabled(false);

        mFoundCheckBox = (CheckBox) v.findViewById(R.id.snack_found);
        mFoundCheckBox.setChecked(mSnack.ismFound());
        mFoundCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mSnack.setmFound(b);
            }
        });

        return v;
    }
}
