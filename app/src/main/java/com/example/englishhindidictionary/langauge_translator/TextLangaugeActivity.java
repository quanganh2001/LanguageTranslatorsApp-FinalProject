package com.example.englishhindidictionary.langauge_translator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.Key;
import com.example.englishhindidictionary.R;
import com.example.englishhindidictionary.langauge_translator.dbhelper.DatabaseHelper;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
//import org.androidannotations.api.rest.MediaType;
import org.androidannotations.rest.spring.api.MediaType;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

public class TextLangaugeActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    AlertDialog.Builder builder1;
    DatabaseHelper dbHelper;
    AlertDialog dialog1;
    LayoutInflater inflater;
    ImageView ivspeak;
    public ProgressDialog mProgressDialog;
    public EditText f210qu;



    public EditText f211rt;
    String speakfromlangcode = "en-IN";
    String speaktolangcode = "en-IN";
    Spinner spinner;
    Spinner spinner2;
    int status;

    public TextToSpeech tts;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.text_langauge_activity);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        this.dbHelper = databaseHelper;
        databaseHelper.openDataBase();
        initTranslator();
        this.builder1 = new AlertDialog.Builder(this);
        this.inflater = getLayoutInflater();
        this.ivspeak = (ImageView) findViewById(R.id.ivspeak);
        this.spinner = (Spinner) findViewById(R.id.fspinner);
        this.spinner2 = (Spinner) findViewById(R.id.sspinner);
        this.f210qu = (EditText) findViewById(R.id.querytext);
        this.f211rt = (EditText) findViewById(R.id.resulttext);
        this.ivspeak.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextLangaugeActivity TextLangaugeActivity = TextLangaugeActivity.this;
                TextLangaugeActivity.speakfromlangcode = LanguagesData.getSpeakLangCode(TextLangaugeActivity.spinner.getSelectedItemPosition());
                TextLangaugeActivity.this.trans();
            }
        });
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, LanguagesData.getSpeakLang());
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_display, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        this.spinner.setAdapter(arrayAdapter);
        this.spinner.setSelection(14);
        this.spinner2.setAdapter(arrayAdapter);
        this.spinner2.setSelection(23);
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                TextLangaugeActivity TextLangaugeActivity = TextLangaugeActivity.this;
                TextLangaugeActivity.speakfromlangcode = LanguagesData.getSpeakLangCode(TextLangaugeActivity.spinner.getSelectedItemPosition());
                ((TextView) adapterView.getChildAt(0)).setTextColor(TextLangaugeActivity.this.getResources().getColor(R.color.colorPrimary));
            }
        });
        this.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                TextLangaugeActivity TextLangaugeActivity = TextLangaugeActivity.this;
                TextLangaugeActivity.speaktolangcode = LanguagesData.getSpeakLangCode(TextLangaugeActivity.spinner2.getSelectedItemPosition());
                ((TextView) adapterView.getChildAt(0)).setTextColor(TextLangaugeActivity.this.getResources().getColor(R.color.colorPrimary));
            }
        });
        findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextLangaugeActivity.this.f210qu.setText("");
                TextLangaugeActivity.this.f211rt.setText("");
                int selectedItemPosition = TextLangaugeActivity.this.spinner.getSelectedItemPosition();
                TextLangaugeActivity.this.spinner.setSelection(TextLangaugeActivity.this.spinner2.getSelectedItemPosition());
                TextLangaugeActivity.this.spinner2.setSelection(selectedItemPosition);
            }
        });
        String stringExtra = getIntent().getStringExtra("text");
        Log.e("====", "onSuccess1: " + stringExtra);
        this.f210qu.setText(stringExtra);
        this.f211rt.setMovementMethod(new ScrollingMovementMethod());
        findViewById(R.id.translate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextLangaugeActivity.this.f210qu.length() > 0) {
                    TextLangaugeActivity TextLangaugeActivity = TextLangaugeActivity.this;
                    TextLangaugeActivity.hideKeyboard(TextLangaugeActivity);
                    ProgressDialog unused = TextLangaugeActivity.this.mProgressDialog = new ProgressDialog(TextLangaugeActivity.this);
                    TextLangaugeActivity.this.mProgressDialog.setMessage("Translating");
                    TextLangaugeActivity.this.mProgressDialog.setProgressStyle(0);
                    TextLangaugeActivity.this.mProgressDialog.setCancelable(false);
                    TextLangaugeActivity.this.mProgressDialog.show();
                    try {
                        String encode = URLEncoder.encode(TextLangaugeActivity.this.f210qu.getText().toString().trim(), Key.STRING_CHARSET_NAME);
                        ReadJSONFeedTask readJSONFeedTask = new ReadJSONFeedTask();
                        readJSONFeedTask.execute(new String[]{"https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + LanguagesData.getsptrCode(TextLangaugeActivity.this.spinner.getSelectedItemPosition()) + "&tl=" + LanguagesData.getsptrCode(TextLangaugeActivity.this.spinner2.getSelectedItemPosition()) + "&dt=t&ie=UTF-8&oe=UTF-8&q=" + encode});
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(TextLangaugeActivity.this.getApplicationContext(), "Enter Text", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.clearall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextLangaugeActivity.this.f210qu.setText("");
                TextLangaugeActivity.this.f211rt.setText("");
            }
        });
        findViewById(R.id.copyq).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextLangaugeActivity.this.f211rt.length() > 0) {
                    ((ClipboardManager) TextLangaugeActivity.this.getApplicationContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text", TextLangaugeActivity.this.f211rt.getText()));
                    Toast.makeText(TextLangaugeActivity.this.getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(TextLangaugeActivity.this.getApplicationContext(), "No Text!", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.speakt).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextLangaugeActivity.this.status == -1 || TextLangaugeActivity.this.status == -2) {
                    Toast.makeText(TextLangaugeActivity.this, "This Language is not supported", Toast.LENGTH_SHORT).show();
                    return;
                }
                TextLangaugeActivity.this.tts.speak(TextLangaugeActivity.this.f211rt.getText().toString(), 0, (HashMap) null);
                Log.e("TTS", "This Language is supported");
            }
        });
        findViewById(R.id.sharet).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextLangaugeActivity.this.f211rt.length() > 0) {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType(MediaType.TEXT_PLAIN);
                    intent.putExtra("android.intent.extra.TEXT", TextLangaugeActivity.this.f211rt.getText().toString());
                    TextLangaugeActivity.this.startActivity(Intent.createChooser(intent, "Share with"));
                    return;
                }
                Toast.makeText(TextLangaugeActivity.this.getApplicationContext(), "No text", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTranslator() {
        this.tts = new TextToSpeech(this, this);
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    public void onInit(int i) {
        if (this.status == 0) {
            int language = this.tts.setLanguage(new Locale(this.speakfromlangcode));
            if (language == -1 || language == -2) {
                Log.e("TTS", "This Language is not supported");
            } else {
                Log.e("TTS", "This Language is supported");
            }
        } else {
            Log.e("TTS", "Initialization Failed!");
        }
    }


    public void trans() {
        SpeechRecognizer createSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.LANGUAGE", this.speakfromlangcode);
        intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", this.speakfromlangcode);
        intent.putExtra("android.speech.extra.MAX_RESULTS", 5);
        intent.putExtra("calling_package", getPackageName());
        createSpeechRecognizer.startListening(intent);
        createSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            public void onBeginningOfSpeech() {
            }

            public void onBufferReceived(byte[] bArr) {
            }

            public void onEndOfSpeech() {
            }

            public void onEvent(int i, Bundle bundle) {
            }

            public void onPartialResults(Bundle bundle) {
            }

            public void onRmsChanged(float f) {
            }

            public void onReadyForSpeech(Bundle bundle) {
                TextLangaugeActivity.this.builder1.setView(TextLangaugeActivity.this.inflater.inflate(R.layout.speakdialog, (ViewGroup) null));
                TextLangaugeActivity TextLangaugeActivity = TextLangaugeActivity.this;
                TextLangaugeActivity.dialog1 = TextLangaugeActivity.builder1.create();
                TextLangaugeActivity.this.dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                TextLangaugeActivity.this.dialog1.show();
                ((TextView) TextLangaugeActivity.this.dialog1.findViewById(R.id.dlang)).setText(LanguagesData.getSpeakLang()[TextLangaugeActivity.this.spinner.getSelectedItemPosition()]);
            }

            public void onError(int i) {
                String str;
                Log.e("====", "onError: " + i);
                switch (i) {
                    case 2:
                        str = "Network Error, Please Check Your Internet";
                        break;
                    case 3:
                        str = "Audio Error, Please Try Again";
                        break;
                    case 4:
                        str = "I Can not Connect To Server";
                        break;
                    case 5:
                        str = "I Can't Hear You, Please Try Again";
                        break;
                    case 6:
                        str = " I Can't Hear You, Please Try Again";
                        break;
                    case 7:
                        str = "I Don't Understand, Please Try Again";
                        break;
                    case 8:
                        str = "Recognizer Busy, Please Try Later";
                        break;
                    default:
                        str = "Didn't understand, please try again.";
                        break;
                }
                Toast.makeText(TextLangaugeActivity.this.getApplicationContext(), str, 0).show();
                if (TextLangaugeActivity.this.dialog1 != null) {
                    TextLangaugeActivity.this.dialog1.dismiss();
                }
            }

            public void onResults(Bundle bundle) {
                ArrayList<String> stringArrayList = bundle.getStringArrayList("results_recognition");
                TextLangaugeActivity.this.dialog1.dismiss();
                TextLangaugeActivity.this.f210qu.setText(stringArrayList.get(0).trim());
                Log.e("===", "onResults: " + stringArrayList.get(0).trim());
            }
        });
    }

    public void onDestroy() {
        TextToSpeech textToSpeech = this.tts;
        if (textToSpeech != null) {
            textToSpeech.stop();
            this.tts.shutdown();
        }
        super.onDestroy();
    }

    public void onPause() {
        TextToSpeech textToSpeech = this.tts;
        if (textToSpeech != null) {
            textToSpeech.stop();
            this.tts.shutdown();
        }
        super.onPause();
    }

    private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
        private ReadJSONFeedTask() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public String doInBackground(String... strArr) {
            return TextLangaugeActivity.this.readJSONFeed(strArr[0]);
        }

        public void onPostExecute(String str) {
            TextLangaugeActivity.this.mProgressDialog.dismiss();
            if (str.equals("[\"ERROR\"]")) {
                Toast.makeText(TextLangaugeActivity.this, "Sorry Something went Wrong", 0).show();
                return;
            }
            try {
                JSONArray jSONArray = new JSONArray(str);
                String str2 = "";
                for (int i = 0; i < jSONArray.getJSONArray(0).length(); i++) {
                    str2 = str2 + jSONArray.getJSONArray(0).getJSONArray(i).getString(0);
                }
                TextLangaugeActivity.this.f211rt.setText(str2);
                TextLangaugeActivity.this.dbHelper.addSearchHistory(TextLangaugeActivity.this.spinner.getSelectedItem().toString(), TextLangaugeActivity.this.f210qu.getText().toString().trim(), TextLangaugeActivity.this.spinner2.getSelectedItem().toString(), TextLangaugeActivity.this.f211rt.getText().toString().trim());
                TextLangaugeActivity.this.speaktolangcode = LanguagesData.getSpeakLangCode(TextLangaugeActivity.this.spinner2.getSelectedItemPosition());
                TextLangaugeActivity.this.status = TextLangaugeActivity.this.tts.setLanguage(new Locale(TextLangaugeActivity.this.speaktolangcode));
                TextLangaugeActivity.this.tts.setLanguage(new Locale(TextLangaugeActivity.this.speaktolangcode));
            } catch (Exception e) {
                Log.d("JSONFeedTask", e.getLocalizedMessage());
            }
        }
    }

    public String readJSONFeed(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpResponse execute = new DefaultHttpClient().execute(new HttpGet(str));
            if (execute.getStatusLine().getStatusCode() == 200) {
                InputStream content = execute.getEntity().getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                }
                content.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
            sb.append("[\"ERROR\"]");
        }
        return sb.toString();
    }

    public void onBackPressed() {
        super.onBackPressed();
        SplashScreen.showAdmobInterstitial();
    }
}
