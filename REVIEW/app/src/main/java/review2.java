import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.example.review.R;

import java.time.Period;

public class review2 {

    package com.example.bohra.savvysavingappfinal;
/**
 * This class includes the necessary methods for setting up the users saving goals.
 * It includes a calendar function for setting the date and will be used to show
 users how close they are
 * to their saving goal by the app at a later date.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
    public class SavingSetup extends AppCompatActivity
    {
        android.icu.util.Calendar currentTime =
                android.icu.util.Calendar.getInstance();
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_saving_setup);
            Long min = currentTime.getTime().getTime();
            final CalendarView savingCalendar = (CalendarView)
                    findViewById(R.id.calendarView);
            savingCalendar.setMinDate(min);
            final EditText goalAmount = (EditText)
                    findViewById(R.id.savingAmountEditText);
            final EditText pledgeAmount = (EditText)
                    findViewById(R.id.pledgeAmountEditText);
            Button goHomeBtn = (Button) findViewById(R.id.continueDateButton);
 /*
 *Constraint listener for checking whether data has been entered by the user
for setting a saving goal.
 *This prompts the user to input a valid value before continuing to the home
screen.
 */
            goHomeBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Spinner pledgePeriodSpinner = (Spinner)
                            findViewById(R.id.periodSpinner3);
                    //Period incomePeriod =
                    Period.valueOf(incomePeriodSpinner.getSelectedItem().toString());
                    String goalCheck = goalAmount.getText().toString();
                    String pledgeCheck = pledgeAmount.getText().toString();
                    if ((goalCheck.isEmpty()) || (pledgeCheck.isEmpty()))
                    {
                        Toast goalLowToast = Toast.makeText(getApplicationContext(),
                                "Your must set a pledge and goal!", Toast.LENGTH_SHORT);
                        goalLowToast.show();
                    }
                    else
                    {
                        int goalAmountToSend =
                                Integer.parseInt(goalAmount.getText().toString());
                        int pledgeAmountToSend =
                                Integer.parseInt(pledgeAmount.getText().toString());
                        if ((goalAmountToSend < 0) || (pledgeAmountToSend < 0))
                        {
                            Toast goalLowToast =
                                    Toast.makeText(getApplicationContext(), "Your must set a pledge and goal!",
                                            Toast.LENGTH_SHORT);
                            goalLowToast.show();
                        }
                        //This is the calender function that is used to allow user to
                        set a saving goal date in a visually
                        //gratifying way.
else
                        {
                            Date d = new
                                    Date(Long.parseLong(String.valueOf(savingCalendar.getDate())));
                            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
                            String dateText = df2.format(d);
                            String content = "Goal:" + goalAmount.getText().toString()
                                    + "\n" +
                                    "Target Date Long:" + savingCalendar.getDate() +
                                    "\n" +
                                    "Readable Date:" + dateText + "\n" +
                                    "Pledge Period:" +
                                    pledgePeriodSpinner.getSelectedItem().toString() + "\n" +
                                    "Pledge Goal:" + pledgeAmount.getText().toString()
                                    + "\n";
                            IO io = new IO();
                            io.updateFile("finance.txt", content);
                            Intent goToHome = new Intent(getApplicationContext(),
                                    MainActivity.class);
                            startActivity(goToHome);
                        }
                    }
                }
            });
        }
    }
}
