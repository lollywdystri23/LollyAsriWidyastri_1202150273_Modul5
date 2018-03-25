package com.example.android.lollyasriwidyastri_1202150273_modul5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //deklarasi semua variabel yang akan digunakan
    private Database dtBase;
    private RecyclerView rcView;
    private Adapter adapter;
    private ArrayList<AddData> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set title menjadi To Do List
        setTitle("ToDo List");

        //Merefer semua variabel yang ada
        rcView = findViewById(R.id.rec_view); //mengakses recyclerview
        data_list = new ArrayList<>(); //membuat araylist baru
        dtBase = new Database(this); //membuat database baru
        dtBase.readdata(data_list); //memanggil method readdata

        //menginisialisasi shared preference
        SharedPreferences sharedP = this.getApplicationContext().getSharedPreferences("Preferences", 0);
        int color = sharedP.getInt("Colourground", R.color.white);

        adapter = new Adapter(this,data_list, color); //membuat adapter baru
        rcView.setHasFixedSize(true); //mengatur perubahan ukuran
        rcView.setLayoutManager(new LinearLayoutManager(this));
        rcView.setAdapter(adapter); //inisiasi adapter untuk recycler view

        //menjalankan method hapus data pada to do list
        hapusgeser();
    }

    //method untuk menghapus item pada to do list
    public void hapusgeser(){
        //membuat touch helper baru untuk recycler view
        ItemTouchHelper.SimpleCallback touchcall = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AddData current = adapter.getData(position);
                if(direction==ItemTouchHelper.LEFT){ //apabila item di swipe ke arah kiri
                    if(dtBase.removedata(current.getTodo())){ //remove item yang dipilih dengan mengenali todonya sebagai primary key
                        adapter.deleteData(position); //menghapus data
                        //membuat snack bar dan pemberitahuan bahwa item sudah terhapus dengan durasi 2 sekon
                        Snackbar.make(findViewById(R.id.coordinator), "List Deleted", 2000).show();
                    }
                }
            }
        };
        //menentukan itemtouchhelper untuk recycler view
        ItemTouchHelper touchhelp = new ItemTouchHelper(touchcall);
        touchhelp.attachToRecyclerView(rcView);
    }

    //ketika menu pada activity di buat
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //method yang dijalankan ketika item di pilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //mendapatkan id dari item yang
        int id = item.getItemId();
        //apabila item yang dipilih adalah setting
        if (id==R.id.action_settings){
            //membuat intent ke menu Settings
            Intent intent = new Intent(MainActivity.this, Settings.class);
            //memulai intent
            startActivity(intent);
            //menutup aktivitas setelah intent dijalankan
            finish();
        }
        return true;
    }

    public void addlist(View view) { //method yang akan dijalankan ketika tombol add di klik
        Intent intent = new Intent(MainActivity.this, AddToDo.class); //intent ke class add to do
        startActivity(intent); //memulai intent
    }
}
