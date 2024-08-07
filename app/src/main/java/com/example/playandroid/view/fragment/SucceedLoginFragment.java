package com.example.playandroid.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.playandroid.MyApplication;
import com.example.playandroid.R;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.interf.contract.SucceedLoginContract;
import com.example.playandroid.presenter.SucceedLoginPresenter;
import com.example.playandroid.util.RetrofitUtil;
import com.example.playandroid.view.activity.BottomActivity;
import com.example.playandroid.view.activity.CollectionActivity;

import java.util.HashSet;

public class SucceedLoginFragment extends BaseFragment<SucceedLoginPresenter> implements SucceedLoginContract.VP {
    private ImageView succeedLoginSymbol;

    private TextView usernameTv;

    private LinearLayout exitLogin;

    private LinearLayout collection;

    private ImageView exitLoginImg;

    private TextView exitLoginBtn;

    private String username;

    private Activity mActivity;



    public SucceedLoginFragment(String username){
        this.username = username;
    }


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.succeed_login_fragment, container, false);

        succeedLoginSymbol = view.findViewById(R.id.succeed_login_symbol);
        usernameTv = view.findViewById(R.id.username);
        exitLogin = view.findViewById(R.id.exit_login);
        exitLoginBtn = view.findViewById(R.id.exit_login_text);
        exitLoginImg = view.findViewById(R.id.exit_login_img);
        collection = view.findViewById(R.id.collection);
        mActivity = requireActivity();

        usernameTv.setText(username);
        exitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitLogin();
                Intent intent = new Intent(mActivity, BottomActivity.class);
                intent.putExtra("exitLogin", true);
                startActivity(intent);
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, CollectionActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public int getFragmentId() {
        return R.layout.succeed_login_fragment;
    }


    @Override
    public void initData() {
    }

    @Override
    public void initView() {
    }


    @Override
    public SucceedLoginPresenter getPresenterInstance() {
        return new SucceedLoginPresenter();
    }

    @Override
    public void exitLogin() {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("cookies_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mPresenter.exitLogin();
    }

    @Override
    public void exitLoginResult() {
    }
}
