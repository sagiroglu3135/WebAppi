package com.example.hatayli.webappi;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;


import com.example.hatayli.webappi.AsyncTasks.AsyncWebCall;
import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {

    static public ShimmerFrameLayout shimmerFrameLayout;
    static public RecyclerView recyclerView;
    boolean internetConnection=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();//findViewsById methods;

        //checking network connection.
        if(!isNetworkConnected()){
            internetConnection=false;

            //show alert dialog about internet connection
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Ağ Bağlantısı Uyarısı");
            alertDialogBuilder.setMessage("Cihazın internete bağlanması gerekli." +
                    "Lütfen ağ bağlantınızı açınız");
            alertDialogBuilder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

           
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
        else{

            //internetConnection is true than callWebTask
            internetConnection=true;
            AsyncWebCall asyncWebCall= new AsyncWebCall(this);
            asyncWebCall.execute();

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!internetConnection){
            shimmerFrameLayout.hideShimmer();
        }else{
            shimmerFrameLayout.startShimmer();
        }

    }

    private void initViews() {
        shimmerFrameLayout=findViewById(R.id.shimmerFrameLayout);
        recyclerView=findViewById(R.id.recyclerView);
    }

    //https://gelecegiyazanlar.turkcell.com.tr/soru/internet-baglantisi-androidte-nasil-kontrol-edilir
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
