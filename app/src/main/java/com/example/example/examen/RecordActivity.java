package com.example.example.examen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.example.examen.utils.AdminSQLiteOpenHelper;
import com.example.example.examen.utils.Compra;
import com.example.example.examen.utils.CompraAdapter;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CompraAdapter compraAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        recyclerView = findViewById(R.id.compras_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        AdminSQLiteOpenHelper adminSQL = new AdminSQLiteOpenHelper(this, "db", null, 1);
        SQLiteDatabase sqlDB = adminSQL.getWritableDatabase();


        ArrayList<Compra> compras = new ArrayList<>();


        Cursor row = sqlDB.rawQuery("select * from compra ", null);
        if (row.moveToFirst()) {
            do {

                Compra player = new Compra(row.getString(1),row.getString(2), row.getString(3));

                compras.add(player);


            } while (row.moveToNext());
        }
        compraAdapter = new CompraAdapter(compras);
        recyclerView.setAdapter(compraAdapter);
    }
}
