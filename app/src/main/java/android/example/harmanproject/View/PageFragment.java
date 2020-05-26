package android.example.harmanproject.View;

import android.example.harmanproject.Adapters.PageAdapter;
import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.Activity2ViewModel;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import it.xabaras.android.viewpagerindicator.widget.ViewPagerIndicator;

public class PageFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        PageAdapter pageAdapter = new PageAdapter(getContext(), Activity2ViewModel.exampleList);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(pageAdapter);

        // set viewPagerIndicator
        ViewPagerIndicator viewPagerIndicator = view.findViewById(R.id.view_pager_indicator);
        viewPagerIndicator.initWithViewPager(viewPager);

        return view;
    }

}



