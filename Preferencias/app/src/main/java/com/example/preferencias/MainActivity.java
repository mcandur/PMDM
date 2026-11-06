package com.example.preferencias;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et1 = findViewById(R.id.txtMail); //et1 hace referencia a txtMail

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE); //datos -> el archivo que se va a crear y almacenar el correo que escribamos; mode_private -> solo visible por esta app
        et1.setText(preferences.getString("mail",""));

    }

    public void guardar(View view){
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit(); //modifica el archivo sharedpreferences q contiene datos
        editor.putString("mail",et1.getText().toString());

        //ejecutar los cambios
        editor.commit();
        finish();
    }
}