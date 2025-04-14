package com.example.balaton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity {

    private ImageView mapImage;
    private FrameLayout mapRootLayout;

    private static final String LOG_TAG = "MapActivity";

    // xRatio, yRatio, name
    Object[][] dotData = {
            {0.36f, 0.44f, "Tapolca"},
            {0.65f, 0.72f, "Zamárdi"},
            {0.44f, 0.63f, "Füred"},
            {0.17f, 0.39f, "Keszthely"},
            {0.19f, 0.27f, "Sümeg"},
            {0.32f, 0.61f, "Veszprém"},
            {0.26f, 0.91f, "Nádasladány"},
            {0.17f, 0.41f, "Balatonlelle"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapImage = findViewById(R.id.mapImage);
        mapRootLayout = findViewById(R.id.mapRootLayout);

        mapImage.post(() -> {
            int imageWidth = mapImage.getWidth();
            int imageHeight = mapImage.getHeight();

            for (Object[] dot : dotData) {
                float xRatio = (float) dot[0];
                float yRatio = (float) dot[1];
                String name = (String) dot[2];

                View dotView = new View(this);
                dotView.setBackgroundResource(R.drawable.dot_background);
                dotView.setTag(name); // itt eltároljuk a város nevét

                dotView.setOnClickListener(this::onDotClick);

                int dotSize = 32; // px
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(dotSize, dotSize);
                params.leftMargin = (int) (xRatio * imageWidth) - dotSize / 2;
                params.topMargin = (int) (yRatio * imageHeight) - dotSize / 2;

                mapRootLayout.addView(dotView, params);
            }
        });
    }

    public void onDotClick(View view) {
        String message = (String) view.getTag(); // lekérjük a nevet
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}