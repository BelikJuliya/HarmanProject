package android.example.harmanproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

        ExampleElement current = mDataList.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.grid_example_element, parent, false);
        TextView textView = convertView.findViewById(R.id.grid_example_text);
        ImageView imageView = convertView.findViewById(R.id.grid_example_image);

        textView.setText(current.getText());
        //imageView.setImageResource(current.getImageResource());
        imageView.setImageURI(current.getImageResource());

        return convertView;

    }
}
