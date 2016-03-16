/*
speedReader by Meliodas on 13/03/2016
 */

package edu.nitt.spider.vishnugt.speedreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Spinner s_speed,n_words;
    TextView outputText;
    Button btn;
    EditText speedview, contenttoread;
    int iterator=0;
    int speed = 400;
    String[] textsplitted;
    boolean isReading = false;
    int no=1;
    public static String initial_content = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initializing views
       // outputText = (TextView)findViewById(R.id.outtext);
      //  speedview = (EditText)findViewById(R.id.speed);
        contenttoread = (EditText)findViewById(R.id.contenttoread);
        btn = (Button)findViewById(R.id.readbtn);
        s_speed= (Spinner) findViewById(R.id.speedspinner);
        n_words= (Spinner) findViewById(R.id.wordsSpinner);


        initializeValues();

        s_speed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        speed=450;
                        break;
                    case 1:
                        speed=350;
                        break;
                    case 2:
                        speed=200;
                        break;
                    case 3:
                        speed=150;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                speed=200;
            }
        });

        n_words.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        no=1;

                        break;
                    case 1:
                        no=2;

                        break;
                    case 2:
                        no=3;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                no=1;
            }
        });

    }


    public void initializeValues(){
        //Initializing values for spinners


        List<String> speedValues=new ArrayList<>();
        speedValues.add("Slow");
        speedValues.add("Medium");
        speedValues.add("Fast");
        speedValues.add("SuperFast");

        List<String> words=new ArrayList<>();
        words.add("1");
        words.add("2");
        words.add("3");

        ArrayAdapter<String> speedAdapter=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,speedValues);
        s_speed.setAdapter(speedAdapter);

        ArrayAdapter<String> wordAdapter=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,words);
        n_words.setAdapter(wordAdapter);
    }


    public void btnfunction(View v)
    {

        initial_content=contenttoread.getText().toString();
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted() && iterator<textsplitted.length-1 && isReading==true) {
                        Thread.sleep(speed);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int j=0;
                                String text="";

                                while ( j<no){
                                            if(!textsplitted[iterator].contains("~")) {
                                                text += textsplitted[iterator++];
                                                text += " ";
                                            }
                                            j++;



                                    contenttoread.setCursorVisible(!isReading);
                                    contenttoread.setText(text);
                                }
                                Log.d("text",text);
                                //outputText.setText(text);

                                if(iterator==textsplitted.length-2)
                                {

                                    isReading=false;
                                    btn.setText("Speed Read");
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        String content= "";

        if(isReading)
        {

            isReading=false;
            btn.setText("Speed Read");
            return ;
        }


        if(contenttoread.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Content to be read is Empty :(", Toast.LENGTH_SHORT).show();
            return;
        }
        /*
        speed = Integer.parseInt(speedview.getText().toString());
        if(speedview.getText().toString().isEmpty() || speed==0)
        {
            Toast.makeText(this, "Speed can't be null, setting it to 400!", Toast.LENGTH_SHORT).show();
            speed=400;
        }
        */
        isReading = true;
        iterator=0;
        //textsplitted = contenttoread.getText().toString().split(" ");
        String temp = "";
        switch(contenttoread.getText().toString().split(" ").length%6){
            case 5:
                temp=contenttoread.getText().toString()+" ~~~~~";
                break;
            case 4:
                temp=contenttoread.getText().toString()+" ~~~~";
                break;
            case 3:
                temp=contenttoread.getText().toString()+" ~~~";
                break;
            case 2:
                temp=contenttoread.getText().toString()+" ~~";
                break;
            case 1:
                temp=contenttoread.getText().toString()+" ~";
                break;
            case 0:
                temp=contenttoread.getText().toString();
                break;

        }
        textsplitted=temp.split(" ");
        Log.d("textsplitted", temp);
        t.start();
        btn.setText("Stop!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
