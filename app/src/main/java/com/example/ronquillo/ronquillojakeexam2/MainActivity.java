package com.example.ronquillo.ronquillojakeexam2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference root;
    EditText first, last, exam1, exam2, result;
    ArrayList<String> keyList;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        root = db.getReference("grade");
        first = findViewById(R.id.firstname);
        last = findViewById(R.id.lastname);
        exam1 = findViewById(R.id.exam1txt);
        exam2 = findViewById(R.id.exam2txt);
        result = findViewById(R.id.result);
        keyList = new ArrayList<>();
    }


    @Override
    protected void onStart() {
        super.onStart();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ss: dataSnapshot.getChildren()) {
                    keyList.add(ss.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addRecord(View v){
        if(v.getId() == R.id.avebtn){
            int x, y;
            try{
                x = Integer.parseInt(exam1.getText().toString());
                y = Integer.parseInt(exam2.getText().toString());
                int c = (x+y)/2;

                String fname = first.getText().toString();
                String lname = last.getText().toString();
                Student sgrade = new Student(fname,lname,c);
                String key = root.push().getKey();
                root.child(key).setValue(sgrade);
                keyList.add(key);


                root.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        index = (int) dataSnapshot.getChildrenCount() - 1;
                        Student stud = dataSnapshot.child(keyList.get(index)).getValue(Student.class);
                        result.setText(stud.getResult().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            catch (NumberFormatException e){
                Toast.makeText(this, "Invalid input.", Toast.LENGTH_LONG).show();
            }


        }

        Toast.makeText(this,"Added to Database",Toast.LENGTH_LONG).show();
    }
}