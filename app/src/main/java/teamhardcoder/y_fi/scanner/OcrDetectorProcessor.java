/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package teamhardcoder.y_fi.scanner;

import android.util.Log;
import android.util.SparseArray;
import java.lang.String;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import teamhardcoder.y_fi.scanner.ui.GraphicOverlay;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 * TODO: Make this implement Detector.Processor<TextBlock> and add text to the GraphicOverlay
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        mGraphicOverlay = ocrGraphicOverlay;
    }

    // TODO:  Once this implements Detector.Processor<TextBlock>, implement the abstract methods.
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        CharSequence total = "total";
        String currentText = "";
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {

                //Log.d("Processor", "Text detected! " + item.getValue());

                // get current string
                currentText = item.getValue();
                currentText = currentText.toLowerCase();

                // if the string contains the word "total" then print statemet
                if(currentText.contains(total)){
                    Log.d("Processor", "the word total was found!");
                    Log.d("Processor", "currentText is: " + currentText);
                }
            }
            //String currentText = item.getValue();

            OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
            mGraphicOverlay.add(graphic);
        }
    }

    @Override
    public void release() {
        mGraphicOverlay.clear();
    }
}
