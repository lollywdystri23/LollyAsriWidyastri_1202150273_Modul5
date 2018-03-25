package com.example.android.lollyasriwidyastri_1202150273_modul5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddToDo extends AppCompatActivity {

    //deklarasi semua variabel yang akan digunakan
    private EditText ToDo, Description, Priority;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        setTitle("Add To Do"); //set title menjadi add to do

        //mengambil semua variabel yang sudah di deklarasikan
        ToDo = (EditText) findViewById(R.id.edt_Todo);
        Description = (EditText) findViewById(R.id.edt_Desc);
        Priority = (EditText) findViewById(R.id.edt_Priority);
        db = new Database(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddToDo.this, MainActivity.class); //intent untuk pindah ke MainActivity
        startActivity(intent); //memulai intent
        this.finish(); //menutup aktivitas setelah intent selesai dijalankan
    }

    public void addTodo(View view) {
        //jika data todoname, deskripsi dan prioritas di isi maka akan menampilkan toast
        if (db.inputdata(new AddData(ToDo.getText().toString(), Description.getText().toString(), Priority.getText().toString()))){
            Toast.makeText(this, "To Do List Added !", Toast.LENGTH_SHORT).show(); //menampilkan toast
            startActivity(new Intent(AddToDo.this, MainActivity.class)); //intent ke mainActivity
            this.finish(); //menutup aktivitas setelah intent selesai dijalankan
        }else {
            //apabila edit text kosong maka akan muncul toast
            Toast.makeText(this, "To Do List Can't Empty", Toast.LENGTH_SHORT).show();
            ToDo.setText(null); //set textview to do kosong
            Description.setText(null); //set textview desc kosong
            Priority.setText(null); //set textview prior ksoong
        }
    }
}
