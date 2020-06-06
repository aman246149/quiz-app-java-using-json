package com.example.volleypractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.volleypractice.data.AnswerListAsyncResponse;
import com.example.volleypractice.data.QuestionBank;
import com.example.volleypractice.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  String url="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    private TextView questionTextview;
    private TextView questionCounterTextview;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int currentquestionindex=0;
    private List<Question> questionList;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       trueButton=findViewById(R.id.truebutton);
       falseButton=findViewById(R.id.falsebutton);
       nextButton=findViewById(R.id.nextquestionbutton);
       prevButton=findViewById(R.id.previousquestionbutton);
       questionTextview=findViewById(R.id.questionstextview);
       questionCounterTextview=findViewById(R.id.counter);



       nextButton.setOnClickListener(this);
       prevButton.setOnClickListener(this);
       trueButton.setOnClickListener(this);
       falseButton.setOnClickListener(this);

        System.out.println(trueButton);



      questionList= new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
       @Override
       public void processFinished(ArrayList<Question> questionArrayList) {
//           Log.d("INSIDE", "processFinished: " + questionArrayList) ;
           questionTextview.setText(questionList.get(currentquestionindex).getAnswer());


       }
   });












    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.nextquestionbutton:
                currentquestionindex = (currentquestionindex + 1) % questionList.size();
                updateQuestion();
                break;

            case R.id.previousquestionbutton:
                if (currentquestionindex>0)
                currentquestionindex=(currentquestionindex-1) %questionList.size();
                updateQuestion();;
                break;
            case R.id.truebutton:
                checkAnswer(true);
                updateQuestion();
             break;

            case R.id.falsebutton:
                checkAnswer(false);
                updateQuestion();
                break;






        }
    }
    private void updateQuestion() {
//        String question = questionList.get(currentquestionindex).getAnswer();
        questionTextview.setText(questionList.get(currentquestionindex).getAnswer());
        questionCounterTextview.setText(currentquestionindex + " / " + questionList.size()); // 0 / 234

    }

    private  void checkAnswer(boolean useranswer)

    {
        boolean  answerIsTrue=questionList.get(currentquestionindex).isAnswerTrue();
        int toastMessageId = 0;
        if (useranswer==answerIsTrue)
        {
            fadeView();
            toastMessageId=R.string.correct_answer;


        }
        else{
            shakeanimation();
            toastMessageId=R.string.wrong_answer;
        }

        Toast.makeText(MainActivity.this,toastMessageId,Toast.LENGTH_LONG).show();


    }

    private  void shakeanimation()
    {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        final CardView cardView=findViewById(R.id.quizcard);

        cardView.setAnimation(shake);


        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private  void fadeView()
    {
        final CardView cardView=findViewById(R.id.quizcard);
        AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0.0f);

        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }



}