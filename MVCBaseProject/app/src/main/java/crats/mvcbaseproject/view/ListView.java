package crats.mvcbaseproject.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import crats.mvcbaseproject.R;

/**
 * Created by yashshah on 2017-12-09.
 */

public class ListView extends AppCompatActivity {


    public static android.widget.ListView list_view;
    private ArrayList<String> mListview = new ArrayList<String>();


    ArrayAdapter<String> myArrayAdapter;

    String total = "";

    TextView prname1;

    RatingBar ratingBar1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewactivity);





        prname1 = (TextView)findViewById(R.id.prname);
        list_view = (android.widget.ListView)findViewById(R.id.listview2);
        myArrayAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, R.id.rowTextView,mListview);
        ratingBar1 = (RatingBar)findViewById(R.id.ratingBar1);

        list_view.setAdapter(myArrayAdapter);




        Bundle sum = getIntent().getExtras();
        total = sum.getString("qrcode");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference().child("primarydatabase");


       Query query = databaseReference.orderByChild("qrcode").equalTo(total);

        query.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {





    String myChildValues = "";
    int a = 0;
    int count = 0;
    for (DataSnapshot snapshot : dataSnapshot.getChildren())
    {
        myChildValues = snapshot.child("review").getValue(String.class);
        mListview.add(myChildValues);
        myArrayAdapter.notifyDataSetChanged();

        String progress = snapshot.child("progress").getValue(String.class);
        int b = Integer.parseInt(progress);
        a = a + b;
        count++;
        String prname = snapshot.child("productName").getValue(String.class);
        prname1.setText(prname);
    }


    try
    {
        int c = a / count;
        ratingBar1.setProgress(c);
    }
    catch(Exception e)
    {

        ratingBar1.setVisibility(View.INVISIBLE);
        mListview.add("Sorry,");
        mListview.add("No Review for this item.");
        myArrayAdapter.notifyDataSetChanged();

    }


                //ratingBar1.setProgress(c);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



}
