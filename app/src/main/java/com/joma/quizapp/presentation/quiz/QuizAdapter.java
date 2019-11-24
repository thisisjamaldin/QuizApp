package com.joma.quizapp.presentation.quiz;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joma.quizapp.R;
import com.joma.quizapp.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    private ArrayList<Question> mQuestions = new ArrayList<>();
    private QuizAdapter.Listener mListener;

    public QuizAdapter(QuizAdapter.Listener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mQuestions.get(position));
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Listener mListener;
        private TextView questionText;
        private Button button;
        private Button button1;
        private Button button2;
        private Button button3;
        private Button buttonYes;
        private Button buttonNo;
        private View multiLayout;
        private View booleanLayout;

        public ViewHolder(@NonNull View itemView, Listener listener) {
            super(itemView);
            mListener = listener;
            questionText = itemView.findViewById(R.id.item_question);
            button = itemView.findViewById(R.id.item_answer);
            button1 = itemView.findViewById(R.id.item_answer_1);
            button2 = itemView.findViewById(R.id.item_answer_2);
            button3 = itemView.findViewById(R.id.item_answer_3);
            buttonYes = itemView.findViewById(R.id.item_answer_yes);
            buttonNo = itemView.findViewById(R.id.item_answer_no);
            multiLayout = itemView.findViewById(R.id.item_multiple_answer_layout);
            booleanLayout = itemView.findViewById(R.id.item_boolean_answer_layout);
        }

        public void bind(Question question) {
            questionText.setText(Html.fromHtml(question.getQuestion()));
            if (question.getType().equals("multiple")) {
                multiLayout.setVisibility(View.VISIBLE);
                booleanLayout.setVisibility(View.GONE);
                button.setText(Html.fromHtml(question.getAnswers().get(0)));
                button1.setText(Html.fromHtml(question.getAnswers().get(1)));
                button2.setText(Html.fromHtml(question.getAnswers().get(2)));
                button3.setText(Html.fromHtml(question.getAnswers().get(3)));
            }
            if (question.getType().equals("boolean")) {
                booleanLayout.setVisibility(View.VISIBLE);
                multiLayout.setVisibility(View.GONE);
            }
        }
    }

    public void setQuestions(List<Question> qestions) {
        mQuestions.clear();
        mQuestions.addAll(qestions);
        notifyDataSetChanged();
    }
    public List<Question> getQuestions() {
        return mQuestions;
    }

    public interface Listener {
        void onAnswerClick(int questionPosition, int answerPPosition);
    }
}
