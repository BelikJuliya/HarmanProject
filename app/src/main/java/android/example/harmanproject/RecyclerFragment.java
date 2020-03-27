package android.example.harmanproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//@Override
//protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        recyclerView = findViewById(R.id.recycle_view);
//        recyclerView.setHasFixedSize(true);
//
//        initRecycleView();
//
//        MyAdapter myAdapter = new MyAdapter(generateValues());
//        recyclerView.setAdapter(myAdapter);
//        }

public class RecyclerFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //        return inflater.inflate(R.layout.fragment1, null);

        View rootView = inflater.inflate(R.layout.recycler_fragment, container, false);
//        setContentView(R.layout.fragment1);
        recyclerView = rootView.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(Activity2.generateValues());
        recyclerView.setAdapter(recyclerAdapter);
        return rootView;
    }
}


