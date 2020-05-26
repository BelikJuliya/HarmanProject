package android.example.harmanproject.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.example.harmanproject.R;
import android.example.harmanproject.ViewModel.Activity3ViewModel;
import android.example.harmanproject.ViewModel.ExampleElement;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Activity3 extends AppCompatActivity {
    public  ExampleElement exampleElement;
    Activity3ViewModel viewModel;

    public Activity3 () {
        viewModel = new Activity3ViewModel(this);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Intent intent = getIntent();
        exampleElement = intent.getParcelableExtra("Example element");

        assert exampleElement != null;
        Uri imageRes = exampleElement.getImageResource();
        String textRes = exampleElement.getText();

        ImageView imageView = findViewById(R.id.image_activity_3);
        imageView.setImageURI(imageRes);

        TextView nameOfImage = findViewById(R.id.text_activity_3);
        nameOfImage.setText(textRes);

        TextView metadataTextView = findViewById(R.id.meta_data);
        metadataTextView.setText(viewModel.extractMetadata());

    }


    public void openMap(View view) {
        Intent intent = new Intent(Activity3.this, Activity4.class);
        startActivity(intent);
    }
}


