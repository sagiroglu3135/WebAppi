package com.example.hatayli.webappi;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.example.hatayli.webappi.Models.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class UserDetailActivity extends AppCompatActivity {


    ImageView userImage;
    TextView userNameBold,userID,userContacts,userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        initView();
        Intent intent= getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        User user= (User) bundle.getSerializable("user");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(user.getFirst_name()+" "+user.getLast_name());

        //Picasso get image from url and set it ImageView
        //Picasso.get().load( intent.getStringExtra("img_Url")).into(userImage);

        //Picasso get circular image from url
        Picasso.get().load(user.getImageUrl())
                .placeholder(R.drawable.circle_image_placeholder)
                .error(R.drawable.ic_person_40dp)
                .transform(new CircleTransformation())
                .into(userImage);


        userNameBold.setText(user.getFirst_name()+" "+user.getLast_name());
        userID.setText(user.getId());
        userContacts.setText(Math.round(Math.random()*100)+"");
        userEmail.setText(user.getEmail());


    }

    private void initView() {
        userImage=findViewById(R.id.user_image);
        userNameBold=findViewById(R.id.user_name_bold);
        userID=findViewById(R.id.user_id);
        userContacts=findViewById(R.id.user_contacts);
        userEmail=findViewById(R.id.user_email);

        //set click events in email textView
        userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //stackoverflow.com/questions/2197741/how-to-send-emails-from-my-android-application
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{userEmail.getText().toString()});
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Image transfom  normal form to circular form.
    class CircleTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                    super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}




