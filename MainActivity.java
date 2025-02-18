package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyAdapter adapter;
    private List<String> items;
    private DatabaseHelper databaseHelper;  // ✅ SQLite Database Helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        Button addNoteButton = findViewById(R.id.add_button);

        // ✅ DatabaseHelper ইনিশিয়ালাইজ করো
        databaseHelper = new DatabaseHelper(this);

        // ✅ Database থেকে সব নোট লোড করো
        items = databaseHelper.getAllNotes();

        // ✅ Adapter সেট করো
        adapter = new MyAdapter(this, items);
        listView.setAdapter(adapter);

        // ✅ নতুন নোট যোগ করার বাটন ক্লিক লিসেনার
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String newNote = data.getStringExtra("NEW_NOTE");
            if (newNote != null) {
                // ✅ নতুন নোট SQLite Database-এ সংরক্ষণ করো
                databaseHelper.insertNote(newNote);

                // ✅ নতুন নোট ListView তে আপডেট করো
                items.clear();
                items.addAll(databaseHelper.getAllNotes());
                adapter.notifyDataSetChanged();
            }
        }
    }
}
