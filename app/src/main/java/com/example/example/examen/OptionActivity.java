package com.example.example.examen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OptionActivity extends AppCompatActivity {

    TextView textViewbienvenido;
    Button buttonPurchase, buttonRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        buttonPurchase = findViewById(R.id.button_comprar);
        buttonRecords = findViewById(R.id.button_registro);
        textViewbienvenido = findViewById(R.id.text_view_bienvenido);

        final String name = getIntent().getStringExtra("name");
        textViewbienvenido.setText("Bienvenido "+ name);

        buttonPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionActivity.this, PurchaseActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        buttonRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });
    }
}
