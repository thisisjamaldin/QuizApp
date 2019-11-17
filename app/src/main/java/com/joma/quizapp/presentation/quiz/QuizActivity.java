package com.joma.quizapp.presentation.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joma.quizapp.App;
import com.joma.quizapp.R;
import com.joma.quizapp.data.IQuizRepository;
import com.joma.quizapp.model.Category;
import com.joma.quizapp.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity {

    private QuizViewModel quizViewModel;
    private RecyclerView recyclerView;
    private QuizAdapter adapter;
    private ImageView backImage;
    private TextView categoryText;
    private List<Question> questionList = new ArrayList<>();
    private int amount;
    private Integer category;
    private String difficulty;
    private ProgressBar loading;
    private static String EXTRA_AMOUNT = "amount";
    private static String EXTRA_CATEGORY = "category";
    private static String EXTRA_DIFFICULTY = "difficulty";

    public static void start(Context context, int amount, int category, String difficulty) {
        Intent intent = new Intent(context, QuizActivity.class);
        intent.putExtra(EXTRA_AMOUNT, amount);
        intent.putExtra(EXTRA_CATEGORY, category);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);

        loading = findViewById(R.id.quiz_loading_progress);
        categoryText = findViewById(R.id.quiz_category);
        backImage = findViewById(R.id.quiz_back_image);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        amount = getIntent().getIntExtra(EXTRA_AMOUNT, 1);

        category = getIntent().getIntExtra(EXTRA_CATEGORY, 0) + 8;
        if (category == 8) category = null;

        difficulty = getIntent().getStringExtra(EXTRA_DIFFICULTY).toLowerCase();
        if (difficulty.equals("any")) difficulty = null;

        getQuestions();

        recyclerView = findViewById(R.id.quiz_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new QuizAdapter(questionList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
    }

    private void getQuestions() {
        App.quizRepository.getQuiz(new IQuizRepository.OnQuizCallBack() {
            @Override
            public void onSuccess(List<Question> questions) {
                questionList.addAll(questions);
                adapter.notifyDataSetChanged();
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("-----------", e.getMessage());
            }
        }, amount, category, difficulty);


        App.quizRepository.getCategory(new IQuizRepository.OnCategoryCallBack() {
            @Override
            public void onSuccess(List<Category> categories) {
                Log.d("---------", categories + "");
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("-----------", e.getMessage());
            }
        });

        App.quizRepository.getTotalQuestion(new IQuizRepository.OnTotalQuestionCallBack() {
            @Override
            public void onSuccess(TotalQuestion overall, Map<Integer, TotalQuestion> totalQuestions) {
                Log.d("---------", overall + "\n---------" + totalQuestions);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("-----------", e.getMessage());
            }
        });
    }
}