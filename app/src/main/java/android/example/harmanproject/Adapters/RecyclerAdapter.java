package android.example.harmanproject.Adapters;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.DemoItemForAdapters;
import android.example.harmanproject.databinding.ExampleElementBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ExampleViewHolder> {
    private ArrayList<DemoItemForAdapters> mDemoItemForAdapters;
    private onItemClickListener mListener;
    private static ExampleElementBinding mBinding;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private TextView mText;
        private ImageView mImageView;

        ExampleViewHolder(ExampleElementBinding binding, final onItemClickListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mText = mBinding.gridItem;
            //mImageView = itemView.findViewById(R.id.example_image);
            mImageView = mBinding.exampleImage;
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public RecyclerAdapter(ArrayList<DemoItemForAdapters> demoItemForAdapters) {
        mDemoItemForAdapters = demoItemForAdapters;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mBinding = DataBindingUtil.inflate(inflater, R.layout.example_element, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_element, parent, false);
        return new ExampleViewHolder(mBinding, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        DemoItemForAdapters currentItem = mDemoItemForAdapters.get(position);
        holder.mImageView.setImageURI(currentItem.getImageUri());
        //Glide.with(mContext).load(current.getImageUri()).into(imageView);
        holder.mText.setText(currentItem.getText());
    }

    @Override
    public int getItemCount() {
        return mDemoItemForAdapters.size();
    }
}
