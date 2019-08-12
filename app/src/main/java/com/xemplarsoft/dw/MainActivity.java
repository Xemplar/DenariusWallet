package com.xemplarsoft.dw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import net.glxn.qrgen.android.QRCode;

public class MainActivity extends AppCompatActivity {
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_layout);
        inflater = LayoutInflater.from(this);
        final RelativeLayout root = (RelativeLayout) findViewById(R.id.root);
        inflater.inflate(R.layout.content_main, root, true);

        final ImageView qr = findViewById(R.id.qr_code);

        try {
            Bitmap bit = QRCode.from("DBY1BZawkKT5CJkt6LBkANQ56bu5VUo8eY").withSize(1024,1024).withColor(0xFF313131,0xFFFFFFFF).bitmap();
            qr.setImageBitmap(cropBorder(bit, 100));
        } catch (Exception e){
            e.printStackTrace();
        }

        qr.post(new Runnable() {
            public void run() {
                int margin = (root.getWidth() / 2 - qr.getWidth() / 2);
                System.out.println("DEBUG: root " + root.getWidth());
                System.out.println("DEBUG: qr " + qr.getWidth());

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)qr.getLayoutParams();
                params.setMargins(0, margin, 0, 0);
                qr.setLayoutParams(params);
            }
        });

        Runnable run = new Runnable() {
            public void run() {
                test();
            }
        };
        Thread t = new Thread(run);
        t.start();
    }

    public void test(){

    }

    private Bitmap cropBorder(Bitmap bmp, int borderSize) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() - borderSize * 2, bmp.getHeight() - borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawBitmap(bmp, -borderSize, -borderSize, null);
        return bmpWithBorder;
    }

    public float dpToPx(float dp){
        return dp * ((float) this.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public float pxToDp(float px){
        return px / ((float) this.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
