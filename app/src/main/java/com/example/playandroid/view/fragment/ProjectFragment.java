package com.example.playandroid.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.interf.contract.ProjectContract;
import com.example.playandroid.entity.ProjectType;
import com.example.playandroid.presenter.ProjectPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectContract.VP {

    private final static int SET_PROJECT_TYPE = 0;

    private FragmentActivity mActivity;

    private TabLayout projectTypeTl;

    private ProgressDialog progressDialog;

    private ViewPager projectArticleVp;

    private List<ProjectType> mProjectTypeList = new ArrayList<>();
    private List<ProjectContentFragment> mProjectList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = requireActivity();
    }

    @Override
    public int getFragmentId() {
        return R.layout.project_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("正在努力加载");
        progressDialog.setCancelable(false);
        progressDialog.show();
        projectTypeTl = mActivity.findViewById(R.id.project_type_tl);
        projectArticleVp = mActivity.findViewById(R.id.project_content_vp);
        requestProjectTypeData();
    }


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SET_PROJECT_TYPE:
                    for (int i = 0; i < mProjectTypeList.size(); i++) {
                        projectTypeTl.addTab(projectTypeTl.newTab().setText(mProjectTypeList.get(i).getName()));
                        mProjectList.add(new ProjectContentFragment(mProjectTypeList.get(i).getId()));
                    }
                    projectArticleVp.setAdapter(new FragmentPagerAdapter(mActivity.getSupportFragmentManager(),
                            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                        @NonNull
                        @Override
                        public Fragment getItem(int position) {
                            return mProjectList.get(position);
                        }

                        @Override
                        public int getCount() {
                            return mProjectList.size();
                        }


                        @Nullable
                        @Override
                        public CharSequence getPageTitle(int position) {
                            return mProjectTypeList.get(position).getName();
                        }
                    });


                    //设置TabLayout和ViewPager的联动
                    projectTypeTl.setupWithViewPager(projectArticleVp);

                    progressDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public ProjectPresenter getPresenterInstance() {
        return new ProjectPresenter();
    }

    @Override
    public void requestProjectTypeData() {
        mPresenter.requestProjectTypeData();
    }

    @Override
    public void requestProjectTypeDataResult(List<ProjectType> projectTypeList) {
        this.mProjectTypeList = projectTypeList;
        Message message = new Message();
        message.what = SET_PROJECT_TYPE;
        handler.sendMessage(message);
    }

}
