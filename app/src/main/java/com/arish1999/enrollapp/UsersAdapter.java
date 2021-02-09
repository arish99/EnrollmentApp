package com.arish1999.enrollapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {
    FirebaseDatabase database;

    Context context;
    ArrayList<UsersModel> usersModelList;

    public UsersAdapter(Context context, ArrayList<UsersModel> usersModelList) {
        this.context = context;
        this.usersModelList = usersModelList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {


        database = FirebaseDatabase.getInstance();
        UsersModel model = usersModelList.get(position);
        int years = getCurrentAge(model.getDate_of_birth());
        String getYears = String.valueOf(years);
        holder.name.setText(model.getfName()+" "+model.getlName());
        holder.age.setText(getYears+"|");
        holder.hometown.setText(model.getHomeTown());
        holder.gender.setText(model.getGender()+"|");
        Glide.with(context).load(model.getProfileImage()).
                placeholder(R.drawable.avatar).into(holder.profileImg);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("users").child(model.getPhoneNumber()).removeValue();

            }
        });


    }

    private int getCurrentAge(String date_of_birth) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(date_of_birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        return age;
    }


    @Override
    public int getItemCount() {
        return usersModelList.size();
    }
}
class UsersViewHolder extends RecyclerView.ViewHolder{

    TextView name,gender,age,hometown;
    ImageView profileImg,deleteButton;


    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.userName);
        gender = itemView.findViewById(R.id.userGender);
        age =itemView.findViewById(R.id.userAge);
        hometown = itemView.findViewById(R.id.userHometown);
        profileImg = itemView.findViewById(R.id.profile);
        deleteButton = itemView.findViewById(R.id.deleteBtn);

    }
}
