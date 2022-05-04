package com.example.androidinternshipassignment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import java.util.ArrayList;


public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.viewHolder> {

    ArrayList<String> list;
    public static ArrayList<ArrayList<Uri>> imgList = new ArrayList<>();
    Context context;
    final Handler handler = new Handler();

    public recyclerViewAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
        imgList.add(new ArrayList<>());
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview1, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (imgList.get(position).size() == 0) {
            imgList.add(new ArrayList<>());
        }
        recyclerViewAdapter2 adapter = new recyclerViewAdapter2(imgList.get(position), context);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL);
        holder.recyclerView2.setAdapter(adapter);
        holder.recyclerView2.setLayoutManager(manager);
        holder.textView.setText(list.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Position " + position);
                openGallery(position);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView2;
        TextView textView;
        Button button;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView2 = itemView.findViewById(R.id.recyclerView2);
            textView = itemView.findViewById(R.id.rv1textView);
            button = itemView.findViewById(R.id.rv1button);
        }
    }

    private static int PICK_IMAGE = 100;

    private void openGallery(int pos) {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        PICK_IMAGE = 100 + pos;
        ((Activity) context).startActivityForResult(gallery, PICK_IMAGE);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            int pos = PICK_IMAGE - 100;
            if(data.getClipData()!=null){
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imgList.get(pos).add(imageUri);
                }
            }else if(data.getData()!=null){
                Uri imageUri = data.getData();
                imgList.get(pos).add(imageUri);
            }
        }
    }
}


