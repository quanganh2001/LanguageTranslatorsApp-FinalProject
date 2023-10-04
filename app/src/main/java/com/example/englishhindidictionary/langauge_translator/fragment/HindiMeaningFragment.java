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

public class HindiMeaningFragment extends Fragment {
    public static ArrayList<GetSetClass> HindiAll;
    public static String hWord;
    String array;
    JSONArray contacts = null;
    TextView hAntonym0;
    TextView hAntonym1;
    TextView hAntonym2;
    TextView hAntonym3;
    TextView hAntonym4;
    TextView hAntonym5;
    TextView hAntonym6;
    TextView hAntonym7;
    TextView hAntonym8;
    TextView hAntonym9;
    TextView hGrammer0;
    TextView hGrammer1;
    TextView hGrammer2;
    TextView hGrammer3;
    TextView hGrammer4;
    TextView hGrammer5;
    TextView hGrammer6;
    TextView hGrammer7;
    TextView hGrammer8;
    TextView hGrammer9;
    LinearLayout hLinearLayout0;
    LinearLayout hLinearLayout1;
    LinearLayout hLinearLayout2;
    LinearLayout hLinearLayout3;
    LinearLayout hLinearLayout4;
    LinearLayout hLinearLayout5;
    LinearLayout hLinearLayout6;
    LinearLayout hLinearLayout7;
    LinearLayout hLinearLayout8;
    LinearLayout hLinearLayout9;
    TextView hMeaning0;
    TextView hMeaning1;
    TextView hMeaning2;
    TextView hMeaning3;
    TextView hMeaning4;
    TextView hMeaning5;
    TextView hMeaning6;
    TextView hMeaning7;
    TextView hMeaning8;
    TextView hMeaning9;
    TextView hSynonym0;
    TextView hSynonym1;
    TextView hSynonym2;
    TextView hSynonym3;
    TextView hSynonym4;
    TextView hSynonym5;
    TextView hSynonym6;
    TextView hSynonym7;
    TextView hSynonym8;
    TextView hSynonym9;
    TextView hinWordView;
    Database hin_cuznapps_database;
    List<DictionaryUtils> listDitctionaryUtils;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_hindi_meaning, viewGroup, false);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        int i = MeaningActivity.wId;
        this.listDitctionaryUtils = new ArrayList();
        this.hinWordView = (TextView) getActivity().findViewById(R.id.hinWord);
        this.hin_cuznapps_database = new Database(getActivity());
        HindiAll = new ArrayList<>();
        ArrayList<GetSetClass> GetMeaningsByHinWord = this.hin_cuznapps_database.GetMeaningsByHinWord(i);
        HindiAll = GetMeaningsByHinWord;
        GetSetClass getSetClass = GetMeaningsByHinWord.get(0);
        String hinMeaning = getSetClass.getHinMeaning();
        String hinWord = getSetClass.getHinWord();
        hWord = hinWord;
        this.array = "{ str: " + hinMeaning + " }";
        this.hinWordView.setText(hinWord);
        new GetContact().execute(new Void[0]);
    }

    private class GetContact extends AsyncTask<Void, Void, Void> {
        private GetContact() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Void doInBackground(Void... voidArr) {
            try {
                JSONObject jSONObject = new JSONObject(HindiMeaningFragment.this.array);
                HindiMeaningFragment.this.contacts = jSONObject.getJSONArray("str");
                for (int i = 0; i < HindiMeaningFragment.this.contacts.length(); i++) {
                    JSONObject jSONObject2 = HindiMeaningFragment.this.contacts.getJSONObject(i);
                    DictionaryUtils dictionaryUtils = new DictionaryUtils();
                    String string = jSONObject2.getString("t");
                    String string2 = jSONObject2.getString("m");
                    String string3 = jSONObject2.getString("s");
                    dictionaryUtils.setAntonym(jSONObject2.getString("a"));
                    dictionaryUtils.setMean(string2);
                    dictionaryUtils.setSynonym(string3);
                    dictionaryUtils.setNoun(string);
                    HindiMeaningFragment.this.listDitctionaryUtils.add(dictionaryUtils);
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
            for (int i = 0; i < HindiMeaningFragment.this.listDitctionaryUtils.size(); i++) {
                DictionaryUtils dictionaryUtils = HindiMeaningFragment.this.listDitctionaryUtils.get(i);
                switch (i) {
                    case 0:
                        HindiMeaningFragment.this.setLayouts0();
                        HindiMeaningFragment.this.hGrammer0.setText(dictionaryUtils.getNoun());
                        HindiMeaningFragment.this.hMeaning0.setText(dictionaryUtils.getMean());
                        HindiMeaningFragment.this.hSynonym0.setText(dictionaryUtils.getSynonym());
                        HindiMeaningFragment.this.hAntonym0.setText(dictionaryUtils.getAntonym());
                        break;
                    case 1:
                        HindiMeaningFragment.this.setLayouts1();
                        HindiMeaningFragment.this.hGrammer1.setText(dictionaryUtils.getNoun());
                        HindiMeaningFragment.this.hMeaning1.setText(dictionaryUtils.getMean());
                        HindiMeaningFragment.this.hSynonym1.setText(dictionaryUtils.getSynonym());
                        HindiMeaningFragment.this.hAntonym1.setText(dictionaryUtils.getAntonym());
                        break;
                    case 2:
                        HindiMeaningFragment.this.setLayouts2();
                        HindiMeaningFragment.this.hGrammer2.setText(dictionaryUtils.getNoun());
                        HindiMeaningFragment.this.hMeaning2.setText(dictionaryUtils.getMean());
                        HindiMeaningFragment.this.hSynonym2.setText(dictionaryUtils.getSynonym());
                        HindiMeaningFragment.this.hAntonym2.setText(dictionaryUtils.getAntonym());
                        break;
                    case 3:
                        HindiMeaningFragment.this.setLayouts3();
                        HindiMeaningFragment.this.hGrammer3.setText(dictionaryUtils.getNoun());
                        HindiMeaningFragment.this.hMeaning3.setText(dictionaryUtils.getMean());
                        HindiMeaningFragment.this.hSynonym3.setText(dictionaryUtils.getSynonym());
                        HindiMeaningFragment.this.hAntonym3.setText(dictionaryUtils.getAntonym());
                        break;
                    case 4:
                        HindiMeaningFragment.this.setLayouts4();
                        HindiMeaningFragment.this.hGrammer4.setText(dictionaryUtils.getNoun());
                        HindiMeaningFragment.this.hMeaning4.setText(dictionaryUtils.getMean());
                        HindiMeaningFragment.this.hSynonym4.setText(dictionaryUtils.getSynonym());
                        HindiMeaningFragment.this.hAntonym4.setText(dictionaryUtils.getAntonym());
                        break;
                    case 5:
                        HindiMeaningFragment.this.setLayouts5();
                        HindiMeaningFragment.this.hGrammer5.setText(dictionaryUtils.getNoun());
                        HindiMeaningFragment.this.hMeaning5.setText(dictionaryUtils.getMean());
                        HindiMeaningFragment.this.hSynonym5.setText(dictionaryUtils.getSynonym());
                        HindiMeaningFragment.this.hAntonym5.setText(dictionaryUtils.getAntonym());
                        break;
                    case 6:
                        HindiMeaningFragment.this.setLayouts6();
                        HindiMeaningFragment.this.hGrammer6.setText(dictionaryUtils.getNoun());
                        HindiMeaningFragment.this.hMeaning6.setText(dictionaryUtils.getMean());
                        HindiMeaningFragment.this.hSynonym6.setText(dictionaryUtils.getSynonym());
                        HindiMeaningFragment.this.hAntonym6.setText(dictionaryUtils.getAntonym());
                        break;
                    case 7:
                        HindiMeaningFragment.this.setLayouts7();
                        HindiMeaningFragment.this.hGrammer7.setText(dictionaryUtils.getNoun());
                        HindiMeaningFragment.this.hMeaning7.setText(dictionaryUtils.getMean());
                        HindiMeaningFragment.this.hSynonym7.setText(dictionaryUtils.getSynonym());
                        HindiMeaningFragment.this.hAntonym7.setText(dictionaryUtils.getAntonym());
                        break;
                    case 8:
                        HindiMeaningFragment.this.setLayouts8();
                        HindiMeaningFragment.this.hGrammer8.setText(dictionaryUtils.getNoun());
                        HindiMeaningFragment.this.hMeaning8.setText(dictionaryUtils.getMean());
                        HindiMeaningFragment.this.hSynonym8.setText(dictionaryUtils.getSynonym());
                        HindiMeaningFragment.this.hAntonym8.setText(dictionaryUtils.getAntonym());
                        break;
                    case 9:
                        HindiMeaningFragment.this.setLayouts9();
                        HindiMeaningFragment.this.hGrammer9.setText(dictionaryUtils.getNoun());
                        HindiMeaningFragment.this.hMeaning9.setText(dictionaryUtils.getMean());
                        HindiMeaningFragment.this.hSynonym9.setText(dictionaryUtils.getSynonym());
                        HindiMeaningFragment.this.hAntonym9.setText(dictionaryUtils.getAntonym());
                        break;
                }
            }
        }
    }

    public void setLayouts0() {
        this.hLinearLayout0 = (LinearLayout) getActivity().findViewById(R.id.hGroup0);
        this.hGrammer0 = (TextView) getActivity().findViewById(R.id.hingrammer0);
        this.hMeaning0 = (TextView) getActivity().findViewById(R.id.hinmeaning0);
        this.hSynonym0 = (TextView) getActivity().findViewById(R.id.hinsynonym0);
        this.hAntonym0 = (TextView) getActivity().findViewById(R.id.hinantonym0);
        this.hLinearLayout0.setVisibility(View.VISIBLE);
    }

    public void setLayouts1() {
        this.hLinearLayout1 = (LinearLayout) getActivity().findViewById(R.id.hGroup1);
        this.hGrammer1 = (TextView) getActivity().findViewById(R.id.hingrammer1);
        this.hMeaning1 = (TextView) getActivity().findViewById(R.id.hinmeaning1);
        this.hSynonym1 = (TextView) getActivity().findViewById(R.id.hinsynonym1);
        this.hAntonym1 = (TextView) getActivity().findViewById(R.id.hinantonym1);
        this.hLinearLayout1.setVisibility(View.VISIBLE);
    }

    public void setLayouts2() {
        this.hLinearLayout2 = (LinearLayout) getActivity().findViewById(R.id.hGroup2);
        this.hGrammer2 = (TextView) getActivity().findViewById(R.id.hingrammer2);
        this.hMeaning2 = (TextView) getActivity().findViewById(R.id.hinmeaning2);
        this.hSynonym2 = (TextView) getActivity().findViewById(R.id.hinsynonym2);
        this.hAntonym2 = (TextView) getActivity().findViewById(R.id.hinantonym2);
        this.hLinearLayout2.setVisibility(View.VISIBLE);
    }

    public void setLayouts3() {
        this.hLinearLayout3 = (LinearLayout) getActivity().findViewById(R.id.hGroup3);
        this.hGrammer3 = (TextView) getActivity().findViewById(R.id.hingrammer3);
        this.hMeaning3 = (TextView) getActivity().findViewById(R.id.hinmeaning3);
        this.hSynonym3 = (TextView) getActivity().findViewById(R.id.hinsynonym3);
        this.hAntonym3 = (TextView) getActivity().findViewById(R.id.hinantonym3);
        this.hLinearLayout3.setVisibility(View.VISIBLE);
    }

    public void setLayouts4() {
        this.hLinearLayout4 = (LinearLayout) getActivity().findViewById(R.id.hGroup4);
        this.hGrammer4 = (TextView) getActivity().findViewById(R.id.hingrammer4);
        this.hMeaning4 = (TextView) getActivity().findViewById(R.id.hinmeaning4);
        this.hSynonym4 = (TextView) getActivity().findViewById(R.id.hinsynonym4);
        this.hAntonym4 = (TextView) getActivity().findViewById(R.id.hinantonym4);
        this.hLinearLayout4.setVisibility(View.VISIBLE);
    }

    public void setLayouts5() {
        this.hLinearLayout5 = (LinearLayout) getActivity().findViewById(R.id.hGroup5);
        this.hGrammer5 = (TextView) getActivity().findViewById(R.id.hingrammer5);
        this.hMeaning5 = (TextView) getActivity().findViewById(R.id.hinmeaning5);
        this.hSynonym5 = (TextView) getActivity().findViewById(R.id.hinsynonym5);
        this.hAntonym5 = (TextView) getActivity().findViewById(R.id.hinantonym5);
        this.hLinearLayout5.setVisibility(View.VISIBLE);
    }

    public void setLayouts6() {
        this.hLinearLayout6 = (LinearLayout) getActivity().findViewById(R.id.hGroup6);
        this.hGrammer6 = (TextView) getActivity().findViewById(R.id.hingrammer6);
        this.hMeaning6 = (TextView) getActivity().findViewById(R.id.hinmeaning6);
        this.hSynonym6 = (TextView) getActivity().findViewById(R.id.hinsynonym6);
        this.hAntonym6 = (TextView) getActivity().findViewById(R.id.hinantonym6);
        this.hLinearLayout6.setVisibility(View.VISIBLE);
    }

    public void setLayouts7() {
        this.hLinearLayout7 = (LinearLayout) getActivity().findViewById(R.id.hGroup7);
        this.hGrammer7 = (TextView) getActivity().findViewById(R.id.hingrammer7);
        this.hMeaning7 = (TextView) getActivity().findViewById(R.id.hinmeaning7);
        this.hSynonym7 = (TextView) getActivity().findViewById(R.id.hinsynonym7);
        this.hAntonym7 = (TextView) getActivity().findViewById(R.id.hinantonym7);
        this.hLinearLayout7.setVisibility(View.VISIBLE);
    }

    public void setLayouts8() {
        this.hLinearLayout8 = (LinearLayout) getActivity().findViewById(R.id.hGroup8);
        this.hGrammer8 = (TextView) getActivity().findViewById(R.id.hingrammer8);
        this.hMeaning8 = (TextView) getActivity().findViewById(R.id.hinmeaning8);
        this.hSynonym8 = (TextView) getActivity().findViewById(R.id.hinsynonym8);
        this.hAntonym8 = (TextView) getActivity().findViewById(R.id.hinantonym8);
        this.hLinearLayout8.setVisibility(View.VISIBLE);
    }

    public void setLayouts9() {
        this.hLinearLayout9 = (LinearLayout) getActivity().findViewById(R.id.hGroup9);
        this.hGrammer9 = (TextView) getActivity().findViewById(R.id.hingrammer9);
        this.hMeaning9 = (TextView) getActivity().findViewById(R.id.hinmeaning9);
        this.hSynonym9 = (TextView) getActivity().findViewById(R.id.hinsynonym9);
        this.hAntonym9 = (TextView) getActivity().findViewById(R.id.hinantonym9);
        this.hLinearLayout9.setVisibility(View.VISIBLE);
    }
}
