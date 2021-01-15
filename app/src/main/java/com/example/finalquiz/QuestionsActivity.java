package com.example.finalquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {
    TextView tv;
    Button submitbutton, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3,rb4;
    List<String> questions=new ArrayList<>();
    List<String> answers=new ArrayList<>();
    List<String> opt=new ArrayList<>();
    int flag=0;
    public static int marks=0,correct=0,wrong=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        final TextView score = (TextView)findViewById(R.id.textView4);
        TextView textView=(TextView)findViewById(R.id.DispName);
        Intent intent = getIntent();
        String name= intent.getStringExtra("myname");

        if (name.trim().equals(""))
            textView.setText("Hello User");
        else
            textView.setText("Hello " + name);

        submitbutton=(Button)findViewById(R.id.button3);
        quitbutton=(Button)findViewById(R.id.buttonquit);
        tv=(TextView) findViewById(R.id.tvque);

        radio_g=(RadioGroup)findViewById(R.id.answersgrp);
        rb1=(RadioButton)findViewById(R.id.radioButton);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        rb3=(RadioButton)findViewById(R.id.radioButton3);
        rb4=(RadioButton)findViewById(R.id.radioButton4);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(radio_g.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();

                if(ansText.equals(answers.get(flag))) {
                    correct++;
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                }
                else {
                    wrong++;
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }

                flag++;

                if (score != null)
                    score.setText(""+correct);

                if(flag<questions.size())
                {
                    tv.setText(questions.get(flag));
                    rb1.setText(opt.get(flag*4));
                    rb2.setText(opt.get(flag*4 +1));
                    rb3.setText(opt.get(flag*4 +2));
                    rb4.setText(opt.get(flag*4 +3));
                }
                else
                {
                    marks=correct;
                    Intent in = new Intent(getApplicationContext(),ResultActivity.class);
                    startActivity(in);
                }
                radio_g.clearCheck();
            }
        });

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
                startActivity(intent);
            }
        });
        initComponent();

    }

    private void initComponent() {

        DatabaseContract databaseFunctions=new DatabaseContract(QuestionsActivity.this);
        List<QuestionModel> questionModels = new ArrayList<>(databaseFunctions.showAllQuestion());
        if(questionModels.size()<=0)
        {

            QuestionModel questionModel=new QuestionModel("Where is International Islamic university located?","H 10 Islamabad","G 10 Islamabad","H 10 Islamabad","PWD Islamabad","H 9 Islamabad");
            questionModels.add(questionModel);

            questionModel=new QuestionModel(
                    "Is International Islamic university have transport facilities?","Both","For Boys only","For Female Only","Both","None");
            questionModels.add(questionModel);

            questionModel=new QuestionModel(
                    "Which method can be defined only once in a program?",
                    "main method",
                    "finalize method","main method","static method","private method");
            questionModels.add(questionModel);

            questionModel=new QuestionModel(
                    "Which of these is not a bitwise operator?",
                    "<=",
                    "&","&=","|=","<=");
            questionModels.add(questionModel);


            questionModel=new QuestionModel(
                    "Which keyword is used by method to refer to the object that invoked it?",
                    "this",
                    "import","this","catch","abstract");
            questionModels.add(questionModel);

            questionModel=new QuestionModel(
                    "Which of these keywords is used to define interfaces in Java?",
                    "interface",
                    "Interface","interface","intf","Intf");
            questionModels.add(questionModel);


            questionModel=new QuestionModel(
                    "Which of the following is correct way of importing an entire package ‘pkg’?",
                    "import pkg.*",
                    "Import pkg.","import pkg.*","Import pkg.*","import pkg.");
            questionModels.add(questionModel);


            questionModel=new QuestionModel(
                    "Which of these access specifiers can be used for an interface?",
                    "public",
                    "public","protected","private","All of the mentioned");
            questionModels.add(questionModel);


            questionModel=new QuestionModel(
                    "What is the return type of Constructors?",
                    "None of the mentioned",
                    "int","float","void","None of the mentioned");
            questionModels.add(questionModel);


            questionModel=new QuestionModel(
                    "Which of the following package stores all the standard java classes?",
                    "java",
                    "lang","java","util","java.packages");
            questionModels.add(questionModel);

            questionModel=new QuestionModel(
                    "Which of these method of class String is used to compare two String objects for their equality?",
                    "equals()",
                    "equals()","Equals()","isequal()","Isequal()");
            questionModels.add(questionModel);
            questionModel=new QuestionModel(
                    "An expression involving byte, int, & literal numbers is promoted to which of these?",
                    "int",
                    "int","long","byte","float");
            questionModels.add(questionModel);


            databaseFunctions.insertQuestionList(questionModels);
        }
        Collections.shuffle(questionModels);
       /*else
       {
           Toast.makeText(this, "From database", Toast.LENGTH_SHORT).show();
       }*/

        for (int i=0;i<questionModels.size();i++)
        {
            QuestionModel questionModel=questionModels.get(i);
            questions.add(questionModel.getQuestion());
            answers.add(questionModel.getAnswer());
            opt.add(questionModel.getOption1());
            opt.add(questionModel.getOption2());
            opt.add(questionModel.getOption3());
            opt.add(questionModel.getOption4());
        }

        tv.setText(questions.get(flag));
        rb1.setText(opt.get(0));
        rb2.setText(opt.get(1));
        rb3.setText(opt.get(2));
        rb4.setText(opt.get(3));
    }

}