package com.arish1999.enrollapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class enroll_fragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage storage;
    ImageView profilePhoto;
    Uri selectedImage;
    Button addUserBtn;
    EditText fname,lname,dob,gender,country,state,hometown,phonenumber,telephonenumber;
    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.enroll_layout,container,false);
        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        profilePhoto = root.findViewById(R.id.profileImage);
        fname = root.findViewById(R.id.firstName);
        lname= root.findViewById(R.id.lastName);
        dob = root.findViewById(R.id.doB);
        gender = root.findViewById(R.id.genderId);
        country = root.findViewById(R.id.countryId);
        state = root.findViewById(R.id.stateId);
        hometown = root.findViewById(R.id.homeTown);
        phonenumber = root.findViewById(R.id.phoneNumber);
        telephonenumber = root.findViewById(R.id.telephoneNumber);

        addUserBtn = root.findViewById(R.id.addBtn);
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = fname.getText().toString();
                String lastName = lname.getText().toString();
                String dateBirth = dob.getText().toString();
                String gender1 = gender.getText().toString();
                String country1 = country.getText().toString();
                String state1 = state.getText().toString();
                String hometown1 = hometown.getText().toString();
                String phonenumber1 = phonenumber.getText().toString();
                String telephone1 = telephonenumber.getText().toString();
                int phoneLength = phonenumber1.length();

                if(firstName.isEmpty())
                {
                    fname.setError("Fill this field");
                }
                else if(lastName.isEmpty())
                {
                    lname.setError("Fill this field");
                }
                else if(dateBirth.isEmpty())
                {
                    dob.setError("Fill this field");
                }
                else if(gender1.isEmpty())
                {
                    gender.setError("Fill this field");
                }
                else if(country1.isEmpty())
                {
                    country.setError("Fill this field");
                }
                else if(state1.isEmpty())
                {
                    state.setError("Fill this field");
                }
                else if(hometown1.isEmpty())
                {
                    hometown.setError("Fill this field");
                }
                else if(phonenumber1.isEmpty())
                {
                    phonenumber.setError("Fill this field");
                }
                else if(phoneLength != 10)
                {
                    phonenumber.setError("Invalid Phone Number");
                }

                else if(!dateValid(dateBirth))
                {
                    dob.setError("Date not valid:Please enter in  format (\"dd/MM/yyyy\")");
                }

                else if(telephone1.isEmpty())
                {
                    telephonenumber.setError("Fill this field");
                }



                else {
                    if (selectedImage != null) {
                        StorageReference reference = storage.getReference().child("Profiles").child(lastName);
                        reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            String profileUri = uri.toString();
                                            UsersModel usersModel = new UsersModel(firstName, lastName, dateBirth, gender1, country1, state1, hometown1, phonenumber1, telephone1, profileUri);
                                            database.getReference()
                                                    .child("users")
                                                    .child(phonenumber1)
                                                    .setValue(usersModel)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(context, "User has been added", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });

                                        }
                                    });

                                }

                            }
                        });


                    } else {
                        UsersModel usersModel = new UsersModel(firstName, lastName, dateBirth, gender1, country1, state1, hometown1, phonenumber1, telephone1, "no image");
                        database.getReference()
                                .child("users")
                                .child(phonenumber1)
                                .setValue(usersModel)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "User has been added", Toast.LENGTH_SHORT).show();

                                    }
                                });


                    }




                    }
                }

            private boolean dateValid(String dateBirth) {
                SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
                sdfrmt.setLenient(false);
                try
                {
                    Date javaDate = sdfrmt.parse(dateBirth);

                }
                /* Date format is invalid */
                catch (ParseException e)
                {

                    return false;
                }
                /* Return true if date format is valid */
                return true;
            }


        });




        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null)
        {
            if(data.getData()!=null)
            {
               profilePhoto.setImageURI(data.getData());
                selectedImage=data.getData();
            }
        }
    }
}
