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

    ArrayList<String> list;
    Context context;

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public PageAdapter(Context context, ArrayList<String> list) {
//        super(manager);
        this.context = context;
        this.list = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.grid_view_element, container, false);
        TextView textView = view.findViewById(R.id.grid_item);
        textView.setText(list.get(position));
        container.addView(view);
        return view;
    }

//
//    @Override
//    public Fragment getItem(int position) {
//        return PageFragment.newInstance(Activity2.generateValues().get(position));
//    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
