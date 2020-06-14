package android.example.harmanproject.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.example.harmanproject.ViewModel.DemoItemForAdapters;
import android.example.harmanproject.databinding.GridExampleElementBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<DemoItemForAdapters> mDataList;

    public GridAdapter(Context context, ArrayList<DemoItemForAdapters> dataList) {
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DemoItemForAdapters current = mDataList.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        GridExampleElementBinding binding = GridExampleElementBinding.inflate(inflater, parent, false);
        convertView = binding.getRoot();
        TextView textView = binding.gridExampleText;
        ImageView imageView = binding.gridExampleImage;

        textView.setText(current.getText());
        //imageView.setImageURI(current.getImageResource());
        Glide.with(mContext).load(current.getImageUri()).into(imageView);

        return convertView;

    }

}
