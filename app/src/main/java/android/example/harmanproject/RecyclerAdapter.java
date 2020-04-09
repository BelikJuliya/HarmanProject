package android.example.harmanproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ExampleViewHolder> {
    private ArrayList<ExampleElement> mExampleElements;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private TextView mText;
        private ImageView mImageView;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.grid_item);
            mImageView = itemView.findViewById(R.id.example_image);
        }

    }

    public RecyclerAdapter(ArrayList<ExampleElement> exampleElements) {
        mExampleElements = exampleElements;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_element, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleElement currentItem = mExampleElements.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mText.setText(currentItem.getText());
    }

    @Override
    public int getItemCount() {
        return mExampleElements.size();
    }
}
