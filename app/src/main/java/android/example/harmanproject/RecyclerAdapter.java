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

    static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout textView;

        MyViewHolder(LinearLayout textView) {
            super(textView);
            this.textView = textView;
        }
    }

    RecyclerAdapter(ArrayList<String> myDataSet) {
        mDataset = myDataSet;
    }
    /*using gridView and gridItem links is not a mistake
    because the layout of one element for grid item and recycle item is the same */

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
