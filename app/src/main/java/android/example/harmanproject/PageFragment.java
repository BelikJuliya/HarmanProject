package android.example.harmanproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import it.xabaras.android.viewpagerindicator.widget.ViewPagerIndicator;

public class PageFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        PageAdapter pageAdapter = new PageAdapter(getContext(), Activity2.exampleList);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(pageAdapter);

        // set viewPagerIndicator
        ViewPagerIndicator viewPagerIndicator = view.findViewById(R.id.view_pager_indicator);
        viewPagerIndicator.initWithViewPager(viewPager);


        return view;
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(getContext(), Activity3.class);
//        intent.putExtra("Example element", Activity2.exampleList.get(position));
//        startActivity(intent);
//    }
}



