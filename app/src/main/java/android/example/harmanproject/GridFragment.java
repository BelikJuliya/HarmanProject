package android.example.harmanproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
//    private TextView textView;
//    private GridAdapter gridAdapter;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textView = findViewById(R.id.list_item_text1);
//        final GridView gridView = (GridView) findViewById(R.id.grid_view);
//        gridAdapter = new GridAdapter(getApplicationContext(), generateValues());
//        gridView.setAdapter(gridAdapter);


public class GridFragment extends Fragment {

    private GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity2, container, false);
        rootView.findViewById(R.id.grid_view);
        GridAdapter gridAdapter = new GridAdapter(getActivity(), Activity2.generateValues());
        gridView.setAdapter(gridAdapter);

        return rootView;
    }

}
//    Context context;
//    GridView gridView;
//    GridAdapter gridAdapter;
//    View view;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savesInstanceState) {
//        context = inflater.getContext(); //what does it do?
//        view =  inflater.inflate(R.layout.grid_fragment, container, false);
//        gridView = getView().findViewById(R.id.grid_view);
//        gridAdapter = new GridAdapter(context, Activity2.generateValues());
//        gridView.setAdapter(gridAdapter);
//        return view;
//    }
//}



//        View view = inflater.inflate(R.layout.grid_view_element, container, false);
//        gridAdapter = new GridAdapter(context, Activity2.generateValues());
//        gridView = getView().findViewById(R.id.grid_item);
//        gridView.setAdapter(gridAdapter);
//        return view;
//    }
//}



//
//    // consolidated project
//
//    private TextView textView; //gr
//
//    private GridFragment gridView;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.grid_fragment, container, false);
//
//        textView = (TextView) inflater.inflate(R.layout.grid_fragment, container, false);
//        textView.findViewById(R.id.list_item_text);
//        gridView.
//
//
//        //GridView gridView = (GridView) inflater.inflate(R.id.grid_view, textView)
//
//
//
//        rootView.findViewById(R.id.grid_view);
//
//        gridView
//
//        GridAdapter gridAdapter = new GridAdapter(getActivity(), Activity2.generateValues());
//        gridView.setAdapter(gridAdapter);
//
//        return rootView;
//    }
//
//    // the same from gridView project
//
//    private TextView textView;
//    private GridAdapter gridAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textView = findViewById(R.id.list_item_text1);
//        final GridView gridView = (GridView) findViewById(R.id.grid_view);
//        gridAdapter = new GridAdapter(getApplicationContext(), generateValues());
//        gridView.setAdapter(gridAdapter);
//
//
//}
//
//
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                             Bundle savedInstanceState) {
////        return inflater.inflate(R.layout.grid_fragment, null);
////    }