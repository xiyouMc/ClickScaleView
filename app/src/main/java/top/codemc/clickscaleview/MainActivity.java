package top.codemc.clickscaleview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Bitmap touchBmp;
    private float scaleWidth = 1, scaleHeight = 1;
    private double smallScale = 0.85;
    private double bigScale = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        final ImageView touch_view = (ImageView) findViewById(R.id.scale_view);
        touch_view.setImageBitmap(touchBmp);
        touch_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        scale(touch_view, smallScale);
                        return true;

                    case MotionEvent.ACTION_UP:
                        scale(touch_view, bigScale);
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * 缩放 View
     *
     * @param view  需要缩放的View
     * @param scale 缩放率
     */
    private void scale(ImageView view, double scale) {
        int bmpWidth = touchBmp.getWidth();
        int bmpHeight = touchBmp.getHeight();

        scaleWidth = (float) (scaleWidth * scale);
        scaleHeight = (float) (scaleHeight * scale);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBmp = Bitmap.createBitmap(touchBmp, 0, 0, bmpWidth, bmpHeight, matrix, true);

        if (scale < 1 && bigScale == 0) {
            bigScale = (double) bmpHeight / (double) resizeBmp.getHeight();
        }
        view.setImageBitmap(resizeBmp);
    }
}
