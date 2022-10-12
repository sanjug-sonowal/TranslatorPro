package com.translatorpro.protranslator.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.testing.protranslator.R;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_translate;

    EditText et_1;
    TextView txt, txt_lan_1, txt_lan_2;
    Translator englishHindiTranslator, hindiEnglishTranslator;
    Translator englishArabicTranslator, arabicEnglishTranslator;
    Translator englishSpanishTranslator, spanishEnglishTranslator;
    Translator englishKoreanTranslator, koreanEnglishTranslator;
    Translator englishJapaneseTranslator, japaneseEnglishTranslator;
    Translator englishChineseTranslator, chineseEnglishTranslator;
    Translator englishFrenchTranslator, frenchEnglishTranslator;
    Translator englishGermanTranslator, germanEnglishTranslator;
    ClipboardManager clipboard;
    ClipData clip;
    MDToast mdToast;
    Boolean flag = true;
    Dialog dialog;
    TranslatorOptions options_2, options_3, options_4, options_5, options_6, options_7, options_8, options_9, options_10, options_11, options_12, options_13, options_14, options_15,options_16;
    String value;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            value = bundle.getString("key");
        }


        et_1 = findViewById(R.id.et_1);
        txt = findViewById(R.id.txt);
        txt_lan_1 = findViewById(R.id.txt_lan_1);
        txt_lan_2 = findViewById(R.id.txt_lan_2);

        btn_translate = (Button) findViewById(R.id.btn_translate);


//        findViewById(R.id.swap).setOnClickListener(this);
        findViewById(R.id.mic).setOnClickListener(this);
        findViewById(R.id.cp_1).setOnClickListener(this);
        findViewById(R.id.cp_2).setOnClickListener(this);

        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        dialog = new Dialog(MainActivity.this, android.R.style.Theme_Dialog);
        open_dialog();

        txt_lan_2.setText(value);

        txt_lan_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SelectLanguage.class));
                finish();
            }
        });

        txt_lan_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SelectLanguage.class));
                finish();
            }
        });

        btn_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value.equals("Hindi")) {
                    translate_hin(et_1.getText().toString());
                } else if (value.equals("Arabic")) {
                    translate_arabic(et_1.getText().toString());
                } else if (value.equals("Spanish")) {
                    translate_spanish(et_1.getText().toString());
                } else if (value.equals("Korean")) {
                    translate_korean(et_1.getText().toString());
                } else if (value.equals("Mexican")) {
                    translate_spanish(et_1.getText().toString());
                } else if (value.equals("Japanese")) {
                    translate_japanese(et_1.getText().toString());
                } else if (value.equals("Cantonese")) {
                    translate_chinese(et_1.getText().toString());
                } else if (value.equals("English")) {
                    translate_eng(et_1.getText().toString());
                } else if (value.equals("French")) {
                    translate_french(et_1.getText().toString());
                } else if (value.equals("German")) {
                    translate_german(et_1.getText().toString());
                } else {
                    translate_hin(et_1.getText().toString());
                }
            }
        });


        // Create an English-Hindi translator:
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.HINDI) //english to hindi
                        .build();

        options_2 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.HINDI)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();

        options_3 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.ARABIC) //english to arabic
                        .build();

        options_4 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ARABIC)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();

        options_5 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.SPANISH) //BOTH FOR english to SPANINSH AND MEXICAN
                        .build();

        options_6 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.SPANISH)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();

        options_7 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.KOREAN) // english to korean
                        .build();

        options_8 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.KOREAN)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();

        options_9 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.JAPANESE)
                        .build();


        options_10 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.JAPANESE)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();


        options_11 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.CHINESE)
                        .build();


        options_12 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.CHINESE)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();


        options_13 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.FRENCH)
                        .build();


        options_14 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.FRENCH)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();


        options_15 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.GERMAN)
                        .build();


        options_16 =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.GERMAN)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();

        englishHindiTranslator =
                Translation.getClient(options);

        englishArabicTranslator =
                Translation.getClient(options_3);

        englishSpanishTranslator =
                Translation.getClient(options_5);

        englishKoreanTranslator =
                Translation.getClient(options_7);

        englishJapaneseTranslator =
                Translation.getClient(options_9);

        englishChineseTranslator =
                Translation.getClient(options_11);

        englishFrenchTranslator =
                Translation.getClient(options_13);

        englishGermanTranslator =
                Translation.getClient(options_15);


        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();


        /* english to hindi translator download */

        englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                download_hin_eng();

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                txt.setText(e.getMessage());
                            }
                        });

        /* english to spanish translator download */

        englishSpanishTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                download_spanish_eng();

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                txt.setText(e.getMessage());
                            }
                        });

        /* english to arabic translator download */

        englishArabicTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        download_arabic_eng();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        txt.setText(e.getMessage());
                    }
                });

        /* english to korean translator download */

        englishKoreanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        download_korean_eng();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        txt.setText(e.getMessage());
                    }
                });

        /* english to japanese translator download */

        englishJapaneseTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        download_japanese_eng();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        txt.setText(e.getMessage());
                    }
                });

        /* english to chinese translator download */

        englishChineseTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        download_chinese_eng();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        txt.setText(e.getMessage());
                    }
                });

        /* english to french translator download */

        englishFrenchTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        download_french_eng();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        txt.setText(e.getMessage());
                    }
                });

        /* english to german translator download */

        englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        download_german_eng();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        txt.setText(e.getMessage());
                    }
                });


    }

    void download_hin_eng() {
        hindiEnglishTranslator = Translation.getClient(options_2);
        hindiEnglishTranslator.downloadModelIfNeeded()
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
//                                findViewById(R.id.btn).setEnabled(true);
//                                Toast.makeText(getApplicationContext(),"Second done",Toast.LENGTH_SHORT).show();
                                txt.setText(null);
                                dialog.dismiss();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void download_arabic_eng() {
        arabicEnglishTranslator = Translation.getClient(options_4);
        arabicEnglishTranslator.downloadModelIfNeeded()
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
//                                findViewById(R.id.btn).setEnabled(true);
//                                Toast.makeText(getApplicationContext(),"Second done",Toast.LENGTH_SHORT).show();
                                txt.setText(null);
                                dialog.dismiss();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }
    void download_spanish_eng() {
        spanishEnglishTranslator = Translation.getClient(options_6);
        spanishEnglishTranslator.downloadModelIfNeeded()
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
//                                findViewById(R.id.btn).setEnabled(true);
//                                Toast.makeText(getApplicationContext(),"Second done",Toast.LENGTH_SHORT).show();
                                txt.setText(null);
                                dialog.dismiss();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void download_korean_eng() {
        koreanEnglishTranslator = Translation.getClient(options_7);
        koreanEnglishTranslator.downloadModelIfNeeded()
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
//                                findViewById(R.id.btn).setEnabled(true);
//                                Toast.makeText(getApplicationContext(),"Second done",Toast.LENGTH_SHORT).show();
                                txt.setText(null);
                                dialog.dismiss();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void download_japanese_eng() {
        japaneseEnglishTranslator = Translation.getClient(options_9);
        japaneseEnglishTranslator.downloadModelIfNeeded()
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
//                                findViewById(R.id.btn).setEnabled(true);
//                                Toast.makeText(getApplicationContext(),"Second done",Toast.LENGTH_SHORT).show();
                                txt.setText(null);
                                dialog.dismiss();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void download_chinese_eng() {
        chineseEnglishTranslator = Translation.getClient(options_11);
        chineseEnglishTranslator.downloadModelIfNeeded()
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
//                                findViewById(R.id.btn).setEnabled(true);
//                                Toast.makeText(getApplicationContext(),"Second done",Toast.LENGTH_SHORT).show();
                                txt.setText(null);
                                dialog.dismiss();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void download_french_eng() {
        frenchEnglishTranslator = Translation.getClient(options_13);
        frenchEnglishTranslator.downloadModelIfNeeded()
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
//                                findViewById(R.id.btn).setEnabled(true);
//                                Toast.makeText(getApplicationContext(),"Second done",Toast.LENGTH_SHORT).show();
                                txt.setText(null);
                                dialog.dismiss();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void download_german_eng() {
        germanEnglishTranslator = Translation.getClient(options_15);
        germanEnglishTranslator.downloadModelIfNeeded()
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
//                                findViewById(R.id.btn).setEnabled(true);
//                                Toast.makeText(getApplicationContext(),"Second done",Toast.LENGTH_SHORT).show();
                                txt.setText(null);
                                dialog.dismiss();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mic:
                voice();
                break;

//            case R.id.swap:
//                swap();
//                break;

            case R.id.cp_1:
                try {
                    copy(et_1.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.cp_2:
                try {
                    copy(txt.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    void toast(String message, int type) {
        mdToast = MDToast.makeText(getApplicationContext(), message, MDToast.LENGTH_SHORT, type);
        mdToast.show();
    }

    void voice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if (flag)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        else intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi");
        try {
            startActivityForResult(intent, 200);
        } catch (ActivityNotFoundException a) {
            toast("Intent Problem", 3);
        }
    }

    public void open_dialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setCancelable(false);
        dialog.show();
    }

    void swap() {
        String a = txt_lan_1.getText().toString();
        String b = txt_lan_2.getText().toString();
        a = a + b;
        b = a.substring(0, a.length() - b.length());
        a = a.substring(b.length());
        txt_lan_1.setText(a);
        txt_lan_2.setText(b);
        if (flag)
            flag = false;
        else flag = true;
        et_1.setText(null);
        txt.setText(null);
        toast("Language Changed", 1);
    }

    void copy(String text) {
        if (!text.equals("")) {
            clip = ClipData.newPlainText("text", text);
            clipboard.setPrimaryClip(clip);
            toast("Text Copied", 1);
        } else {
            toast("Text Copied", 2);
//            mdToast = MDToast.makeText(getApplicationContext(), "There is no text", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
        }
        mdToast.show();
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                assert result != null;
                et_1.setText(result.get(0));

                if (value.equals("Hindi")) {
                    translate_hin(et_1.getText().toString().trim());
                } else if (value.equals("Arabic")) {
                    translate_arabic(et_1.getText().toString().trim());
                } else if (value.equals("Spanish")) {
                    translate_spanish(et_1.getText().toString().trim());
                } else if (value.equals("Korean")) {
                    translate_korean(et_1.getText().toString().trim());
                } else if (value.equals("Mexican")) {
                    translate_spanish(et_1.getText().toString().trim());
                } else if (value.equals("Japanese")) {
                    translate_japanese(et_1.getText().toString().trim());
                } else if (value.equals("Cantonese")) {
                    translate_chinese(et_1.getText().toString().trim());
                } else if (value.equals("English")) {
                    translate_eng(et_1.getText().toString().trim());
                } else if (value.equals("French")) {
                    translate_french(et_1.getText().toString().trim());
                } else if (value.equals("German")) {
                    translate_german(et_1.getText().toString().trim());
                } else {
                    translate_hin(et_1.getText().toString().trim());
                }

            }
        }
    }

    void translate_hin(String text) {
        englishHindiTranslator.translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                txt.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }
    void translate_eng(String text) {
        hindiEnglishTranslator.translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                txt.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void translate_arabic(String text) {
        englishArabicTranslator.translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                txt.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void translate_spanish(String text) {
        englishSpanishTranslator.translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                txt.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void translate_japanese(String text) {
        englishJapaneseTranslator.translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                txt.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void translate_chinese(String text) {
        englishChineseTranslator.translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                txt.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void translate_french(String text) {
        englishFrenchTranslator.translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                txt.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void translate_german(String text) {
        englishGermanTranslator.translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                txt.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

    void translate_korean(String text) {
        englishKoreanTranslator.translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                txt.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                                txt.setText(e.getMessage());
                            }
                        });
    }

}