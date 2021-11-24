package com.example.student;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    List<Model> my_list;
    Context context;

    public CustomAdapter(List<Model> my_list, Context context) {
        this.my_list = my_list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Model model = my_list.get(position);
        if(!TextUtils.isEmpty(model.id)) {
            holder.name.setText(model.name);
            holder.name.setVisibility(View.VISIBLE);
            holder.role.setText(model.role);
            holder.role.setVisibility(View.VISIBLE);
            holder.height.setText(model.height);
            holder.height.setVisibility(View.VISIBLE);
            holder.weight.setText(model.weight);
            holder.weight.setVisibility(View.VISIBLE);
            holder.country.setText(model.country);
            holder.country.setVisibility(View.VISIBLE);

            holder.Delete.setVisibility(View.VISIBLE);

            holder.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            "http://halitpractice.tech/AndroidApiTestNEWStudentPanel/OgrenciPanel/api/delete.php",

                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject object = new JSONObject(response);
                                        String check = object.getString("statusC");
                                        if (check.equals("true")){
                                            Delete(position);
                                            Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> deleteParams = new HashMap<>();
                            deleteParams.put("id",model.getId());
                            return deleteParams;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);

                }
            });


            Picasso.with(context).load(model.getImage()).into(holder.image);

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("id", model.getId());
                    intent.putExtra("image", model.getImage());
                    intent.putExtra("name", model.getName());
                    intent.putExtra("role", model.getRole());
                    intent.putExtra("height", model.getHeight());
                    intent.putExtra("weight", model.getWeight());
                    intent.putExtra("country", model.getCountry());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//not recommend
                    context.startActivity(intent);

                }
            });
        } else {
            Picasso.with(context).load(model.getImage()).into(holder.image);
            holder.comments.setText(model.comment);
            holder.comments.setVisibility(View.VISIBLE);
            holder.formats.setText(model.format);
            holder.formats.setVisibility(View.VISIBLE);

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentBigPictureActivity.class);
                    intent.putExtra("comments", model.getComment());
                    intent.putExtra("format", model.getFormat());
                    intent.putExtra("image", model.getImage());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//not recommend
                    context.startActivity(intent);

                }
            });



//            with this Method I try to delete image from DetailActivity but this method does not help works , it maybe missing something
            holder.DeleteImageFromDetailActivity.setVisibility(View.VISIBLE);
            holder.DeleteImageFromDetailActivity.setOnClickListener(new View.OnClickListener() {
                String id;
                @Override
                public void onClick(View v) {

                    final String url = "http://halitpractice.tech/AndroidApiTestNEWStudentPanel/OgrenciPanel/api/deleteMultiPictureByIdPOST.php"+id;
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d( "onResponse: ",response);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d( "onErrorResponse: ",""+error);

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> map = new HashMap<>();
                            map.put("id",id);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);

                }
            });




        }

    }






    @Override
    public int getItemCount() {
        return my_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,role,height,weight,country,comments,formats;
        ImageButton Delete,DeleteImageFromDetailActivity;
        Button Edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            role=itemView.findViewById(R.id.role);
            height=itemView.findViewById(R.id.heights);
            weight=itemView.findViewById(R.id.weights);
            country=itemView.findViewById(R.id.countries);
            comments=itemView.findViewById(R.id.comments);
            formats=itemView.findViewById(R.id.formats);
            Delete = itemView.findViewById(R.id.rcy_delete);
            DeleteImageFromDetailActivity = itemView.findViewById(R.id.rcy_delete2);

            Edit = itemView.findViewById(R.id.rcy_edit);


        }
    }
    public void Delete(int item){
        my_list.remove(item);
        notifyItemRemoved(item);
    }
}
