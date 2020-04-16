package android.example.harmanproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView recyclerView;
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(Activity2.exampleList);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnClickListener(new RecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), Activity3.class);
                intent.putExtra("Example element", Activity2.exampleList.get(position));
                startActivity(intent);
                //Activity2.exampleList.get(position).
            }
        });
        return view;
    }
}


