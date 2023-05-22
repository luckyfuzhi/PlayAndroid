package com.example.playandroid.view.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.interf.contract.RegisterContract;
import com.example.playandroid.presenter.RegisterPresenter;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.VP {

    private final static int PSW_LENGTH_LACK = -1;
    private final static int REGISTER_SUCCESS = 1;
    private final static int USERNAME_REGISTERED = -2;

    private EditText usernameEdit;

    private EditText passwordEdit;

    private EditText ensurePswEdit;

    private Button registerButton;

    private Button backButton;

    private Activity mActivity = this;

    private Map<String, String> paramMap = new HashMap<>();

    @Override
    public void initView() {
        usernameEdit = findViewById(R.id.register_username_edit);
        passwordEdit = findViewById(R.id.register_password_edit);
        ensurePswEdit = findViewById(R.id.ensure_password_edit);
        registerButton = findViewById(R.id.register_activity_button);
        backButton = findViewById(R.id.register_back);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        registerButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.register_activity;
    }

    @Override
    public RegisterPresenter getPresenterInstance() {
        return new RegisterPresenter();
    }

    @Override
    public void destroy() {

    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register_back) {
            finish();
        } else if (view.getId() == R.id.register_activity_button) {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            String ensurePassword = ensurePswEdit.getText().toString();

            paramMap.put("username", username);
            paramMap.put("password", password);
            paramMap.put("repassword", ensurePassword);

            if (usernameEdit.length() == 0 || passwordEdit.length() == 0 || ensurePswEdit.length() == 0) {
                Toast.makeText(mActivity, "账号或者密码或者确认密码栏不能为空！", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(ensurePassword)) {
                Toast.makeText(mActivity, "两次密码输入应该一致！", Toast.LENGTH_SHORT).show();
            } else {
                sendRegisterData(paramMap);
            }

        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case PSW_LENGTH_LACK:
                    Toast.makeText(mActivity, "密码长度必须大于6位！", Toast.LENGTH_SHORT).show();
                    break;
                case USERNAME_REGISTERED:
                    Toast.makeText(mActivity, "用户名已被注册！", Toast.LENGTH_SHORT).show();
                    break;
                case REGISTER_SUCCESS:
                    Toast.makeText(mActivity, "注册成功！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void sendRegisterData(Map<String, String> paramMap) {
        mPresenter.sendRegisterData(paramMap);
    }

    @Override
    public void responseRegisterResult(int registerResult) {
        Message message = new Message();
        message.what = registerResult;
        handler.sendMessage(message);
    }
}
