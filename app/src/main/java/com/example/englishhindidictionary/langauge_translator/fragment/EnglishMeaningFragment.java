package com.example.englishhindidictionary.langauge_translator.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.englishhindidictionary.R;
import com.example.englishhindidictionary.langauge_translator.MeaningActivity;
import com.example.englishhindidictionary.langauge_translator.dbhelper.Database;
import com.example.englishhindidictionary.langauge_translator.utildata.DictionaryUtils;
import com.example.englishhindidictionary.langauge_translator.utildata.GetSetClass;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EnglishMeaningFragment extends Fragment {
    public static ArrayList<GetSetClass> EngAll;
    public static String engWord;
    public static String forHindi;

    public String array;

    public JSONArray contacts = null;

    public TextView enAntonym0, enAntonym1, enAntonym2, enAntonym3, enAntonym4, enAntonym5, enAntonym6, enAntonym7, enAntonym8, enAntonym9, enGrammer0, enGrammer1, enGrammer2, enGrammer3, enGrammer4, enGrammer5, enGrammer6, enGrammer7, enGrammer8, enGrammer9;
    public LinearLayout enLinearLayout0;
    public LinearLayout enLinearLayout1;
    public LinearLayout enLinearLayout2;
    public LinearLayout enLinearLayout3;
    public LinearLayout enLinearLayout4;
    public LinearLayout enLinearLayout5;
    public LinearLayout enLinearLayout6;
    public LinearLayout enLinearLayout7;
    public LinearLayout enLinearLayout8;
    public LinearLayout enLinearLayout9;

    public TextView enMeaning0, enMeaning1, enMeaning2, enMeaning3, enMeaning4, enMeaning5, enMeaning6, enMeaning7, enMeaning8, enMeaning9, enSynonym0, enSynonym1, enSynonym2, enSynonym3, enSynonym4, enSynonym5, enSynonym6, enSynonym7, enSynonym8, enSynonym9;
    public TextView engWordView;
    public Database eng_cuznapps_database;
    public List<DictionaryUtils> listDitctionaryUtils;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_eng_meaning, viewGroup, false);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        forHindi = HindiMeaningFragment.hWord;
        int i = MeaningActivity.wId;
        this.listDitctionaryUtils = new ArrayList();
        this.eng_cuznapps_database = new Database(getActivity());
        EngAll = new ArrayList<>();
        EngAll = this.eng_cuznapps_database.GetMeaningsByEngWord(i);
        this.engWordView = (TextView) getActivity().findViewById(R.id.word);
        GetSetClass getSetClass = EngAll.get(0);
        String engMeaning = getSetClass.getEngMeaning();
        engWord = getSetClass.getEngWord();
        this.array = "{ str: " + engMeaning + " }";
        this.engWordView.setText(engWord);
        this.eng_cuznapps_database.InsertHis(i, engWord, forHindi);
        new GetContacts().execute(new Void[0]);
    }

    public void setLayouts0() {
        this.enLinearLayout0 = (LinearLayout) getActivity().findViewById(R.id.engGroup0);
        this.enGrammer0 = (TextView) getActivity().findViewById(R.id.enGrammer0);
        this.enMeaning0 = (TextView) getActivity().findViewById(R.id.enMeaning0);
        this.enSynonym0 = (TextView) getActivity().findViewById(R.id.enSynonym0);
        this.enAntonym0 = (TextView) getActivity().findViewById(R.id.enAntonym0);
        this.enLinearLayout0.setVisibility(View.VISIBLE);
    }

    public void setLayouts1() {
        this.enLinearLayout1 = (LinearLayout) getActivity().findViewById(R.id.engGroup1);
        this.enGrammer1 = (TextView) getActivity().findViewById(R.id.enGrammer1);
        this.enMeaning1 = (TextView) getActivity().findViewById(R.id.enMeaning1);
        this.enSynonym1 = (TextView) getActivity().findViewById(R.id.enSynonym1);
        this.enAntonym1 = (TextView) getActivity().findViewById(R.id.enAntonym1);
        this.enLinearLayout1.setVisibility(View.VISIBLE);
    }

    public void setLayouts2() {
        this.enLinearLayout2 = (LinearLayout) getActivity().findViewById(R.id.engGroup2);
        this.enGrammer2 = (TextView) getActivity().findViewById(R.id.enGrammer2);
        this.enMeaning2 = (TextView) getActivity().findViewById(R.id.enMeaning2);
        this.enSynonym2 = (TextView) getActivity().findViewById(R.id.enSynonym2);
        this.enAntonym2 = (TextView) getActivity().findViewById(R.id.enAntonym2);
        this.enLinearLayout2.setVisibility(View.VISIBLE);
    }

    public void setLayouts3() {
        this.enLinearLayout3 = (LinearLayout) getActivity().findViewById(R.id.engGroup3);
        this.enGrammer3 = (TextView) getActivity().findViewById(R.id.enGrammer3);
        this.enMeaning3 = (TextView) getActivity().findViewById(R.id.enMeaning3);
        this.enSynonym3 = (TextView) getActivity().findViewById(R.id.enSynonym3);
        this.enAntonym3 = (TextView) getActivity().findViewById(R.id.enAntonym3);
        this.enLinearLayout3.setVisibility(View.VISIBLE);
    }

    public void setLayouts4() {
        this.enLinearLayout4 = (LinearLayout) getActivity().findViewById(R.id.engGroup4);
        this.enGrammer4 = (TextView) getActivity().findViewById(R.id.enGrammer4);
        this.enMeaning4 = (TextView) getActivity().findViewById(R.id.enMeaning4);
        this.enSynonym4 = (TextView) getActivity().findViewById(R.id.enSynonym4);
        this.enAntonym4 = (TextView) getActivity().findViewById(R.id.enAntonym4);
        this.enLinearLayout4.setVisibility(View.VISIBLE);
    }

    public void setLayouts5() {
        this.enLinearLayout5 = (LinearLayout) getActivity().findViewById(R.id.engGroup5);
        this.enGrammer5 = (TextView) getActivity().findViewById(R.id.enGrammer5);
        this.enMeaning5 = (TextView) getActivity().findViewById(R.id.enMeaning5);
        this.enSynonym5 = (TextView) getActivity().findViewById(R.id.enSynonym5);
        this.enAntonym5 = (TextView) getActivity().findViewById(R.id.enAntonym5);
        this.enLinearLayout5.setVisibility(View.VISIBLE);
    }

    public void setLayouts6() {
        this.enLinearLayout6 = (LinearLayout) getActivity().findViewById(R.id.engGroup6);
        this.enGrammer6 = (TextView) getActivity().findViewById(R.id.enGrammer6);
        this.enMeaning6 = (TextView) getActivity().findViewById(R.id.enMeaning6);
        this.enSynonym6 = (TextView) getActivity().findViewById(R.id.enSynonym6);
        this.enAntonym6 = (TextView) getActivity().findViewById(R.id.enAntonym6);
        this.enLinearLayout6.setVisibility(View.VISIBLE);
    }

    public void setLayouts7() {
        this.enLinearLayout7 = (LinearLayout) getActivity().findViewById(R.id.engGroup7);
        this.enGrammer7 = (TextView) getActivity().findViewById(R.id.enGrammer7);
        this.enMeaning7 = (TextView) getActivity().findViewById(R.id.enMeaning7);
        this.enSynonym7 = (TextView) getActivity().findViewById(R.id.enSynonym7);
        this.enAntonym7 = (TextView) getActivity().findViewById(R.id.enAntonym7);
        this.enLinearLayout7.setVisibility(View.VISIBLE);
    }

    public void setLayouts8() {
        this.enLinearLayout8 = (LinearLayout) getActivity().findViewById(R.id.engGroup8);
        this.enGrammer8 = (TextView) getActivity().findViewById(R.id.enGrammer8);
        this.enMeaning8 = (TextView) getActivity().findViewById(R.id.enMeaning8);
        this.enSynonym8 = (TextView) getActivity().findViewById(R.id.enSynonym8);
        this.enAntonym8 = (TextView) getActivity().findViewById(R.id.enAntonym8);
        this.enLinearLayout8.setVisibility(View.VISIBLE);
    }

    public void setLayouts9() {
        this.enLinearLayout9 = (LinearLayout) getActivity().findViewById(R.id.engGroup9);
        this.enGrammer9 = (TextView) getActivity().findViewById(R.id.enGrammer9);
        this.enMeaning9 = (TextView) getActivity().findViewById(R.id.enMeaning9);
        this.enSynonym9 = (TextView) getActivity().findViewById(R.id.enSynonym9);
        this.enAntonym9 = (TextView) getActivity().findViewById(R.id.enAntonym9);
        this.enLinearLayout9.setVisibility(View.VISIBLE);
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        private GetContacts() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Void doInBackground(Void... voidArr) {
            try {
                JSONArray unused = EnglishMeaningFragment.this.contacts = new JSONObject(EnglishMeaningFragment.this.array).getJSONArray("str");
                for (int i = 0; i < EnglishMeaningFragment.this.contacts.length(); i++) {
                    JSONObject jSONObject = EnglishMeaningFragment.this.contacts.getJSONObject(i);
                    DictionaryUtils dictionaryUtils = new DictionaryUtils();
                    String string = jSONObject.getString("t");
                    String string2 = jSONObject.getString("m");
                    String string3 = jSONObject.getString("s");
                    dictionaryUtils.setAntonym(jSONObject.getString("a"));
                    dictionaryUtils.setMean(string2);
                    dictionaryUtils.setSynonym(string3);
                    dictionaryUtils.setNoun(string);
                    EnglishMeaningFragment.this.listDitctionaryUtils.add(dictionaryUtils);
                }
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            new DictionaryUtils();
            for (int i = 0; i < EnglishMeaningFragment.this.listDitctionaryUtils.size(); i++) {
                DictionaryUtils dictionaryUtils = EnglishMeaningFragment.this.listDitctionaryUtils.get(i);
                switch (i) {
                    case 0:
                        EnglishMeaningFragment.this.setLayouts0();
                        EnglishMeaningFragment.this.enGrammer0.setText(dictionaryUtils.getNoun());
                        EnglishMeaningFragment.this.enMeaning0.setText(dictionaryUtils.getMean());
                        EnglishMeaningFragment.this.enSynonym0.setText(dictionaryUtils.getSynonym());
                        EnglishMeaningFragment.this.enAntonym0.setText(dictionaryUtils.getAntonym());
                        break;
                    case 1:
                        EnglishMeaningFragment.this.setLayouts1();
                        EnglishMeaningFragment.this.enGrammer1.setText(dictionaryUtils.getNoun());
                        EnglishMeaningFragment.this.enMeaning1.setText(dictionaryUtils.getMean());
                        EnglishMeaningFragment.this.enSynonym1.setText(dictionaryUtils.getSynonym());
                        EnglishMeaningFragment.this.enAntonym1.setText(dictionaryUtils.getAntonym());
                        break;
                    case 2:
                        EnglishMeaningFragment.this.setLayouts2();
                        EnglishMeaningFragment.this.enGrammer2.setText(dictionaryUtils.getNoun());
                        EnglishMeaningFragment.this.enMeaning2.setText(dictionaryUtils.getMean());
                        EnglishMeaningFragment.this.enSynonym2.setText(dictionaryUtils.getSynonym());
                        EnglishMeaningFragment.this.enAntonym2.setText(dictionaryUtils.getAntonym());
                        break;
                    case 3:
                        EnglishMeaningFragment.this.setLayouts3();
                        EnglishMeaningFragment.this.enGrammer3.setText(dictionaryUtils.getNoun());
                        EnglishMeaningFragment.this.enMeaning3.setText(dictionaryUtils.getMean());
                        EnglishMeaningFragment.this.enSynonym3.setText(dictionaryUtils.getSynonym());
                        EnglishMeaningFragment.this.enAntonym3.setText(dictionaryUtils.getAntonym());
                        break;
                    case 4:
                        EnglishMeaningFragment.this.setLayouts4();
                        EnglishMeaningFragment.this.enGrammer4.setText(dictionaryUtils.getNoun());
                        EnglishMeaningFragment.this.enMeaning4.setText(dictionaryUtils.getMean());
                        EnglishMeaningFragment.this.enSynonym4.setText(dictionaryUtils.getSynonym());
                        EnglishMeaningFragment.this.enAntonym4.setText(dictionaryUtils.getAntonym());
                        break;
                    case 5:
                        EnglishMeaningFragment.this.setLayouts5();
                        EnglishMeaningFragment.this.enGrammer5.setText(dictionaryUtils.getNoun());
                        EnglishMeaningFragment.this.enMeaning5.setText(dictionaryUtils.getMean());
                        EnglishMeaningFragment.this.enSynonym5.setText(dictionaryUtils.getSynonym());
                        EnglishMeaningFragment.this.enAntonym5.setText(dictionaryUtils.getAntonym());
                        break;
                    case 6:
                        EnglishMeaningFragment.this.setLayouts6();
                        EnglishMeaningFragment.this.enGrammer6.setText(dictionaryUtils.getNoun());
                        EnglishMeaningFragment.this.enMeaning6.setText(dictionaryUtils.getMean());
                        EnglishMeaningFragment.this.enSynonym6.setText(dictionaryUtils.getSynonym());
                        EnglishMeaningFragment.this.enAntonym6.setText(dictionaryUtils.getAntonym());
                        break;
                    case 7:
                        EnglishMeaningFragment.this.setLayouts7();
                        EnglishMeaningFragment.this.enGrammer7.setText(dictionaryUtils.getNoun());
                        EnglishMeaningFragment.this.enMeaning7.setText(dictionaryUtils.getMean());
                        EnglishMeaningFragment.this.enSynonym7.setText(dictionaryUtils.getSynonym());
                        EnglishMeaningFragment.this.enAntonym7.setText(dictionaryUtils.getAntonym());
                        break;
                    case 8:
                        EnglishMeaningFragment.this.setLayouts8();
                        EnglishMeaningFragment.this.enGrammer8.setText(dictionaryUtils.getNoun());
                        EnglishMeaningFragment.this.enMeaning8.setText(dictionaryUtils.getMean());
                        EnglishMeaningFragment.this.enSynonym8.setText(dictionaryUtils.getSynonym());
                        EnglishMeaningFragment.this.enAntonym8.setText(dictionaryUtils.getAntonym());
                        break;
                    case 9:
                        EnglishMeaningFragment.this.setLayouts9();
                        EnglishMeaningFragment.this.enGrammer9.setText(dictionaryUtils.getNoun());
                        EnglishMeaningFragment.this.enMeaning9.setText(dictionaryUtils.getMean());
                        EnglishMeaningFragment.this.enSynonym9.setText(dictionaryUtils.getSynonym());
                        EnglishMeaningFragment.this.enAntonym9.setText(dictionaryUtils.getAntonym());
                        break;
                }
            }
        }
    }
}
