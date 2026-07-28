package com.example.bmicalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class ResultActivity extends AppCompatActivity {
    ImageView backImageView;
    TextView bmiResultTextView, bmiInformation;
    MaterialButton calculateAgainButton;
    LinearLayout underweightLayout, normalWeightLayout, overweightLayout, obeseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        underweightLayout = findViewById(R.id.underweightLayout);
        normalWeightLayout = findViewById(R.id.normalWeightLayout);
        overweightLayout = findViewById(R.id.overweightLayout);
        obeseLayout = findViewById(R.id.obeseLayout);
        bmiInformation = findViewById(R.id.bmiInformation);
        bmiResultTextView = findViewById(R.id.bmiResultTextView);
        backImageView = findViewById(R.id.backImageView);
        calculateAgainButton = findViewById(R.id.calculateAgainButton);

        // Intent থেকে ডেটা নেওয়া
        String bmi = getIntent().getStringExtra("BMI");
        String category = getIntent().getStringExtra("CATEGORY");

        // BMI মান দেখানো
        if (bmi != null) {
            bmiResultTextView.setText(bmi);
        }

        // ক্র্যাশ এড়াতে Safe Equals ব্যবহার করা হয়েছে
        if ("Underweight".equalsIgnoreCase(category)) {

            // নীল / আকাশী কালার (Underweight)
            underweightLayout.setBackgroundColor(Color.parseColor("#38BDF8"));
            bmiInformation.setText("শারীরিক প্রয়োজনীয় পুষ্টির অভাব বা ইমিউন সিস্টেম দুর্বল থাকার লক্ষণ হতে পারে। পেশি গঠন ও সুস্থভাবে ওজন বাড়ানোর জন্য প্রোটিন সমৃদ্ধ খাবার এবং নিউট্রিশনিস্টের পরামর্শে সঠিক ডায়েট চার্ট অনুসরণ করা উচিত।");

        } else if ("Normal Weight".equalsIgnoreCase(category)) {

            // সবুজ কালার (Normal Weight)
            normalWeightLayout.setBackgroundColor(Color.parseColor("#4ADE80"));
            bmiInformation.setText("আপনার শারীরিক ওজন একদম সঠিক ও ভারসাম্যপূর্ণ রয়েছে, যা হৃদরোগ বা ডায়াবেটিসের ঝুঁকি কমায়। এই স্বাস্থ্যকর অবস্থা বজায় রাখতে নিয়মিত সুষম খাদ্যগ্রহণ এবং দৈনন্দিন কায়িক পরিশ্রম বা ব্যায়াম অব্যাহত রাখুন।");

        } else if ("Overweight".equalsIgnoreCase(category)) {

            // হলুদ / অরেঞ্জ কালার (Overweight)
            overweightLayout.setBackgroundColor(Color.parseColor("#FACC15"));
            bmiInformation.setText("শরীরে বাড়তি মেদ জমতে শুরু করেছে, যা ভবিষ্যতে উচ্চ রক্তচাপ ও অন্যান্য স্বাস্থ্য সমস্যার সৃষ্টি করতে পারে। দৈনিক ক্যালরি গ্রহণের পরিমাণ নিয়ন্ত্রণ করা এবং নিয়মিত কার্ডিও বা শারীরিক ব্যায়াম শুরু করা প্রয়োজন।");

        } else if ("Obese".equalsIgnoreCase(category)) {

            // লাল কালার (Obese)
            obeseLayout.setBackgroundColor(Color.parseColor("#F87171"));
            bmiInformation.setText("এটি একটি গুরুতর স্বাস্থ্য ঝুঁকি, যা হৃদরোগ, টাইপ-২ ডায়াবেটিস ও জয়েন্টের সমস্যার সম্ভাবনা বাড়িয়ে দেয়। দ্রুত একজন রেজিস্টার্ড চিকিৎসক বা অভিজ্ঞ নিউট্রিশনিস্টের পরামর্শ নিয়ে কাস্টমাইজড ডায়েট ও লাইফস্টাইল প্ল্যান শুরু করা উচিত।");

        } else {
            bmiInformation.setText("তথ্য পাওয়া যায়নি। অনুগ্রহ করে আবার চেষ্টা করুন।");
        }

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        calculateAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}