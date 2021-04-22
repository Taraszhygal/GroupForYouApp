package com.groupforyouapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity {

    TextView title_view, desc_view, category_view,  mentor_view, exp_view, number_view;
    ImageView image_view;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_group);
        init();

        Intent intent = this.getIntent();

        //Receive data
        String title = intent.getExtras().getString("Group_name");
        String mentor = intent.getExtras().getString("Author");
        String category = intent.getExtras().getString("Category");
        String desc = intent.getExtras().getString("Description");
        String exp = intent.getExtras().getString("Exp");
        String number = intent.getExtras().getString("Number");
        String image = intent.getExtras().getString("Icon");

        //set it in element view
        title_view.setText(title);
        desc_view.setText(desc);
        category_view.setText("Категорія: "+category);
        mentor_view.setText("Керівник: "+mentor);
        exp_view.setText("Досвід роботи: "+exp);
        number_view.setText("Номер телефону: "+number);
        if (image!=null) {
            image_view.setBackground(null);
            Picasso.with(getApplication())
                    .load(image)
                    .fit()
                    .centerInside()
                    .into(image_view);
        }else {
            image_view.setImageResource(R.drawable.camera_icon);
        }
    }

    private void init() {
        title_view=findViewById(R.id.group_title);
        desc_view=findViewById(R.id.group_desc);
        category_view=findViewById(R.id.category_group);
        mentor_view=findViewById(R.id.mentor);
        exp_view=findViewById(R.id.mentor_exp);
        number_view=findViewById(R.id.phone_number);
        image_view=findViewById(R.id.icon_group);
    }
}
