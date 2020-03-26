//package android.example.harmanproject;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.GridView;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//
//public class GridFragment extends Fragment {
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