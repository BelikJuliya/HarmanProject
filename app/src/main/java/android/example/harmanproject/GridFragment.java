package android.example.harmanproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class GridFragment extends Fragment {

    Context context;
    GridView gridView;
    GridAdapter gridAdapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savesInstanceState) {
        context = inflater.getContext(); //what does it do?
        //getView() возвращает то, что в return у onCreateView - не использовать
        //мой фрагмент будет связан с xml, ссылка на айди которого вводится
        //на этом xml будет искаться грид вью на след шаге
        view = inflater.inflate(R.layout.grid_fragment, container, false);
        //ищем в переданном xml грид вью, который передаем в качестве параметра
        gridView = view.findViewById(R.id.grid_view);
        gridAdapter = new GridAdapter(context, Activity2.generateValues());
        gridView.setAdapter(gridAdapter);
        return view;
    }
}
