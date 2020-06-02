package android.example.harmanproject.View;

import android.annotation.SuppressLint;
import android.example.harmanproject.databinding.EnterMessageBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class EnterMessage extends Fragment {

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EnterMessageBinding binding = EnterMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
