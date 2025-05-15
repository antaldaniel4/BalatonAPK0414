package com.example.balaton;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LatnivaloActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latnivalo);

        db = FirebaseFirestore.getInstance();

        Button feltoltesGomb = findViewById(R.id.btnFeltoltes);
        feltoltesGomb.setOnClickListener(v -> feltoltAdatokFirestoreba(this));
    }

    private void feltoltAdatokFirestoreba(Context context) {
        try {
            InputStream is = context.getAssets().open("latnivalok.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonBuilder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                Latnivalo l = new Latnivalo();
                l.setNév(obj.getString("Név"));
                l.setVáros(obj.getString("Város"));
                l.setCím(obj.getString("Cím"));
                l.setLeírás(obj.getString("Leírás"));

                JSONArray katArray = obj.getJSONArray("Kategória");
                List<String> kategoriak = new ArrayList<>();
                for (int j = 0; j < katArray.length(); j++) {
                    kategoriak.add(katArray.getString(j));
                }
                l.setKategória(kategoriak);

                db.collection("latnivalok").add(l);
            }

            Toast.makeText(context, "Adatok sikeresen feltöltve!", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Log.e("Firestore", "Hiba a JSON olvasása vagy feltöltés során", e);
            Toast.makeText(context, "Hiba: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
