package android.example.harmanproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.example.harmanproject.R;
import android.example.harmanproject.View.Activity3;
import android.example.harmanproject.ViewModel.Activity2ViewModel;
import android.example.harmanproject.ViewModel.ExampleElement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends PagerAdapter {

    private ArrayList<ExampleElement> mList;
    private Context mContext;

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public PageAdapter(Context context, ArrayList<ExampleElement> list) {
        mContext = context;
        mList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ExampleElement current = mList.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pager_example_element, container, false);
        TextView textView = view.findViewById(R.id.page_example_text);
        textView.setText(current.getText());
        ImageView imageView = view.findViewById(R.id.page_example_image);
        imageView.setImageURI(current.getImageResource());
        container.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Activity3.class);
                intent.putExtra("Example element", Activity2ViewModel.exampleList.get(position));
                mContext.startActivity(intent);
            }
        });
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
