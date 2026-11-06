package com.example.activitypruebas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    /*private EditText et1;
    private EditText et2;

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
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
    }

    public void enviar(View view){
        Intent i = new Intent(this, SegundoActivity.class);
        i.putExtra("nombre",et1.getText().toString());
        i.putExtra("anyo",et2.getText().toString());
        startActivity(i);
    }*/

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
        et1 = findViewById(R.id.et1);
    }

    public void irALaWeb(View view){
        String url = et1.getText().toString();
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + url));

        startActivity(i);
    }
    /*public void irALaWeb(View view){
        Intent i = new Intent(this, SegundoActivity.class);
        i.putExtra("sitioWeb", et1.getText().toString());
        startActivity(i);
    }*/

}