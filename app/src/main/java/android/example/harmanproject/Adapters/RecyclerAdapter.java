package android.example.harmanproject.Adapters;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.ExampleElement;
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
    private onItemClickListener mListener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private TextView mText;
        private ImageView mImageView;

        ExampleViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            mText = itemView.findViewById(R.id.grid_item);
            mImageView = itemView.findViewById(R.id.example_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

    public RecyclerAdapter(ArrayList<ExampleElement> exampleElements) {
        mExampleElements = exampleElements;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_element, parent, false);
        return new ExampleViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleElement currentItem = mExampleElements.get(position);
        holder.mImageView.setImageURI(currentItem.getImageResource());
        holder.mText.setText(currentItem.getText());
    }

    @Override
    public int getItemCount() {
        return mExampleElements.size();
    }
}
