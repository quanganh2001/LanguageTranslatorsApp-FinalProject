package com.example.englishhindidictionary.langauge_translator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.englishhindidictionary.R;
import com.example.englishhindidictionary.langauge_translator.dbhelper.DatabaseHelper;
import com.example.englishhindidictionary.langauge_translator.utildata.HistoryItem;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    public List<HistoryItem> dataList;
    DatabaseHelper dbHelper;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView delete;
        public TextView lang1;
        public TextView lang2;
        public TextView str1;
        public TextView str2;

        public MyViewHolder(View view) {
            super(view);
            this.lang1 = (TextView) view.findViewById(R.id.lang1);
            this.str1 = (TextView) view.findViewById(R.id.str1);
            this.lang2 = (TextView) view.findViewById(R.id.lang2);
            this.str2 = (TextView) view.findViewById(R.id.str2);
            this.delete = (ImageView) view.findViewById(R.id.delete);
        }
    }

    public HistoryAdapter(Context context, List<HistoryItem> list) {
        this.mContext = context;
        this.dataList = list;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.dbHelper = databaseHelper;
        databaseHelper.openDataBase();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_row, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        final HistoryItem historyItem = this.dataList.get(i);
        myViewHolder.lang1.setText(historyItem.getLan1());
        myViewHolder.str1.setText(historyItem.getStr1());
        myViewHolder.lang2.setText(historyItem.getLan2());
        myViewHolder.str2.setText(historyItem.getStr2());
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (HistoryAdapter.this.dbHelper.Delete_History_id(historyItem.getId())) {
                    HistoryAdapter.this.dataList.remove(i);
                    HistoryAdapter.this.notifyItemRemoved(i);
                    HistoryAdapter historyAdapter = HistoryAdapter.this;
                    historyAdapter.notifyItemRangeChanged(i, historyAdapter.dataList.size());
                    return;
                }
                Toast.makeText(HistoryAdapter.this.mContext, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getItemCount() {
        return this.dataList.size();
    }
}
