package android.example.harmanproject.View;

import android.content.Intent;
import android.example.harmanproject.Adapters.GridAdapter;
import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.GalleryViewModel;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

public class GridView extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savesInstanceState) {


        /*
        my fragment will be associated with xml, the id link of which is entered here,
        in this xml file the grid view will be searched in the next step
         */
        View view = inflater.inflate(R.layout.grid_fragment, container, false);

        /* search for gridView (R.id.grid_view) in XML file
        which link was given earlier (R.layout.grid_fragment)
        */
        android.widget.GridView mGridView = view.findViewById(R.id.grid_view);
        GridAdapter gridAdapter = new GridAdapter(getActivity(), GalleryViewModel.exampleList);
        mGridView.setAdapter(gridAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PictMetadata.class);
                intent.putExtra("Example element", GalleryViewModel.exampleList.get(position));
                startActivity(intent);
            }
        });
        return view;

    }
}
