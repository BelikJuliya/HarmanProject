package android.example.harmanproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<ExampleElement> mDataList;

    GridAdapter(Context context, ArrayList<ExampleElement> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView label = (TextView) convertView;


        if (convertView == null) {
            convertView = new TextView(mContext);
            label = (TextView) convertView;
        }
        label.setText(mDataList.get(position));
        return convertView;

    }
}
