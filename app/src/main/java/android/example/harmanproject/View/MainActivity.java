package android.example.harmanproject.View;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.example.harmanproject.R;
import android.example.harmanproject.databinding.ActivityMainBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.mapbox.mapboxsdk.Mapbox;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.JellyTypes.Jelly;
import com.nightonke.jellytogglebutton.State;

public class MainActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private Switch mThemeSwitcher;
    private static final String MyPREFERENCES = "NightModePreferences";
    private static final String KEY_ISNIGHTMODE = "isNightMode";
    private SharedPreferences sharedPreferences;
    JellyToggleButton jtb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Mapbox.getInstance(getApplicationContext(), getString(R.string.MAPBOX_ACCESS_TOKEN));

        jtb = binding.jellyToggleBtn;
        jtb.setJelly(Jelly.RANDOM);
        jtb.setThumbColorRes(R.color.colorPrimaryDark);
        jtb.setLeftBackgroundColorRes(R.color.colorPrimary);
        jtb.setRightBackgroundColorRes(R.color.colorPrimary);

        jtb.setOnStateChangeListener((process, state, jtb) -> {
            if (state.equals(State.LEFT)) {
                //do smth
            } else if (state.equals(State.RIGHT)) {
                //do smth
            }
        });

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mThemeSwitcher = binding.switcher;
        checkNightModeActivated();
        mThemeSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    saveNightModeState(true);
                    recreate();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    saveNightModeState(false);
                    recreate();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itself:
                jtb.setJelly(Jelly.ITSELF);
                break;
            case R.id.lazy_tremble_body_fatty:
                jtb.setJelly(Jelly.LAZY_TREMBLE_BODY_FATTY);
                break;
            case R.id.lazy_tremble_head_slim_jim:
                jtb.setJelly(Jelly.ACTIVE_TREMBLE_HEAD_SLIM_JIM);
                break;
            case R.id.lazy_tremble_tail_fatty:
                jtb.setJelly(Jelly.LAZY_TREMBLE_TAIL_FATTY);
                break;
        }
        return true;
    }

    private void saveNightModeState(boolean nightMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);
        editor.apply();
    }

    public void checkNightModeActivated() {
        if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)) {
            mThemeSwitcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            jtb.setThumbColorRes(R.color.colorPrimaryNight);
            jtb.setRightBackgroundColorRes(R.color.colorAccentNight);
            jtb.setLeftBackgroundColorRes(R.color.colorAccentNight);
        } else {
            mThemeSwitcher.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void goToActivity1(View view) {
        Intent intent = new Intent(MainActivity.this, RotatingPictActivity.class);
        startActivity(intent);

    }

    public void goToActivity2(View view) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(intent);
        } else {
            requestStoragePermission();
        }
    }

    public void showWifiInfo(View view) {
        Intent intent = new Intent(MainActivity.this, WifiInfoActivity.class);
        startActivity(intent);
    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to show images to the activity 2")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == (PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
