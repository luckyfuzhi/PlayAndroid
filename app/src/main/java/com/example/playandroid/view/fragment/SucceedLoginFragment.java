package com.example.playandroid.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.interf.contract.SucceedLoginContract;
import com.example.playandroid.presenter.SucceedLoginPresenter;
import com.example.playandroid.view.activity.BottomActivity;

public class SucceedLoginFragment extends BaseFragment<SucceedLoginPresenter> implements SucceedLoginContract.VP {
    private ImageView succeedLoginSymbol;

    private TextView usernameTv;

    private LinearLayout exitLogin;

    private ImageView exitLoginImg;

    private Button exitLoginBtn;

    private String username;

    private Activity mActivity;



    public SucceedLoginFragment(String username){
        this.username = username;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.succeed_login_fragment, container, false);
        succeedLoginSymbol = view.findViewById(R.id.succeed_login_symbol);
        usernameTv = view.findViewById(R.id.username);
        exitLogin = view.findViewById(R.id.exit_login);
        exitLoginBtn = view.findViewById(R.id.exit_login_button);
        exitLoginImg = view.findViewById(R.id.exit_login_img);
        mActivity = requireActivity();

        usernameTv.setText(username);
        exitLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitLogin();
                Intent intent = new Intent(mActivity, BottomActivity.class);
                intent.putExtra("exitLogin", true);
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
        mPresenter.exitLogin();
    }

    @Override
    public void exitLoginResult() {
    }
}
