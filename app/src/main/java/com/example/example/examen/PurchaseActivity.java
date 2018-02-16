package com.example.example.examen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.example.examen.utils.AdminSQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PurchaseActivity extends AppCompatActivity {

    Spinner spinnerMedicinas;
    Button buttonPurchase;
    EditText editTextCantidad;
    InputStream in;
    BufferedReader reader;
    ArrayList<String> medicinas = new ArrayList<>();
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        spinnerMedicinas = findViewById(R.id.spinner_medicine);
        buttonPurchase = findViewById(R.id.button_comprar);
        editTextCantidad = findViewById(R.id.edit_text_cantidd);
        name = getIntent().getStringExtra("name");


        try {
            loadMedicines();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AdminSQLiteOpenHelper adminSQL = new AdminSQLiteOpenHelper(this, "db", null, 1);
        SQLiteDatabase sqlDB = adminSQL.getWritableDatabase();

        Cursor row2 = sqlDB.rawQuery("select * from medicina ", null);
        if (row2.moveToFirst()) {
            do {

                String nombre = row2.getString(1);

                medicinas.add(nombre);


            } while (row2.moveToNext());
        }


        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<>(this,   android.R.layout.simple_spinner_item, medicinas);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMedicinas.setAdapter(spinnerArrayAdapter2);

        buttonPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdminSQLiteOpenHelper adminSQL = new AdminSQLiteOpenHelper(PurchaseActivity.this, "db", null, 1);
                SQLiteDatabase sqlDB = adminSQL.getWritableDatabase();



                ContentValues compra = new ContentValues();

                compra.put("namecliente", name);
                compra.put("namemedicina", spinnerMedicinas.getSelectedItem().toString());
                compra.put("qty", editTextCantidad.getText().toString());
                long rows = sqlDB.insert("compra", null, compra);

                sqlDB.close();

                Intent intent= new Intent(PurchaseActivity.this, OptionActivity.class);
                Toast.makeText(PurchaseActivity.this, name +"ha comprado " + editTextCantidad.getText().toString() + " " + spinnerMedicinas.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra("name", name);
                startActivity(intent);

            }
        });

    }

    private void loadMedicines() throws IOException {
        AdminSQLiteOpenHelper adminSQL = new AdminSQLiteOpenHelper(this, "db", null, 1);
        SQLiteDatabase sqlDB = adminSQL.getWritableDatabase();


        Cursor row = sqlDB.rawQuery("select name from medicina", null);
        if (row.moveToLast())
        {
            Toast.makeText(this, String.format("Ya hay registros en la tabla"), Toast.LENGTH_SHORT).show();
        }
        else
        {
            in = this.getAssets().open("2.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            int cont = 0;
            String check = null;

            while((check = reader.readLine()) != null)
            {
                ContentValues registro = new ContentValues();
                StringTokenizer tokens = new StringTokenizer(check, "|");

                registro.put("name", tokens.nextToken());
                registro.put("price", tokens.nextToken());
                registro.put("id", tokens.nextToken());

                sqlDB.insert("medicina", null, registro);
                cont++;
            }

            sqlDB.close();

            Toast.makeText(this, String.format("Se han añadido %d registros", cont), Toast.LENGTH_SHORT).show();
        }



    }
}
