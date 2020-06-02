package android.example.harmanproject.View;

import android.example.harmanproject.Adapters.PageAdapter;
import android.example.harmanproject.ViewModel.GalleryViewModel;
import android.example.harmanproject.databinding.PageViewerBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import it.xabaras.android.viewpagerindicator.widget.ViewPagerIndicator;

public class PageViewFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PageViewerBinding binding = PageViewerBinding.inflate(inflater, container, false);
        PageAdapter pageAdapter = new PageAdapter(getContext(), GalleryViewModel.exampleList);

        ViewPager viewPager = binding.viewpager;
        viewPager.setAdapter(pageAdapter);

        // set viewPagerIndicator
        ViewPagerIndicator indicator = binding.viewPagerIndicator;
        indicator.initWithViewPager(viewPager);

        return binding.getRoot();
    }

}



