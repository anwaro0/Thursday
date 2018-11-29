package com.example.wafa.studentapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilesCS383Fragment2 extends Fragment {

    private View mMainView;

    private DatabaseReference mUserDatabase,currentUser;   //databaseReference

    private FirebaseUser mCurrentUser;
//Android Layout

    CircleImageView mDisplayImage;
    Button mNameBtn, mImageBtn , update;
    ListView listView;
    TextView Viewname;
    public FilesCS383Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView= inflater.inflate(R.layout.fragment_files_cs383_fragment2, container, false);



        listView = (ListView) mMainView.findViewById(R.id.listInfoStudent);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        currentUser = FirebaseDatabase.getInstance().getReference().child("Student").child(current_uid);
        Viewname = (TextView) mMainView.findViewById(R.id.nameView);


        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User info = new User();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    final String name = info.setName(dataSnapshot.child("name").getValue().toString());

                    Viewname.setText(name);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Student
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child(current_uid).child("CS383");
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                showUserProfile(dataSnapshot);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        // Inflate the layout for this fragment
        return mMainView;
    }

    public void showUserProfile(DataSnapshot dataSnapshot) {

        final User info = new User();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {


            // final String name = info.setName(dataSnapshot.child("name").getValue().toString());
            final String attendance = info.setAttendance(dataSnapshot.child("attendance").getValue().toString());
            final String quiz = info.setQuize(dataSnapshot.child("quiz").getValue().toString());
            final String mid = info.setMid(dataSnapshot.child("mid").getValue().toString());
            final String finals = info.setFinals(dataSnapshot.child("finals").getValue().toString());
            //final String finals = dataSnapshot.child("image").getValue().toString();

            // Picasso.with(OtherStudentProfile.this).load(image).placeholder(R.drawable.default_img).into(mDisplayImage);

            ArrayList<String> array = new ArrayList<>();
            //array.add(name);
            array.add(attendance);
            array.add(quiz);
            array.add(mid);
            array.add(finals);

            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, array);

            listView.setAdapter(arrayAdapter);


        }
    }








}


