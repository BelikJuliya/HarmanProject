package android.example.harmanproject.View;

import android.content.Intent;
import android.example.harmanproject.Adapters.GridAdapter;
import android.example.harmanproject.ViewModel.GalleryViewModel;
import android.example.harmanproject.databinding.GrigViewBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class GridViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savesInstanceState) {
        /*
        my fragment will be associated with xml, the id link of which is entered here,
        in this xml file the grid view will be searched in the next step
         */
        GrigViewBinding binding = GrigViewBinding.inflate(inflater, container, false);

        /* search for gridView (R.id.grid_view) in XML file
        which link was given earlier (R.layout.grid_view)
        */
        android.widget.GridView gridView = binding.gridView;
        GridAdapter gridAdapter = new GridAdapter(getActivity(), GalleryViewModel.exampleList);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getContext(), MetadataActivity.class);
            intent.putExtra("Example element", GalleryViewModel.exampleList.get(position));
            startActivity(intent);
        });
        return binding.getRoot();

    }
}
