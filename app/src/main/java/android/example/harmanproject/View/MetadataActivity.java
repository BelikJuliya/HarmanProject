package android.example.harmanproject.View;

import android.content.Intent;
import android.example.harmanproject.ViewModel.ExampleElement;
import android.example.harmanproject.ViewModel.MetadataViewModel;
import android.example.harmanproject.databinding.PictMetadataBinding;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import timber.log.Timber;

public class MetadataActivity extends AppCompatActivity {
    public ExampleElement exampleElement;
    private MetadataViewModel mViewModel;

    public MetadataActivity() {
        mViewModel = new MetadataViewModel(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PictMetadataBinding binding = PictMetadataBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setTitle("Metadata");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        exampleElement = intent.getParcelableExtra("Example element");

        if (exampleElement != null) {
            Uri imageRes = exampleElement.getImageResource();
            String textRes = exampleElement.getText();

            ImageView imageView = binding.bigImege;
            imageView.setImageURI(imageRes);

            TextView nameOfImage = binding.imageDescription;
            nameOfImage.setText(textRes);

            TextView metadataTextView = binding.metaData;
            if (mViewModel.extractMetadata() != null) {
                metadataTextView.setText(mViewModel.extractMetadata());
            } else {
                Toast.makeText(this, "There is no metadata in this image", Toast.LENGTH_SHORT).show();
            }

        } else Timber.i("Example element is null, can't show metadata");
    }


    public void openMap(View view) {
        Intent intent = new Intent(MetadataActivity.this, MapBoxActivity.class);
        startActivity(intent);
    }
}


