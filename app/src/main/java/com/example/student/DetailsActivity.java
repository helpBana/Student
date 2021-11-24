package com.example.student;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    public static final String TAG = "TAG";

    ImageView imageView;
    TextView name,role,boyu,eni,ulke;
    String id;
    String playername,playerrole,playerheight,playerweight,playerulke;
    String image;
    private Context context;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView=findViewById(R.id.image);
        name=findViewById(R.id.name);
        role=findViewById(R.id.role);
        boyu=findViewById(R.id.heights);
        eni=findViewById(R.id.weights);
        ulke=findViewById(R.id.countries);
        recyclerView = findViewById(R.id.recycler);
        id = getIntent().getStringExtra("id");
        playername=getIntent().getStringExtra("name");
        playerrole=getIntent().getStringExtra("role");
        playerheight=getIntent().getStringExtra("height");
        playerweight=getIntent().getStringExtra("weight");
        playerulke=getIntent().getStringExtra("country");
        image=getIntent().getStringExtra("image");
        name.setText(playername);
        role.setText(playerrole);
        boyu.setText(playerheight);
        eni.setText(playerweight);
        ulke.setText(playerulke);
        Picasso.with(this).load(image).into(imageView);

        fetchStudent_id();
    }

    private void fetchStudent_id() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://halitpractice.tech/AndroidApiTestNEWStudentPanel/OgrenciPanel/api/getStudentById.php?id="+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            JSONArray jsonArrays = jsonObject.getJSONArray("student_images");
                            Log.d(TAG, "onResponse: "+ jsonArrays);

                            List<Model> models = new ArrayList<>();

                            try {

                                for (int i = 0; i < jsonArrays.length(); i++) {
                                    JSONObject object = jsonArrays.getJSONObject(i);
                                    String image = object.getString("src");
                                    String comment = object.getString("comment");
                                    String format = object.getString("format");
                                    String studentId = object.getString("student_id");
                                    if(studentId.equals(id)) {
                                        Model model = new Model(image, comment,format);
                                        models.add(model);
                                    }
                                }

                            } catch (Exception e) {
                                Log.d(TAG, "onResponse: "+e);
                            }

                            CustomAdapter adapter = new CustomAdapter(models, getApplicationContext());
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(DetailsActivity.this, 1);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " +error.getMessage());

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
