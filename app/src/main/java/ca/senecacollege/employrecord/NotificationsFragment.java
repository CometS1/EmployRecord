package ca.senecacollege.employrecord;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;
import ca.senecacollege.employrecord.DatabaseHelper.Notification;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {


    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        Button addJobButton = (Button) view.findViewById(R.id.addNotification);
        addJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addNotification();
                // do something
            }
        });

        Button loadJobButton = (Button) view.findViewById(R.id.loadNotification);
        loadJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadNotification();
            }
        });

        Button deleteJobButton = (Button) view.findViewById(R.id.deleteNotification);
        deleteJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteNotification();
            }
        });

        Button updateJobButton = (Button) view.findViewById(R.id.updateNotification);
        updateJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateNotification();
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    private MyDBHandler dbHandler() {
        return new MyDBHandler(getActivity().getApplicationContext(), null, null, 1);
    }

}
