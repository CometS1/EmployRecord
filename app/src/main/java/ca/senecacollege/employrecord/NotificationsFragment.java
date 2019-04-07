package ca.senecacollege.employrecord;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import ca.senecacollege.employrecord.DatabaseHelper.JobNotification;
import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;
import ca.senecacollege.employrecord.DatabaseHelper.Notification;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private FloatingActionButton fab;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set action bar title to specified string
        ((MainActivity)getActivity()).setActionBarTitle("Notifications");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = this.getActivity().findViewById(R.id.add_notification);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_screen_area, new NotificationsDetailFragment()).addToBackStack(null).commit();
            }
        });
    }

    private void addNotification() {
        Log.e(TAG, "--> Start add notification");

        Notification notification = new Notification();

        String name ="notify 1";
        notification.setName(name);

        String startDate = "2019-01-01";
        notification.setStartDate(startDate);

        String endDate = "2019-02-02";
        notification.setEndDate(endDate);

        String startTime = "1:00 AM";
        notification.setStartTime(startTime);

        String endTime = "12:00 AM";
        notification.setEndTime(endTime);

        String getAllday = "All Day";
        notification.setAllDay(getAllday);

        String note = "test note";
        notification.setNote(note);

        Integer ColourId = 1;
        notification.setColourId(ColourId);

        Log.e(TAG, "-->New user: " + notification);


        dbHandler().addNotificationHandler(notification);
        Toast.makeText(getActivity().getApplicationContext(), "Notification Added Successfully!", Toast.LENGTH_LONG).show();


    }

    public void loadNotification() {
        Log.i(TAG, "--> Start loadUser");
        String result = (dbHandler().loadNotificationHandler());
        System.out.println(result);

    }

    public void deleteNotification() {
        Log.i(TAG, "--> delete notification");
        String userName = "";

        //TODO: need to update code below so that id delete is fetch from specific job when accessing list
        dbHandler().deleteNotificationHandler(1);
        Toast.makeText(getActivity().getApplicationContext(), "delete user by id 1!", Toast.LENGTH_LONG).show();
    }

    public void updateNotification() {
        Log.i(TAG, "--> update notification");

        Notification notification = new Notification();

        String name ="notify 1";
        notification.setName(name);

        String startDate = "2019-02-01";
        notification.setStartDate(startDate);

        String endDate = "2019-02-04";
        notification.setEndDate(endDate);

        String startTime = "1:00 AM";
        notification.setStartTime(startTime);

        String endTime = "12:00 AM";
        notification.setEndTime(endTime);

        String getAllday = "All Day not";
        notification.setAllDay(getAllday);

        String note = "blow up";
        notification.setNote(note);

        Integer ColourId = 1;
        notification.setColourId(ColourId);

        Log.e(TAG, "-->Update Notification: " + notification);

        // Todo: when we have proper page, when accessing specific job, must fetch out jobid.
        dbHandler().updateNotificationHandler(notification,2);
        Toast.makeText(getActivity().getApplicationContext(), "notification test by id 2", Toast.LENGTH_LONG).show();

    }

    private void addJobNotification() {
        Log.e(TAG, "--> Start add notification");

        JobNotification  jobNotification = new JobNotification();

        int user_id = 1;
        jobNotification.setUserJobId(user_id);

        int notification_id = 1;
        jobNotification.setNotificationId(notification_id);

        Log.e(TAG, "-->New user: " + jobNotification);

        dbHandler().addJobNotificationHandler(jobNotification);
        Toast.makeText(getActivity().getApplicationContext(), "Notification Added Successfully!", Toast.LENGTH_LONG).show();
    }

    public void loadJobNotification() {
        Log.i(TAG, "--> Start load job notification");
        String result = (dbHandler().loadJobNotificationHandler());
        System.out.println(result);

    }

    public void deleteJobNotification() {
        Log.i(TAG, "--> delete notification");
        String userName = "";

        //TODO: need to update code below so that id delete is fetch from specific job when accessing list
        dbHandler().deleteJobNotificationHandler(1);
        Toast.makeText(getActivity().getApplicationContext(), "delete user by id 1!", Toast.LENGTH_LONG).show();
    }

    public void updateJobNotification() {
        Log.i(TAG, "--> update notification");

        JobNotification jobNotification = new JobNotification();

        int user_id = 2;
        jobNotification.setUserJobId(user_id);

        int notification_id = 2;
        jobNotification.setNotificationId(notification_id);



        Log.e(TAG, "-->Update Notification: " + jobNotification);

        // Todo: when we have proper page, when accessing specific job, must fetch out jobid.
        dbHandler().updateJobNotificationHandler(jobNotification,1);
        Toast.makeText(getActivity().getApplicationContext(), "notification test by id 2", Toast.LENGTH_LONG).show();

    }

    private MyDBHandler dbHandler() {
        return new MyDBHandler(getActivity().getApplicationContext(), null, null, 1);
    }

}
