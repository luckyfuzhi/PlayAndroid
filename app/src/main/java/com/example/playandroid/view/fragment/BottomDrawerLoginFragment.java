package com.example.playandroid.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.playandroid.R;
import com.example.playandroid.view.activity.LoginActivity;

public class BottomDrawerLoginFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout loginEntry;



    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_drawer_fragment, container, false);
        loginEntry = view.findViewById(R.id.login_entry);
        loginEntry.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
         if (view.getId() == R.id.login_entry) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }
}
