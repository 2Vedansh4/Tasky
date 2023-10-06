package com.example.tasky;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements RecyclerContactAdapter.ButtonClickListener,RecyclerContactAdapter.CuttonClickListener  {

    ArrayList<ContactModel> arrContacts = new ArrayList<>();
    Button button;
    DatabaseHelper database = new DatabaseHelper(this);
    RecyclerContactAdapter adapter = new RecyclerContactAdapter(this,arrContacts);
    TextView textView2 ;
    Button button24;
    int points ;



    @Override
    public void onButtonClicked(int position) {
    database.deleteContact(arrContacts.get(position).id);
        // Remove the corresponding item from the list
        arrContacts.remove(position);
        // Notify the adapter that the data has changed
       adapter.notifyDataSetChanged();


    }
    @Override
    public void onCuttonClicked(int position) {
        Intent itimer = new Intent(MainActivity.this,timer.class);
        String time = arrContacts.get(position).name;
        String[] timeParts = time.split(":");
        String hours = timeParts[0];
        String minutes = timeParts[1];
        String secs = timeParts[2];
        itimer.putExtra("id",arrContacts.get(position).id);
        itimer.putExtra("number",arrContacts.get(position).number);
       itimer.putExtra("posn",position);
       itimer.putExtra("secs",secs);

        itimer.putExtra("hrs",hours);
       itimer.putExtra("mins",minutes);
     startActivity(itimer);

    }




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", timer.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2 = findViewById(R.id.textView4);
        button24 = findViewById(R.id.button24);

        Intent in = getIntent();
       int posni =  in.getIntExtra("position",-1);
       String time = in.getStringExtra("time") ;
        contactmodel1 model1 = new contactmodel1();

        ArrayList<contactmodel1> arrContacts1 = database.fetchContact();

        button = findViewById(R.id.button);
        RecyclerView recyclerView = findViewById(R.id.recyclerContact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent inext = new Intent(MainActivity.this,tasksadd.class);
        Intent iprev = getIntent();
        button24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                points = 0 ;
                editor.putInt("points", 0);
                editor.apply();
                int i = 0 ;
                textView2.setText("points" +  " : " + String.valueOf(points));
                while(!arrContacts.isEmpty()){
                database.deleteContact(arrContacts.get(i).id);
                // Remove the corresponding item from the list
                arrContacts.remove(i);
                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();}

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(inext);
            }
        });


      for(int i = 0;i <arrContacts1.size();i++){
          arrContacts.add(new ContactModel(arrContacts1.get(i).id,arrContacts1.get(i).name,arrContacts1.get(i).fame));
      }

        adapter.setButtonClickListener((RecyclerContactAdapter.ButtonClickListener) this);
        adapter.setCuttonClickListener((RecyclerContactAdapter.CuttonClickListener) this);
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", this.MODE_PRIVATE);

        super.onResume();
         points = sharedPreferences.getInt("points", 0);
        textView2.setText("points" +  " : " + String.valueOf(points));


        adapter.notifyDataSetChanged();


    }


}