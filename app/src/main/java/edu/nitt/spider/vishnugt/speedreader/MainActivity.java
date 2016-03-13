/*
speedReader by Meliodas on 13/03/2016
 */

package edu.nitt.spider.vishnugt.speedreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView outputText;
    Button btn;
    EditText speedview, contenttoread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initializing views
        outputText = (TextView)findViewById(R.id.outtext);
        speedview = (EditText)findViewById(R.id.speed);
        contenttoread = (EditText)findViewById(R.id.contenttoread);
        btn = (Button)findViewById(R.id.readbtn);
    }


    int iterator=0;
    int speed = 400;
    String textsplitted[];
    boolean isReading = false;

    public void btnfunction(View v)
    {


        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted() && iterator<textsplitted.length-1 && isReading==true) {
                        Thread.sleep(speed);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                outputText.setText(textsplitted[iterator++]);
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

        speed = Integer.parseInt(speedview.getText().toString());
        if(speedview.getText().toString().isEmpty() || speed==0)
        {
            Toast.makeText(this, "Speed can't be null, setting it to 400!", Toast.LENGTH_SHORT).show();
            speed=400;
        }

        isReading = true;
        iterator=0;
        textsplitted = contenttoread.getText().toString().split(" ");


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
