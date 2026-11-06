package com.example.productossqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo, et_descripcion, et_precio;


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

        et_codigo = findViewById(R.id.txt_codigo);
        et_descripcion = findViewById(R.id.txt_descripcion);
        et_precio = findViewById(R.id.txt_precio);
    }


    public void registrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo); // nombre columna, nombre valor
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            long filas = baseDeDatos.insert("articulos", null, registro);

            if(filas != -1){ // Si devuelve -1 como id es qu eha ocurrido algun error (id ya existente)
                // Para que al clicar en registrar los valores desaparezcan y se muestre los hints solo.
                et_codigo.setText("");
                et_descripcion.setText("");
                et_precio.setText("");
                Toast.makeText(this, "Producto insertado correctamente.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Error: código existente.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Ha ocurrido algun error.", Toast.LENGTH_SHORT).show();
        }
        baseDeDatos.close();
    }


    public void buscar (View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase(); // para leer, no escribir

        String codigo = et_codigo.getText().toString();

        if (!codigo.isEmpty()) {
            Cursor fila = baseDeDatos.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo =" + codigo, null);

            if (fila.moveToFirst()) { // si el cursor se mueve de su fila 0 a la 1, ha encontrado algo
                et_descripcion.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
            } else {
                Toast.makeText(this, "El artículo no existe.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Introduzca el codigo del articulo.", Toast.LENGTH_SHORT).show();
        }
        baseDeDatos.close();
    }


    public void eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()){
            int filasEliminadas = baseDeDatos.delete("articulos","codigo=" + codigo, null); // devuelve las filas afectadas


            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            if(filasEliminadas == 1){
                Toast.makeText(this,"Articulo eliminado.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Artículo inexistente.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Introduzca un código válido..", Toast.LENGTH_SHORT).show();
        }
        baseDeDatos.close();
    }


    public void modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            int numeroFilas = baseDeDatos.update("articulos",registro, "codigo=" + codigo, null);

            if(numeroFilas == 1){
                Toast.makeText(this,"Artículo correctamente actualizado", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Artículo inexistente.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Debe rellenar todos los campos.", Toast.LENGTH_SHORT).show();
        }
        baseDeDatos.close();
    }

    //metodo para aplicar una rebaja del 2% a todos los productos

    //Agrega un boton que abra una nueva activity y muestre el nombre de todos los productos
}