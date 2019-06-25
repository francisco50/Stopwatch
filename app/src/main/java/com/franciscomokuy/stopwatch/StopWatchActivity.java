package com.franciscomokuy.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import java.util.Locale;
import android.os.Handler;
import android.widget.TextView;

public class StopWatchActivity extends Activity {

                                                                                           //Number of seconds to be displayed on the stopwatch
    private int seconds = 0;
                                                                                          //to check whether the time is running or not
    private boolean running;
                                                                                          //checks if the app ws running before it was closed or wasn't, before it went visible and invisible.
    private boolean wasrunning;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

                                                                                      //restore activities state by getting values from the Bundle.
             if(savedInstanceState != null)
                 {
                     seconds = savedInstanceState.getInt("seconds");
                     running = savedInstanceState.getBoolean("running");
                     wasrunning = savedInstanceState.getBoolean("wasrunning");
                                                                                    //restore the state of the wasrunning variable if the activity was recreated
                 }

                                                                                    //we are using a seperate method to to update the stopwatch. we are starting it when the activity is created and the activity gets started.
                 runTimer();

    }

                                                                                  ///this method is responsible for saving the states of the variables created
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
                                                                                   //save the state of the variables in the activity's onSaveInstanceState() method.
    savedInstanceState.putInt("seconds",seconds);
    savedInstanceState.putBoolean("running", running);
    savedInstanceState.putBoolean("wasrunning",wasrunning);
                                                                                   //save the state of the was running variable
    }

                                                                              //These methods were created for the instance when the app gets invisible, the app will stop and continue after the app becomes visible again
                                                                              //we don't really need this but we could use it, we actually need our app to continue while the app is invisible, so without this methods
                                                                                 //we will be fine. But we might use this for a game that we want to pause and continue at where we left if the activity goes invisible.
    protected void onPause()
    {
        super.onPause();
                                                                         //record whether the stopwatch was running when the onStop() method was called.
        wasrunning = running;
        running = false;
    }

    protected void onResume()
    {
                                                                              //When you override any activity lifecycle method in your activity, you need to call the activity superclass method.If you don't, you will get an exception.
          super.onResume();
        if(wasrunning)
        {
            running = true;
        }
    }



                                                                                           //gets called when the start button gets clicked
    public void onclickStart(View view)
    {
        running = true;
    }
                                                                                           //gets called when the stop button gets clicked
    public void onclickStop(View view)
    {
        running = false;
    }
                                                                                          //gets called when the reset button gets clicked
    public void onclickReset(View view)
    {
        running = false;
        //if not running start again from 0;
        seconds = 0;
    }

    private void runTimer()
    {
        final TextView timeView = (TextView)findViewById(R.id.time_view);

                                                                                           //we used a handler because we didn't want to block the main Android thread, in non-Android java programs,
                                                                                           // you can perform tasks like this using a background thread.
                                                                                           //A handler is an Android class you can use to schedule code that should run at some point in the future.
                                                                                           // you can also use it to run code that needs to run on a different thread than the main Android thread.
                                                                                           //To use a handler you wrap the code you wish to schedule in a Runnable object.
                                                                                             //post method used for code that needs to run as soon as possible, this method takes one parameter, an object
                                                                                           //of type Runnable.
                                                                                          //A runnable is an object in AndroidVille, is just like a Runnable in the Runnable's run() method.
        final Handler handler = new Handler();                                            // this is the initialisation for both post and PostDelayed.
        handler.post(new Runnable() //use a handler to post code, this makes the code run as soon as possible
        {
            @Override
            public void run()
            {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours,minutes,secs);

                timeView.setText(time);                                                     //set the text view text

                if(running)
                {
                    seconds++;                                                               //if running increment the seconds variable
                }
                handler.postDelayed(this,1000);
                                                                                           //postdelayed method takes 2 parameters, a Runnable and a long. The Runnable contains the code that you want to run in its run()
                                                                                           //method, and the long specifies the number of milliseconds you wish to delay the code by. The code will run as so an possible after the delay.
            }
        });

    }


}
