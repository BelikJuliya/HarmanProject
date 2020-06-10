package android.example.harmanproject.View;

import android.content.Intent;
import android.example.harmanproject.ViewModel.ExampleElement;
import android.example.harmanproject.ViewModel.MetadataViewModel;
import android.example.harmanproject.databinding.PictMetadataBinding;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URI;

import timber.log.Timber;

public class MetadataActivity extends AppCompatActivity {
    public ExampleElement exampleElement;
    private MetadataViewModel mViewModel;
    Uri imageRes;
    private final String TAG = "ImageLoading";

    public MetadataActivity() {
        mViewModel = new MetadataViewModel(this);
    }

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
            imageRes = exampleElement.getImageResource();
            String textRes = exampleElement.getText();

            ImageView imageView = binding.bigImege;
            imageView.setImageURI(imageRes);

            TextView nameOfImage = binding.imageDescription;
            nameOfImage.setText(textRes);

            TextView metadataTextView = binding.metaData;

            metadataTextView.setText(mViewModel.extractMetadata());

        } else Timber.i("Example element is null, can't show metadata");
    }

    public void openMap(View view) {
        if (mViewModel.areCoordinatesExist) {
            Intent intent = new Intent(MetadataActivity.this, MapBoxActivity.class);
            startActivity(intent);
            //Intent intentTest = new Intent(MetadataActivity.this, TestMapBox.class);
            //startActivity(intentTest);

        } else {
            Toast.makeText(this, "There is no coordinates in this photo, please chose another one", Toast.LENGTH_SHORT).show();

        }
    }
}


