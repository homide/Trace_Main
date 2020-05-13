package com.kush.naya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public TextView textview;
    public Button searchButton;

// kuch bhi
    // kush bhi 2
    //hello my name is kanishk chauhan

    public void btnClick1(View view) {
        Toast.makeText(this, "Searching.....", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public Spinner spinnerCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView) findViewById(R.id.searchText);
        searchButton = (Button) findViewById(R.id.btnSearch1);

//        View.OnClickListener sear = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String search = textview.getText().toString();
//                new Main2Activity(search);
//
//            }
//        };
//        searchButton.setOnClickListener(sear);

        final String[]arrayCat=getResources().getStringArray(R.array.Categories);
        spinnerCat=findViewById(R.id.spinnerCat);
        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, ""+arrayCat[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
