package teamhardcoder.y_fi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.List;

import static java.lang.Double.parseDouble;


public class ScannerActivity extends AppCompatActivity {

    SurfaceView cameraDisplay;
    TextView totalBox;
    CameraSource camSrc;
    final int CameraPermissionId = 1001;
    String txtBox = "";

    String total = "";
    String AMOUNT = "amount";
    double totalAmount = 0;
    boolean containsTotal = false;
    Button nextButton = null;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case CameraPermissionId:
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        return;
                    }

                    try
                    {
                        camSrc.start(cameraDisplay.getHolder());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(teamhardcoder.y_fi.R.layout.activity_scanner);

        nextButton = (Button) findViewById(R.id.button2);
        nextButton.setVisibility(View.INVISIBLE);

        cameraDisplay = (SurfaceView) findViewById(teamhardcoder.y_fi.R.id.cameraSurface);
        totalBox = (TextView) findViewById(teamhardcoder.y_fi.R.id.textViewTotal);

        TextRecognizer textReader = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textReader.isOperational()) {
            Log.w("MainActivity", "no TextRecognizer available");
        }
        else
        {
            // create a new camera with following settings
            camSrc = new CameraSource.Builder(getApplicationContext(), textReader)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedFps(30.0f)
                    .setRequestedPreviewSize(1280, 1024)
                    .setAutoFocusEnabled(true)
                    .build();

            cameraDisplay.getHolder().addCallback(new SurfaceHolder.Callback()
            {
                @Override
                public void surfaceCreated(SurfaceHolder holder)
                {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(ScannerActivity.this, new String[] {Manifest.permission.CAMERA},
                                    CameraPermissionId);
                            return;
                        }
                        camSrc.start(cameraDisplay.getHolder());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
                {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder)
                {
                    camSrc.stop();
                }
            });

            textReader.setProcessor(new Detector.Processor<TextBlock>()
            {
                @Override
                public void release()
                {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detectedText)
                {
                    final String TOTAL = "total";
                    final SparseArray<TextBlock> textArray = detectedText.getDetectedItems();
                    if(textArray.size() != 0)
                    {
                        totalBox.post(new Runnable() {
                            @Override
                            public void run() {
                                //StringBuilder strBuilder = new StringBuilder();
                                for(int i = 0; i < textArray.size(); i++)
                                {
                                    // get value of entire text block
                                    TextBlock textBlock = textArray.valueAt(i);

                                    // break TextBlock into a list of its lines
                                    List<Line> lineList = (List<Line>) textBlock.getComponents();


                                    for(Line line : lineList)
                                    {

                                        String textLine = line.getValue();
                                        Log.i("each line", textLine);

                                        // ignore instances of subtotal
                                        if(textLine.toLowerCase().contains("sub total")||
                                                textLine.toLowerCase().contains("subtotal") )
                                        {
                                            continue;
                                        }

                                        // if the line of text contains the word total
                                        if(textLine.toLowerCase().contains(TOTAL)
                                                || textLine.toLowerCase().contains(AMOUNT))
                                        {

                                            // separate lines of text into words separated by space
                                            String[] separateWordsArray = textLine.split(" ");
                                            //int currentIndex = 0;
                                            for(String word: separateWordsArray)
                                            {
                                                Log.i("separateWordsArray", word);
                                                if (word.equalsIgnoreCase(TOTAL) ||
                                                        word.equalsIgnoreCase(AMOUNT))
                                                {
                                                    continue;

                                                }
                                                else
                                                {
                                                    try
                                                    {
                                                        totalAmount = parseDouble(word);
                                                        Log.i("success!", "The total is: " + totalAmount);
                                                        containsTotal = true;
                                                    }
                                                    catch(NumberFormatException e)
                                                    {
                                                        try
                                                        {
                                                            // to account for the case the amount contains a dollar sign $
                                                            totalAmount = parseDouble(word.substring(1, word.length()));
                                                            Log.i("success!", "The total is: " + totalAmount);
                                                            containsTotal = true;
                                                        }
                                                        catch(NumberFormatException f)
                                                        {
                                                            Log.i("error", "Please enter manually");
                                                        }

                                                    }
                                                }
                                                //currentIndex++;
                                                //Log.i("main", "for loop textline reached!");
                                            }

                                            //strBuilder.append(textBlock.getValue());
                                            /*int k;
                                            for(k = 0; k < separateWordsArray.length; k++);
                                            {
                                                if(separateWordsArray[k].equalsIgnoreCase(TOTAL))
                                                {
                                                    Log.d("main", "the word total found!");
                                                    if(separateWordsArray[k + 1] != null) {
                                                        amount = separateWordsArray[k + 1];
                                                        containsTotal = true;
                                                    }
                                                }
                                            }*/

                                        }
                                    }

                                }

                                if(containsTotal)
                                {
                                    String amount = Double.toString(totalAmount);
                                    totalBox.setText("$" + amount);
                                    nextButton.setVisibility(View.VISIBLE);
                                }

                            }
                        });
                    }
                }
            });
        }

    }
    public void toConfirm(View view)
    {
        Intent intent = new Intent(this, ExpenseCreation.class);
        //String pack = Double.toString(totalAmount);
        intent.putExtra("totalAmount", totalAmount);
        startActivity(intent);
    }
}