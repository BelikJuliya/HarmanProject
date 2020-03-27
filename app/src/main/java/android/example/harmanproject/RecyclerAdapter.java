package android.example.harmanproject;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList <String> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout textView;

        public MyViewHolder(LinearLayout textView) {
            super(textView);
            this.textView = textView;
        }
    }

    public RecyclerAdapter (ArrayList <String> myDataSet) {
        mDataset = myDataSet;
    }

    // ссылки на гривью и гридайтем - не ошибка, макет одного элемента для грид айтема и ресайкл айтема одинаковый

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout textView = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_element, parent, false);

        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ((TextView) holder.textView.findViewById(R.id.grid_item)).setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
