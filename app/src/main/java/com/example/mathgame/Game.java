package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    TextView score;
    TextView life;
    TextView time;

    TextView question;
    EditText answer;

    Button ok;
    Button next;
    Random random = new Random();
    int number1;
    int number2;
    int userAnswer;
    int realAnswer;
    int userScore = 0;
    int userLife = 3;

    CountDownTimer timer;
    private static final long START_TIMER_IN_MILIS = 10000;
    Boolean timerRunning;
    long longTimeLeftInMilis = START_TIMER_IN_MILIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = findViewById(R.id.textViewScoreValue);
        life = findViewById(R.id.textViewLifeValue);
        time = findViewById(R.id.textViewTimeValue);
        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);
        ok = findViewById(R.id.buttonOk);
        next = findViewById(R.id.buttonNextQuestion);

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAnswer = Integer.valueOf(answer.getText().toString());

                pauseTimer();

                if (userAnswer == realAnswer){
                    userScore += 10;
                    score.setText(""+userScore);
                    question.setText("Congratulation, ur answer is right");
                } else {
                    userLife -= -1;
                    life.setText(""+userLife);
                    question.setText("Sorry, ur answer is wrong");
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setText("");
                gameContinue();

                resetTimer();
            }
        });
    }

    public void gameContinue(){
        number1 = random.nextInt(100);
        number2 = random.nextInt(100);

        realAnswer = number1 + number2;
        question.setText(number1 + " + "+ number2);
        startTimer();
    }

    public void startTimer(){
        timer = new CountDownTimer(longTimeLeftInMilis, 1000) {
            @Override
            public void onTick(long l) {
                longTimeLeftInMilis = l;
                updateText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                pauseTimer();
                resetTimer();
                updateText();

                userLife -= 1;
                life.setText(""+userLife);
                question.setText("Sorry, time is up!");
            }
        }.start();

        timerRunning = true;
    }

    public void updateText(){
        int second = (int)(longTimeLeftInMilis / 1000) % 60;
        String timeLeft = String.format(Locale.getDefault(), "%02d", second);
        time.setText(timeLeft);
    }

    public void pauseTimer(){
        timer.cancel();
        timerRunning = false;
    }

    public void resetTimer(){
        longTimeLeftInMilis = START_TIMER_IN_MILIS;
        updateText();
    }
}
