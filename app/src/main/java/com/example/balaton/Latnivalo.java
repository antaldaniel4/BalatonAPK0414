package com.example.balaton;

import android.widget.Toast;


import android.content.Context;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Latnivalo {
    private String Név;
    private String Város;
    private String Cím;
    private List<String> Kategória;
    private String Leírás;

    public Latnivalo() {} // Firestore-hoz kell üres konstruktor

    public String getNév() {
        return Név;
    }

    public void setNév(String név) {
        Név = név;
    }

    public String getVáros() {
        return Város;
    }

    public void setVáros(String város) {
        Város = város;
    }

    public String getCím() {
        return Cím;
    }

    public void setCím(String cím) {
        Cím = cím;
    }

    public List<String> getKategória() {
        return Kategória;
    }

    public void setKategória(List<String> kategória) {
        Kategória = kategória;
    }

    public String getLeírás() {
        return Leírás;
    }

    public void setLeírás(String leírás) {
        Leírás = leírás;
    }

    private void feltoltAdatokFirestoreba(Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

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

            Toast.makeText(context, "Adatok feltöltve!", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Hiba a feltöltéskor: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



}