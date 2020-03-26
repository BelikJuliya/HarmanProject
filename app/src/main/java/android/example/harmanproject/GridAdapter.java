package android.example.harmanproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<String> dataList;

    public GridAdapter (Context context, ArrayList <String> dataList){
        mContext = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView label = (TextView) convertView;

        if (convertView == null){
            convertView = new TextView(mContext);
            label = (TextView) convertView;
        }
        label.setText(dataList.get(position));
        return convertView;


//        TextView textView = new TextView(mContext);
//        textView.setText(String.valueOf(position));
//        return textView;

//        final String item = dataList.get(position);
//        if (convertView == null){
//            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
//            convertView = layoutInflater.inflate(R.layout.item_view, null);
//        }
//        return convertView;
    }
}
