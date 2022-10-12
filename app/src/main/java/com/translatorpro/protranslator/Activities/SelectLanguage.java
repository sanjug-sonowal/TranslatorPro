package com.translatorpro.protranslator.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.testing.protranslator.R;

public class SelectLanguage extends AppCompatActivity {

    ImageView arabic_btn;
    ImageView spanish_btn;
    ImageView korean_btn;
    ImageView mexican_btn;
    ImageView japanese_btn;
    ImageView cantonese_btn;
    ImageView english_btn;
    ImageView french_btn;
    ImageView german_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        arabic_btn = (ImageView) findViewById(R.id.arabic_btn);
        spanish_btn = (ImageView) findViewById(R.id.spanish_btn);
        korean_btn = (ImageView) findViewById(R.id.korean_btn);
        mexican_btn = (ImageView) findViewById(R.id.mexican_btn);
        japanese_btn = (ImageView) findViewById(R.id.japanese_btn);
        cantonese_btn = (ImageView) findViewById(R.id.cantonese_btn);
        english_btn = (ImageView) findViewById(R.id.english_btn);
        french_btn = (ImageView) findViewById(R.id.french_btn);
        german_btn = (ImageView) findViewById(R.id.german_btn);

        arabic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLanguage.this,MainActivity.class);
                intent.putExtra("key","Arabic");
                startActivity(intent);
            }
        });

        spanish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLanguage.this,MainActivity.class);
                intent.putExtra("key","Spanish");
                startActivity(intent);
            }
        });

        korean_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLanguage.this,MainActivity.class);
                intent.putExtra("key","Korean");
                startActivity(intent);
            }
        });

        mexican_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLanguage.this,MainActivity.class);
                intent.putExtra("key","Mexican");
                startActivity(intent);
            }
        });

        japanese_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLanguage.this,MainActivity.class);
                intent.putExtra("key","Japanese");
                startActivity(intent);
            }
        });

        cantonese_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLanguage.this,MainActivity.class);
                intent.putExtra("key","Cantonese");
                startActivity(intent);
            }
        });

        english_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLanguage.this,MainActivity.class);
                intent.putExtra("key","English");
                startActivity(intent);
            }
        });

        french_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLanguage.this,MainActivity.class);
                intent.putExtra("key","French");
                startActivity(intent);
            }
        });

        german_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLanguage.this,MainActivity.class);
                intent.putExtra("key","German");
                startActivity(intent);
            }
        });

    }
}