package itgsolutions.com.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class MainActivity extends AppCompatActivity {
Button chosePhoto , chooseFile;
    ImageView imageView ;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private ArrayList<String> docPaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chosePhoto = (Button) findViewById(R.id.button);
        chooseFile = (Button) findViewById(R.id.button2);
        imageView = (ImageView) findViewById(R.id.imageView);

        chosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilePickerBuilder.getInstance().setMaxCount(1)
                        .setSelectedFiles(photoPaths)
                        .setActivityTheme(R.style.AppTheme)
                        .pickPhoto(MainActivity.this);

            }
        });
        final String[] pdf = {".pdf"};

        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* FilePickerBuilder.getInstance().setMaxCount(10)
                        .setSelectedFiles(docPaths)
                        .setActivityTheme(R.style.AppTheme)
                        .addFileSupport("PDF",pdf)
                        .enableDocSupport(false)

                        .pickFile(MainActivity.this);
*/
                startActivity(new Intent(MainActivity.this,NNN.class));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    photoPaths = new ArrayList<>();
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                    File f = new File(photoPaths.get(0));

                    String imageName = f.getName();

                    String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
                    String rot =   f.getParent()+"/";
                    copyFile(rot,f.getName(),root+"Candidates"+File.separator);

                }
                break;
            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    docPaths = new ArrayList<>();
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));

                    File f = new File(docPaths.get(0));

                    String imageName = f.getName();

                    String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
                    String rot =   f.getParent()+"/";
                    copyFile(rot,f.getName(),root+"Candidates"+"/");

                }
                break;
        }
    }
    private void copyFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

}
