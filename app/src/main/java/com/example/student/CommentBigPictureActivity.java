package com.example.student;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class CommentBigPictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_big_picture);
        ImageView imageView=findViewById(R.id.image);
        TextView comment=findViewById(R.id.comments);
        TextView format=findViewById(R.id.formats);
        String comments=getIntent().getStringExtra("comments");
        String formats=getIntent().getStringExtra("format");
        String image=getIntent().getStringExtra("image");
        Picasso.with(this).load(image).into(imageView);
        comment.setText(comments);
        format.setText(formats);

    }
}
