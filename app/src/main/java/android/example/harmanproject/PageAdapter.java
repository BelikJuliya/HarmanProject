package android.example.harmanproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends PagerAdapter {

    private ArrayList<String> mList;
    private Context mcontext;

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    PageAdapter(Context context, ArrayList<String> list) {
        mcontext = context;
        mList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.example_element, container, false);
        TextView textView = view.findViewById(R.id.grid_item);
        textView.setText(mList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
