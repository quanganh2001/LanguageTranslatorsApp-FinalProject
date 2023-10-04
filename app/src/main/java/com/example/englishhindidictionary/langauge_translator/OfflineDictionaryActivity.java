package com.example.englishhindidictionary.langauge_translator;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.englishhindidictionary.R;
import com.example.englishhindidictionary.langauge_translator.adapter.EnglishAdapter;
import com.example.englishhindidictionary.langauge_translator.dbhelper.Database;
import com.example.englishhindidictionary.langauge_translator.utildata.GetSetClass;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.sql.SQLException;
import java.util.ArrayList;

public class OfflineDictionaryActivity extends AppCompatActivity {
    public static ArrayList<GetSetClass> EngAll;
    public static EnglishAdapter cuznappsEnglishAdapter;
    public static ArrayList<GetSetClass> engWordList;
    ImageView cross;
    EditText editText;
    ListView listView;
    Database myDatabase;
    ImageButton speak;

    private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        private AsyncTaskRunner() {
        }


        public void onPreExecute() {
            super.onPreExecute();
            ProgressDialog progressDialog2 = new ProgressDialog(OfflineDictionaryActivity.this);
            this.progressDialog = progressDialog2;
            progressDialog2.setTitle("Getting Data");
            this.progressDialog.setMessage("Please wait getting data...");
            this.progressDialog.show();
        }


        public Void doInBackground(Void... voidArr) {
            try {
                OfflineDictionaryActivity.this.myDatabase.openDataBase();
                return null;
            } catch (SQLException e) {
                OfflineDictionaryActivity offlineDictionaryActivity = OfflineDictionaryActivity.this;
                Toast.makeText(offlineDictionaryActivity, "Data Base are not opened" + e, Toast.LENGTH_SHORT).show();
                return null;
            }
        }


        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.progressDialog.dismiss();
        }
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_dictionary);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        this.myDatabase = new Database(this);
        new AsyncTaskRunner().execute(new Void[0]);
        this.listView = (ListView) findViewById(R.id.enToHinListView);
        engWordList = new ArrayList<>();
        EngAll = new ArrayList<>();
        EditText editText2 = (EditText) findViewById(R.id.enToHinSearchView);
        this.editText = editText2;
        editText2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    OfflineDictionaryActivity.engWordList = OfflineDictionaryActivity.this.myDatabase.SearchEnglishWord(charSequence.toString());
                    OfflineDictionaryActivity.cuznappsEnglishAdapter = new EnglishAdapter(OfflineDictionaryActivity.this, OfflineDictionaryActivity.engWordList, OfflineDictionaryActivity.this.listView);
                    OfflineDictionaryActivity.this.listView.setAdapter(OfflineDictionaryActivity.cuznappsEnglishAdapter);
                    OfflineDictionaryActivity.this.listView.setVisibility(View.VISIBLE);
                    if (OfflineDictionaryActivity.cuznappsEnglishAdapter.getCount() <= 0) {
                        OfflineDictionaryActivity offlineDictionaryActivity = OfflineDictionaryActivity.this;
                        Toast.makeText(offlineDictionaryActivity, "No Suggestion for " + charSequence, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    return;
                }
                OfflineDictionaryActivity.this.listView.setVisibility(View.GONE);
            }
        });
        ImageButton imageButton = (ImageButton) findViewById(R.id.enToHinSpeakBtn);
        this.speak = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "en-US");
                try {
                    OfflineDictionaryActivity.this.startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException unused) {
                    Toast.makeText(OfflineDictionaryActivity.this, "Sorry! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                int id = OfflineDictionaryActivity.engWordList.get(i).getId();
                Intent intent = new Intent(OfflineDictionaryActivity.this, MeaningActivity.class);
                intent.putExtra("MEAN_ID", id);
                OfflineDictionaryActivity.this.startActivity(intent);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.engCross);
        this.cross = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OfflineDictionaryActivity.this.editText.setText("");
            }
        });
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1 && intent != null) {
            ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("android.speech.extra.RESULTS");
            final Dialog dialog = new Dialog(this);
            dialog.setTitle("Speeched Suggestion");
            dialog.setContentView(R.layout.mic_dialog);
            final ListView listView2 = (ListView) dialog.findViewById(R.id.listViews);
            listView2.setAdapter(new ArrayAdapter(this, 17367043, stringArrayListExtra));
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    OfflineDictionaryActivity.this.editText.setText(listView2.getItemAtPosition(i).toString());
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.myDatabase.closeDB();
    }
}
