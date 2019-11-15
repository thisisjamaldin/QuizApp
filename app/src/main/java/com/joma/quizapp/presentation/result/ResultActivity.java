package com.joma.quizapp.presentation.result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.joma.quizapp.R;

public class ResultActivity extends AppCompatActivity {

    private ResultViewModel resultViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultViewModel = ViewModelProviders.of(this).get(ResultViewModel.class);

    }

}