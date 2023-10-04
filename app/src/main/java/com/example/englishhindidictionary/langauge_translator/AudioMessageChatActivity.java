package com.example.englishhindidictionary.langauge_translator;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.Key;
import com.example.englishhindidictionary.R;
import com.example.englishhindidictionary.langauge_translator.adapter.MessageListAdapter;
import com.example.englishhindidictionary.langauge_translator.utildata.VoiceChatItem;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

public class AudioMessageChatActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView back2;
    AlertDialog.Builder builder1;
    VoiceChatItem chatItem = new VoiceChatItem();
    ImageView delete_all;
    AlertDialog dialog1;
    Spinner fspinner;
    Spinner fspinner2;
    LayoutInflater inflater;
    String input1;
    String input2;
    LinearLayout lang1;
    LinearLayout lang2;
    MessageListAdapter mMessageAdapter;

    public ProgressDialog mProgressDialog;
    List<VoiceChatItem> messageList;
    RecyclerView reyclerview_message_list;
    ImageView speak;
    ImageView speak2;
    TextView text_empty;
    int trans;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_voice_chat);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.text_empty = (TextView) findViewById(R.id.text_empty);
        ImageView imageView = (ImageView) findViewById(R.id.back2);
        this.back2 = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AudioMessageChatActivity.this.onBackPressed();
            }
        });
        this.reyclerview_message_list = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        ImageView imageView2 = (ImageView) findViewById(R.id.delete_all);
        this.delete_all = imageView2;
        imageView2.setOnClickListener(this);
        this.lang1 = (LinearLayout) findViewById(R.id.lang1);
        this.lang2 = (LinearLayout) findViewById(R.id.lang2);
        ImageView imageView3 = (ImageView) findViewById(R.id.speak);
        this.speak = imageView3;
        imageView3.setOnClickListener(this);
        ImageView imageView4 = (ImageView) findViewById(R.id.speak2);
        this.speak2 = imageView4;
        imageView4.setOnClickListener(this);
        this.fspinner = (Spinner) findViewById(R.id.fspinner);
        this.fspinner2 = (Spinner) findViewById(R.id.fspinner2);
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, LanguagesData.getSpeakLang());
        Log.e("====", "onCreate: " + LanguagesData.getSpeakLang().length);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_display, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        this.fspinner.setAdapter(arrayAdapter);
        this.fspinner.setSelection(14);
        this.fspinner2.setAdapter(arrayAdapter);
        this.fspinner2.setSelection(23);
        this.fspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(AudioMessageChatActivity.this.getResources().getColor(R.color.colorPrimary));
            }
        });
        this.fspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(AudioMessageChatActivity.this.getResources().getColor(R.color.white));
            }
        });
        this.builder1 = new AlertDialog.Builder(this);
        this.inflater = getLayoutInflater();
        ArrayList arrayList2 = new ArrayList();
        this.messageList = arrayList2;
        arrayList2.clear();
        this.reyclerview_message_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.reyclerview_message_list.setItemAnimator(new DefaultItemAnimator());
        MessageListAdapter messageListAdapter = new MessageListAdapter(this, this.messageList);
        this.mMessageAdapter = messageListAdapter;
        this.reyclerview_message_list.setAdapter(messageListAdapter);
        if (this.messageList.size() == 0) {
            this.text_empty.setVisibility(View.VISIBLE);
            this.reyclerview_message_list.setVisibility(View.GONE);
            return;
        }
        this.text_empty.setVisibility(View.GONE);
        this.reyclerview_message_list.setVisibility(View.VISIBLE);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_all /*2131296433*/:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Do you want to clear translation ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AudioMessageChatActivity.this.messageList.clear();
                        AudioMessageChatActivity.this.mMessageAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
                if (this.messageList.size() == 0) {
                    this.text_empty.setVisibility(View.VISIBLE);
                    this.reyclerview_message_list.setVisibility(View.GONE);
                    return;
                }
                this.text_empty.setVisibility(View.GONE);
                this.reyclerview_message_list.setVisibility(View.VISIBLE);
                return;
            case R.id.speak /*2131296883*/:
                this.trans = 0;
                trans();
                return;
            case R.id.speak2 /*2131296884*/:
                this.trans = 1;
                trans2();
                return;
            default:
                return;
        }
    }

    private void trans() {
        SpeechRecognizer createSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.LANGUAGE", LanguagesData.getSpeakLangCode(this.fspinner.getSelectedItemPosition()));
        intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{LanguagesData.getSpeakLangCode(this.fspinner.getSelectedItemPosition())});
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
                AudioMessageChatActivity.this.builder1.setView(AudioMessageChatActivity.this.inflater.inflate(R.layout.speakdialog, (ViewGroup) null));
                AudioMessageChatActivity audioMessage_Chat_Activity = AudioMessageChatActivity.this;
                audioMessage_Chat_Activity.dialog1 = audioMessage_Chat_Activity.builder1.create();
                AudioMessageChatActivity.this.dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                AudioMessageChatActivity.this.dialog1.show();
                ((TextView) AudioMessageChatActivity.this.dialog1.findViewById(R.id.dlang)).setText(LanguagesData.getSpeakLang()[AudioMessageChatActivity.this.fspinner.getSelectedItemPosition()]);
            }

            public void onError(int i) {
                String str;
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
                Toast.makeText(AudioMessageChatActivity.this.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                if (AudioMessageChatActivity.this.dialog1 != null) {
                    AudioMessageChatActivity.this.dialog1.dismiss();
                }
            }

            public void onResults(Bundle bundle) {
                ArrayList<String> stringArrayList = bundle.getStringArrayList("results_recognition");
                AudioMessageChatActivity.this.dialog1.dismiss();
                Log.e("===", "onResults: " + stringArrayList.get(0).trim());
                AudioMessageChatActivity.this.translate(stringArrayList.get(0).trim());
            }
        });
    }


    public void translate(String str) {
        Log.e("===", "Translate: ");
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.mProgressDialog = progressDialog;
        progressDialog.setMessage("Translating");
        this.mProgressDialog.setProgressStyle(0);
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.show();
        this.input1 = str;
        try {
            String encode = URLEncoder.encode(str, Key.STRING_CHARSET_NAME);
            ReadJSONFeedTask readJSONFeedTask = new ReadJSONFeedTask();
            readJSONFeedTask.execute(new String[]{"https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + LanguagesData.getsptrCode(this.fspinner.getSelectedItemPosition()) + "&tl=" + LanguagesData.getsptrCode(this.fspinner2.getSelectedItemPosition()) + "&dt=t&ie=UTF-8&oe=UTF-8&q=" + encode});
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void trans2() {
        SpeechRecognizer createSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.LANGUAGE", LanguagesData.getSpeakLangCode(this.fspinner2.getSelectedItemPosition()));
        intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{LanguagesData.getSpeakLangCode(this.fspinner2.getSelectedItemPosition())});
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
                AudioMessageChatActivity.this.builder1.setView(AudioMessageChatActivity.this.inflater.inflate(R.layout.speakdialog, (ViewGroup) null));
                AudioMessageChatActivity audioMessage_Chat_Activity = AudioMessageChatActivity.this;
                audioMessage_Chat_Activity.dialog1 = audioMessage_Chat_Activity.builder1.create();
                AudioMessageChatActivity.this.dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                AudioMessageChatActivity.this.dialog1.show();
                ((TextView) AudioMessageChatActivity.this.dialog1.findViewById(R.id.dlang)).setText(LanguagesData.getSpeakLang()[AudioMessageChatActivity.this.fspinner2.getSelectedItemPosition()]);
            }

            public void onError(int i) {
                String str;
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
                Toast.makeText(AudioMessageChatActivity.this.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                if (AudioMessageChatActivity.this.dialog1 != null) {
                    AudioMessageChatActivity.this.dialog1.dismiss();
                }
            }

            public void onResults(Bundle bundle) {
                ArrayList<String> stringArrayList = bundle.getStringArrayList("results_recognition");
                AudioMessageChatActivity.this.dialog1.dismiss();
                Log.e("===", "onResults: " + stringArrayList.get(0).trim());
                AudioMessageChatActivity.this.translate2(stringArrayList.get(0).trim());
            }
        });
    }


    public void translate2(String str) {
        Log.e("===", "Translate: ");
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.mProgressDialog = progressDialog;
        progressDialog.setMessage("Translating");
        this.mProgressDialog.setProgressStyle(0);
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.show();
        this.input2 = str;
        try {
            String encode = URLEncoder.encode(str, Key.STRING_CHARSET_NAME);
            ReadJSONFeedTask readJSONFeedTask = new ReadJSONFeedTask();
            readJSONFeedTask.execute(new String[]{"https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + LanguagesData.getsptrCode(this.fspinner2.getSelectedItemPosition()) + "&tl=" + LanguagesData.getsptrCode(this.fspinner.getSelectedItemPosition()) + "&dt=t&ie=UTF-8&oe=UTF-8&q=" + encode});
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
        private ReadJSONFeedTask() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public String doInBackground(String... strArr) {
            return AudioMessageChatActivity.this.readJSONFeed(strArr[0]);
        }

        public void onPostExecute(String str) {
            AudioMessageChatActivity.this.mProgressDialog.dismiss();
            if (str.equals("[\"ERROR\"]")) {
                Toast.makeText(AudioMessageChatActivity.this, "Sorry Something went Wrong", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONArray jSONArray = new JSONArray(str);
                String str2 = "";
                for (int i = 0; i < jSONArray.getJSONArray(0).length(); i++) {
                    str2 = str2 + jSONArray.getJSONArray(0).getJSONArray(i).getString(0);
                }
                if (AudioMessageChatActivity.this.trans == 0) {
                    AudioMessageChatActivity.this.chatItem.setLan1(AudioMessageChatActivity.this.input1);
                    AudioMessageChatActivity.this.chatItem.setStr1(str2);
                    AudioMessageChatActivity.this.chatItem.setType("receiver");
                    AudioMessageChatActivity.this.messageList.add(AudioMessageChatActivity.this.chatItem);
                    AudioMessageChatActivity.this.mMessageAdapter.notifyItemInserted(AudioMessageChatActivity.this.messageList.size() - 1);
                    if (AudioMessageChatActivity.this.messageList.size() == 0) {
                        AudioMessageChatActivity.this.text_empty.setVisibility(View.VISIBLE);
                        AudioMessageChatActivity.this.reyclerview_message_list.setVisibility(View.GONE);
                    } else {
                        AudioMessageChatActivity.this.text_empty.setVisibility(View.GONE);
                        AudioMessageChatActivity.this.reyclerview_message_list.setVisibility(View.VISIBLE);
                    }
                } else if (AudioMessageChatActivity.this.trans == 1) {
                    AudioMessageChatActivity.this.chatItem.setLan2(AudioMessageChatActivity.this.input2);
                    AudioMessageChatActivity.this.chatItem.setStr2(str2);
                    AudioMessageChatActivity.this.chatItem.setType("sender");
                    AudioMessageChatActivity.this.messageList.add(AudioMessageChatActivity.this.chatItem);
                    AudioMessageChatActivity.this.mMessageAdapter.notifyItemInserted(AudioMessageChatActivity.this.messageList.size() - 1);
                    if (AudioMessageChatActivity.this.messageList.size() == 0) {
                        AudioMessageChatActivity.this.text_empty.setVisibility(View.VISIBLE);
                        AudioMessageChatActivity.this.reyclerview_message_list.setVisibility(View.GONE);
                    } else {
                        AudioMessageChatActivity.this.text_empty.setVisibility(View.GONE);
                        AudioMessageChatActivity.this.reyclerview_message_list.setVisibility(View.VISIBLE);
                    }
                }
                AudioMessageChatActivity.this.mProgressDialog.dismiss();
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
}
