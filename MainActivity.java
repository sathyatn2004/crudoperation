package com.example.myapplication14;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editName, editAge, editId;
    Button btnAdd, btnView, btnUpdate, btnDelete;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);

        editName = findViewById(R.id.editTextName);
        editAge = findViewById(R.id.editTextAge);
        editId = findViewById(R.id.editTextId);

        btnAdd = findViewById(R.id.buttonAdd);
        btnView = findViewById(R.id.buttonView);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);

        btnAdd.setOnClickListener(v -> addData());
        btnView.setOnClickListener(v -> viewData());
        btnUpdate.setOnClickListener(v -> updateData());
        btnDelete.setOnClickListener(v -> deleteData());
    }

    private void addData() {
        boolean isInserted = db.insertData(editName.getText().toString(), editAge.getText().toString());
        Toast.makeText(this, isInserted ? "✅ Data Inserted" : " Insert Failed", Toast.LENGTH_SHORT).show();
    }

    private void viewData() {
        Cursor res = db.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "No Data Found");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append("ID: ").append(res.getString(0)).append("\n");
            buffer.append("Name: ").append(res.getString(1)).append("\n");
            buffer.append("Age: ").append(res.getString(2)).append("\n\n");
        }
        showMessage("Data", buffer.toString());
    }

    private void updateData() {
        boolean isUpdated = db.updateData(editId.getText().toString(), editName.getText().toString(), editAge.getText().toString());
        Toast.makeText(this, isUpdated ? "✅ Data Updated" : " Update Failed", Toast.LENGTH_SHORT).show();
    }

    private void deleteData() {
        Integer deletedRows = db.deleteData(editId.getText().toString());
        Toast.makeText(this, deletedRows > 0 ? "✅ Data Deleted" : " Delete Failed", Toast.LENGTH_SHORT).show();
    }

    private void showMessage(String title, String message) {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}

