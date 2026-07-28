package com.example.bmicalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    AppBarLayout appBarLayout;
    MaterialToolbar toolBar;
    MaterialButton calculateButton;
    TextView cmTextView, ftTextView;
    CardView maleCard, femaleCard;
    NavigationView navigationView;
    LinearLayout mainLinearLayout_Drawer;
    EditText heightEditText, weightEditText, ageEditText;

    boolean isMale = false;
    boolean isFemale = false;

    // CM আর FT এর জন্য দুইটা আলাদা boolean flag
    boolean isCmSelected = true;  // By default CM selected
    boolean isFtSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        maleCard = findViewById(R.id.maleCard);
        femaleCard = findViewById(R.id.femaleCard);
        drawerLayout = findViewById(R.id.main);
        appBarLayout = findViewById(R.id.appBarLayout);
        toolBar = findViewById(R.id.toolBar);
        calculateButton = findViewById(R.id.calculateButton);
        cmTextView = findViewById(R.id.cmTextView);
        ftTextView = findViewById(R.id.ftTextView);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        ageEditText = findViewById(R.id.ageEditText);
        navigationView = findViewById(R.id.navigationView);
        mainLinearLayout_Drawer = findViewById(R.id.mainLinearLayout_Drawer);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.nav_about||menuItem.getItemId()==R.id.nav_settings||menuItem.getItemId()==R.id.nav_contact||menuItem.getItemId()==R.id.nav_rate){
                    Intent myInt = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(myInt);
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });

        // --- Gender Selection ---
        maleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMale = true;
                isFemale = false;
                maleCard.setCardBackgroundColor(Color.parseColor("#26A69A"));
                femaleCard.setCardBackgroundColor(Color.WHITE);
            }
        });

        femaleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFemale = true;
                isMale = false;
                femaleCard.setCardBackgroundColor(Color.parseColor("#26A69A"));
                maleCard.setCardBackgroundColor(Color.WHITE);
            }
        });

        // --- CM and FT Unit Selection ---

        // ১. ইউজার CM সিলেক্ট করলে
        cmTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCmSelected = true;
                isFtSelected = false;
                cmTextView.setTextColor(Color.parseColor("#26A69A"));
                ftTextView.setTextColor(Color.parseColor("#808080"));
                heightEditText.setHint("Height in CM (e.g. 170)");
                // CM mode e number keyboard e rakhle valo
                heightEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
                heightEditText.setText("");
            }
        });

        // ২. ইউজার FT সিলেক্ট করলে
        ftTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFtSelected = true;
                isCmSelected = false;
                ftTextView.setTextColor(Color.parseColor("#26A69A"));
                cmTextView.setTextColor(Color.parseColor("#808080"));
                heightEditText.setHint("Height e.g. 5'8\"");
                // FT mode e text keyboard lagbe, karon ' aar " type korte hobe
                heightEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
                heightEditText.setText("");
            }
        });

        // --- Calculate Button ---
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heightString = heightEditText.getText().toString().trim();
                String weightString = weightEditText.getText().toString().trim();
                String ageString = ageEditText.getText().toString().trim();

                if (heightString.isEmpty()) {
                    heightEditText.setError("Enter your height");
                    return;
                }
                if (weightString.isEmpty()) {
                    weightEditText.setError("Enter your weight");
                    return;
                }
                if (ageString.isEmpty()) {
                    ageEditText.setError("Enter your age");
                    return;
                }

                double weight = Double.parseDouble(weightString);
                double heightInMeter;

                // ইউজার কোনটা সিলেক্ট করেছে সেই অনুযায়ী হিসাব
                if (isCmSelected) {
                    double heightInput = Double.parseDouble(heightString);
                    heightInMeter = heightInput / 100.0;
                } else if (isFtSelected) {
                    heightInMeter = parseFeetInchesToMeters(heightString);
                    if (heightInMeter <= 0) {
                        // error already set inside the method
                        return;
                    }
                } else {
                    double heightInput = Double.parseDouble(heightString);
                    heightInMeter = heightInput / 100.0;
                }

                // BMI Calculation
                double bmi = weight / (heightInMeter * heightInMeter);

                // BMI Category
                String category;
                String bmiResult = String.format("%.2f", bmi);

                if (bmi < 18.5) {
                    category = "Underweight";
                } else if (bmi < 25) {
                    category = "Normal Weight";
                } else if (bmi < 30) {
                    category = "Overweight";
                } else {
                    category = "Obese";
                }

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("BMI", bmiResult);
                intent.putExtra("CATEGORY", category);
                startActivity(intent);

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolBar,R.string.close,R.string.open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // ---------------------------------------------------
    // Helper method: "5'8"" emon string ke meter e convert kore
    // ---------------------------------------------------
    private double parseFeetInchesToMeters(String input) {
        input = input.trim();
        double feet = 0;
        double inches = 0;

        try {
            if (input.contains("'")) {
                // Example: 5'8"  ->  feet part = "5", inch part = "8""
                String[] parts = input.split("'");

                feet = Double.parseDouble(parts[0].trim());

                if (parts.length > 1) {
                    String inchPart = parts[1].replace("\"", "").trim();
                    if (!inchPart.isEmpty()) {
                        inches = Double.parseDouble(inchPart);
                    }
                }
            } else {
                // User jodi shudhu "5" emon number dey, feet hishebe dhorbo
                feet = Double.parseDouble(input);
            }
        } catch (NumberFormatException e) {
            heightEditText.setError("Sothik format e din, jemon: 5'8\"");
            return -1;
        }

        double totalInches = (feet * 12) + inches;
        return totalInches * 0.0254; // 1 inch = 0.0254 meter
    }




}