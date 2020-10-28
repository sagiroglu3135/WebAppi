package com.example.hatayli.webappi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatayli.webappi.MainActivity;
import com.example.hatayli.webappi.Models.User;
import com.example.hatayli.webappi.R;
import com.example.hatayli.webappi.UserDetailActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class mRecyclerViewAdapter extends RecyclerView.Adapter<mRecyclerViewAdapter.mViewHolder>{

    Context context;
    List<User> users;

    public mRecyclerViewAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public mRecyclerViewAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.row_layout,viewGroup,false);

        return new mViewHolder(view) ;

    }

    @Override
    public void onBindViewHolder(@NonNull mRecyclerViewAdapter.mViewHolder mViewHolder, int i) {


        //get circular image with picasso
        Picasso.get().load(users.get(i).getImageUrl())
                .placeholder(R.drawable.circle_image_placeholder)
                .error(R.drawable.circle_image_placeholder)
                .transform(new CircleTransformation())
                .into( mViewHolder.user_image);
        mViewHolder.user_name.setText(users.get(i).getFirst_name()+" "+users.get(i).getLast_name());
        mViewHolder.email.setText(users.get(i).getEmail());

        //Picasso get image from url and set it ImageView
        //Picasso.get().load( users.get(i).getImageUrl()).transform(new CircleTransform()).into(mViewHolder.user_image);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder{

        ImageView user_image;
        TextView user_name;//firstName+LastName
        TextView email;

        public mViewHolder(@NonNull final View itemView) {
            super(itemView);

            user_image=itemView.findViewById(R.id.img);
            user_name=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int itemPosition = MainActivity.recyclerView.getChildLayoutPosition(v);
                    Intent intent = new Intent(v.getContext(), UserDetailActivity.class);

                    Bundle bundle= new Bundle();
                    bundle.putSerializable("user",users.get(itemPosition));
                    intent.putExtra("bundle",bundle);
                    context.startActivity(intent);
                }
            });

        }
    }


}

class CircleTransformation implements Transformation{
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