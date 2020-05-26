package android.example.harmanproject.View;

import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.Activity5ViewModel;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity5 extends AppCompatActivity {
    Activity5ViewModel viewModel;

    public Activity5 (){
        viewModel = new Activity5ViewModel(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        TextView textView = findViewById(R.id.wifi_info);
        textView.setText(viewModel.findWifiInfo());



    }
}
