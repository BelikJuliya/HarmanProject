package android.example.harmanproject.View;

import android.example.harmanproject.Adapters.PageAdapter;
import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.GalleryViewModel;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import it.xabaras.android.viewpagerindicator.widget.ViewPagerIndicator;

public class PageView extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_viewer, container, false);
        PageAdapter pageAdapter = new PageAdapter(getContext(), GalleryViewModel.exampleList);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(pageAdapter);

        // set viewPagerIndicator
        ViewPagerIndicator viewPagerIndicator = view.findViewById(R.id.view_pager_indicator);
        viewPagerIndicator.initWithViewPager(viewPager);

        return view;
    }

}



