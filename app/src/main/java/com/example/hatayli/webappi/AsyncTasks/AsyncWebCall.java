package com.example.hatayli.webappi.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;
import com.example.hatayli.webappi.Adapters.mRecyclerViewAdapter;
import com.example.hatayli.webappi.MainActivity;
import com.example.hatayli.webappi.Models.User;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AsyncWebCall extends AsyncTask<Void, Void, List<User>> {

    static String callStatus="Okey";
    Context context;


    public AsyncWebCall(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        //vertical shimmer animation code
//        Shimmer.AlphaHighlightBuilder builder = new Shimmer.AlphaHighlightBuilder();
//        builder.setDirection(com.facebook.shimmer.Shimmer.Direction.TOP_TO_BOTTOM);
//        MainActivity.shimmerFrameLayout.setShimmer(builder.build());

        //shimmer animation starting
        MainActivity.shimmerFrameLayout.startShimmer();
    }


    private String getDataFromServer(List<User> userList) {


        List<User> users = userList;
        try {
            int page = 1;
            while (page <= 2) {
                OkHttpClient client = new OkHttpClient();
                String url = "https://reqres.in/api/users?page=" + page + "";
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

                if (response.code() != 200)
                    return response.code()+"";
                else{
                    JSONObject packet = new JSONObject(response.body().string());
                    JSONArray array = packet.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        User u = new User();

                        String first_name = object.getString("first_name");
                        String last_name = object.getString("last_name");
                        String email = object.getString("email");
                        String imgUrl = object.getString("avatar");
                        String id=object.getString("id");

                        u.setFirst_name(first_name);
                        u.setLast_name(last_name);
                        u.setEmail(email);
                        u.setImageUrl(imgUrl);
                        u.setId(id);

                        users.add(u);
                    }
                    page++;
                }
            }

            return "Okey";

        } catch (Exception ex) {
                return ex.getMessage();
        }

    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        List<User> users = new ArrayList<>();
            callStatus= getDataFromServer(users);
            return users;
        }

    @Override
    protected void onPostExecute(final List<User> users) {
        super.onPostExecute(users);


        if(callStatus=="Okey"){
            MainActivity.shimmerFrameLayout.stopShimmer();
            MainActivity.recyclerView.setAdapter(new mRecyclerViewAdapter(context,users));
            MainActivity.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            MainActivity.shimmerFrameLayout.setVisibility(View.GONE);
        }
        else{
            Toast.makeText(context,"An error occurred while processing your request",Toast.LENGTH_SHORT).show();
        }


    }


}
