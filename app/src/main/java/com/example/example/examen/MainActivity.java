package com.example.example.examen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.example.examen.utils.AdminSQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText editTextNombre, editTextContrasena;
    Button buttonLogin;
    InputStream in;
    BufferedReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextContrasena = findViewById(R.id.edit_text_contra);
        editTextNombre = findViewById(R.id.edit_text_usuario);
        buttonLogin = findViewById(R.id.button_login);

        try {
            fillDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextNombre.getText().toString().equals("") || editTextContrasena.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                } else {
                    AdminSQLiteOpenHelper adminSQL = new AdminSQLiteOpenHelper(MainActivity.this, "db", null, 1);
                    SQLiteDatabase sqlDB = adminSQL.getWritableDatabase();


                    Cursor row = sqlDB.rawQuery("SELECT * FROM cliente WHERE name = '" + editTextNombre.getText().toString() + "'", null);
                    if (row.moveToLast()) {
                        String name = row.getString(0);

                        Intent intent = new Intent(MainActivity.this, OptionActivity.class);
                        intent.putExtra("name", editTextNombre.getText().toString());

                        startActivity(intent);

                    }else
                        Toast.makeText(MainActivity.this, "No existe el usuario", Toast.LENGTH_SHORT).show();

                    sqlDB.close();
                }

            }
        });


    }

    private void fillDB() throws IOException {
        AdminSQLiteOpenHelper adminSQL = new AdminSQLiteOpenHelper(this, "db", null, 1);
        SQLiteDatabase sqlDB = adminSQL.getWritableDatabase();

        Cursor row = sqlDB.rawQuery("select name from cliente", null);
        if (row.moveToLast())
        {
            Toast.makeText(this, String.format("Ya hay registros en la tabla"), Toast.LENGTH_SHORT).show();
        }
        else
        {
            in = this.getAssets().open("1.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            int cont = 0;
            String check = null;

            while((check = reader.readLine()) != null)
            {
                ContentValues registro = new ContentValues();
                StringTokenizer tokens = new StringTokenizer(check, "|");
                registro.put("name", tokens.nextToken());
                registro.put("pass", tokens.nextToken());
                registro.put("id", tokens.nextToken());

                sqlDB.insert("cliente", null, registro);
                cont++;
            }

            sqlDB.close();

            Toast.makeText(this, String.format("Se han añadido %d registros", cont), Toast.LENGTH_SHORT).show();
        }
    }
}
