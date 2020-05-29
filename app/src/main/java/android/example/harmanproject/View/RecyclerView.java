package android.example.harmanproject.View;

import android.content.Intent;
import android.example.harmanproject.Adapters.RecyclerAdapter;
import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.GalleryViewModel;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class RecyclerView extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        RecyclerFragmentBinding binding = RecyclerFragmentBinding.inflate(inflater, container, false);
//        androidx.recyclerview.widget.RecyclerView recyclerView = binding.recycleView;
//        View view = binding.getRoot();
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        androidx.recyclerview.widget.RecyclerView recyclerView = view.findViewById(R.id.recycle_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(GalleryViewModel.exampleList);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnClickListener(new RecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), PictMetadata.class);
                intent.putExtra("Example element", GalleryViewModel.exampleList.get(position));
                startActivity(intent);

            }
        });
        return view;
    }
}


