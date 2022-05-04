package com.example.androidinternshipassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidinternshipassignment.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerViewAdapter adapter = new recyclerViewAdapter(list, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.mainrecyclerview.setAdapter(adapter);
        binding.mainrecyclerview.setLayoutManager(manager);
        adapter.notifyDataSetChanged();
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                final EditText txt_inputText = mView.findViewById(R.id.txt_input);
                Button btn_cancel = mView.findViewById(R.id.btn_cancel);
                Button btn_okay = mView.findViewById(R.id.btn_okay);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String string = txt_inputText.getText().toString();
                        if (string.length() > 0) {
                            list.add(string);
                            adapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        }else{
                            Toast.makeText(MainActivity.this, "Enter Title", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.show();
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recyclerViewAdapter.onActivityResult(requestCode, resultCode, data);
    }
}