package com.example.productosvolley;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText edtCodigo;
    private EditText edtNombre;
    private EditText edtPrecio;
    private EditText edtFabricante;
    RequestQueue requestQueue; //de volley para la cola de peticiones a la BD


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
        edtCodigo = findViewById(R.id.etCodigo);
        edtFabricante = findViewById(R.id.etFabricante);
        edtNombre = findViewById(R.id.etNombre);
        edtPrecio = findViewById(R.id.etPrecio);

    }

    public void insertarProducto(View view){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://localhost/2DAM/insertar_producto.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, "Producto añadido correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("codigo", edtCodigo.getText().toString());
                parametros.put("nombre", edtNombre.getText().toString());
                parametros.put("precio", edtPrecio.getText().toString());
                parametros.put("fabricante", edtFabricante.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void buscarProducto(View view){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://localhost/2DAM/busacr_producto.php?codigo=" + edtCodigo.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                JSONObject jsonObject = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        jsonObject = jsonArray.getJSONObject(i);
                        edtNombre.setText(jsonObject.getString("nombre"));
                        edtPrecio.setText(jsonObject.getString("precio"));
                        edtFabricante.setText(jsonObject.getString("fabricante"));
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

}