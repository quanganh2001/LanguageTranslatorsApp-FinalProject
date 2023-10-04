package com.example.englishhindidictionary.langauge_translator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.englishhindidictionary.R;
import com.example.englishhindidictionary.langauge_translator.dbhelper.Database;
import com.example.englishhindidictionary.langauge_translator.utildata.GetSetClass;
import java.util.ArrayList;

public class EnglishAdapter extends BaseAdapter {
    public static ArrayList<GetSetClass> wordsList = new ArrayList<>();
    TextView Words;
    Context context;
    LayoutInflater inflater = null;
    ListView f204lv;
    Database obj_cuznapps_Database;

    public long getItemId(int i) {
        return (long) i;
    }

    public EnglishAdapter(Context context2, ArrayList<GetSetClass> arrayList, ListView listView) {
        this.context = context2;
        this.f204lv = listView;
        wordsList = arrayList;
        this.inflater = (LayoutInflater) context2.getSystemService("layout_inflater");
        this.obj_cuznapps_Database = new Database(context2);
    }

    public int getCount() {
        return wordsList.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        String engWord = wordsList.get(i).getEngWord();
        View inflate = this.inflater.inflate(R.layout.item_english_adp, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.engWords);
        this.Words = textView;
        textView.setText(engWord);
        return inflate;
    }
}
