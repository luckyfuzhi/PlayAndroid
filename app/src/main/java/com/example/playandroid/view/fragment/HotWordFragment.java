package com.example.playandroid.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.playandroid.R;

import java.util.ArrayList;
import java.util.List;

public class HotWordFragment extends Fragment {

    private TextView hotWordText;

    private List<String> hotWordList = new ArrayList<>();

    public HotWordFragment(List<String> hotWordList) {
        this.hotWordList = hotWordList;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hot_word_fragment, container, false);
        hotWordText = view.findViewById(R.id.hot_word_textview);
        showHotWord();
        return view;
    }


    /**
     * 展示热词
     */
    public void showHotWord() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String hotWord : hotWordList) {
            stringBuilder.append("<").append(hotWord).append(">   ");
        }
        hotWordText.setText(stringBuilder.toString());
    }


}
