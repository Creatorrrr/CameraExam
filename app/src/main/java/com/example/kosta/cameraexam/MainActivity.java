package com.example.kosta.cameraexam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView photo;
    private Button camera;
    private EditText saveName;

    private File saveFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photo = (ImageView)findViewById(R.id.photo);
        camera = (Button)findViewById(R.id.camera);
        saveName = (EditText)findViewById(R.id.saveName);

        final Intent captureImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = getPhotoFile() != null && captureImg.resolveActivity(getPackageManager()) != null;

        if(canTakePhoto) {
            Uri uri = Uri.fromFile(getPhotoFile());
            captureImg.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImg, 1);
            }
        });

        saveFile = getPhotoFile();
        updatePhototView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updatePhototView();
    }

    private String getPhotoFileName() {
        return "IMG_" + saveName.getText().toString() + ".jpg";
    }

    private File getPhotoFile() {
        File externalFileDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFileDir == null) {
            return null;
        }

        return new File(externalFileDir, getPhotoFileName());
    }

    private void updatePhototView() {
        if(saveFile == null || !saveFile.exists()) {
            photo.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaleBitmap(saveFile.getPath(), this);
            photo.setImageBitmap(bitmap);
        }
    }
}
