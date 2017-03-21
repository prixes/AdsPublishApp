package com.prixesoft.david.ads.app;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by david on 12-Mar-17.
 */

public class ImageProcess {

    public static Drawable resize(Drawable image, MainActivity activity) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 200 , 200, false);
        return new BitmapDrawable(activity.getResources(), bitmapResized);
    }
}
