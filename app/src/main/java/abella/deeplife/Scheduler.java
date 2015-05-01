package abella.deeplife;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import Database.Schedule;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/17/2015.
 */
public class Scheduler extends Activity{
    private PendingIntent pendingIntent;
    DatePicker pickerDate;
    TimePicker pickerTime;
    Button buttonSetAlarm;
    TextView info;
    String gotbread;
    EditText desc;
    int parser;
    UsersDataSource usersDataSource;
    private ScheduleClient scheduleClient;
    final static int RQS_1 = 1;
   Schedule schedule = new Schedule();

    private static final String LOGTAGA = "tota";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();


        usersDataSource = new UsersDataSource(this);
        usersDataSource.open();
        desc = (EditText)findViewById(R.id.etscheduledescription);
        Bundle gotbasket = getIntent().getExtras();
        gotbread = gotbasket.getString("key");
        parser = Integer.parseInt(gotbread.toString());


        info = (TextView)findViewById(R.id.info);
        pickerDate = (DatePicker)findViewById(R.id.pickerdate);
        pickerTime = (TimePicker)findViewById(R.id.pickertime);

        Calendar now = Calendar.getInstance();

        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);
//i will not use this stuff ena seate lay plus one kalareku betoast invalid date yelegnal
        pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY) + 1 );
        pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));

        buttonSetAlarm = (Button)findViewById(R.id.setalarm);
        buttonSetAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {




                Calendar current = Calendar.getInstance();
                Calendar cal = Calendar.getInstance();
                cal.set(pickerDate.getYear(),
                        pickerDate.getMonth(),
                        pickerDate.getDayOfMonth(),
                        pickerTime.getCurrentHour(),
                        pickerTime.getCurrentMinute(),
                        00);
                if(cal.compareTo(current) <= 0){
                    //The set Date/Time already passed
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_SHORT).show();
                }

               else {
                    createdata();
                   // setAlarm(cal);
                    setalarmfromnotify();



                }

            }});
    }




    private void setAlarm(Calendar targetCal){

        info.setText("\n\n***\n"
                + "Alarm is set@ " + targetCal.getTime() + "\n"
                + "***\n");




        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }

    private void setRepeativeAlarm(Calendar targetCal){

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm Set", Toast.LENGTH_SHORT).show();

    }

private void setalarmfromnotify(){
// Get the date from our datepicker
    int day = pickerDate.getDayOfMonth();
    int month = pickerDate.getMonth();
    int year = pickerDate.getYear();
    // Create a new calendar set to the date chosen
    // we set the time to midnight (i.e. the first minute of that day)
    Calendar c = Calendar.getInstance();
    c.set(year, month, day);
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service

    scheduleClient.setAlarmForNotification(c);
    // Notify the user what they just did
    Toast.makeText(this, "Notification set for: "+ day +"/"+ (month+1) +"/"+ year, Toast.LENGTH_SHORT).show();
}

    private void createdata() {

        schedule.setCheatsid(Integer.parseInt(gotbread));
        schedule.setLable(desc.getText().toString());
        schedule.setYear(String.valueOf(pickerDate.getYear()));
        schedule.setMonth(String.valueOf(pickerDate.getMonth()));
        schedule.setDay(String.valueOf(pickerDate.getDayOfMonth()));
        schedule.setHour(String.valueOf(pickerTime.getCurrentHour()));
        schedule.setMinute(String.valueOf(pickerTime.getCurrentMinute()));
        schedule =  usersDataSource.createschedule(schedule);


    }

}


