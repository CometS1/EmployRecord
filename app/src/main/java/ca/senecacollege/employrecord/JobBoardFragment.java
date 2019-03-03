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
import android.widget.TextView;
import android.widget.Toast;

import ca.senecacollege.employrecord.DatabaseHelper.Jobs;
import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;
import ca.senecacollege.employrecord.DatabaseHelper.User;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class JobBoardFragment extends Fragment {

    public JobBoardFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_job_board, container, false);

        Button addJobButton = (Button) view.findViewById(R.id.addJob);
        addJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addJob();
                // do something
            }
        });

        Button loadJobButton = (Button) view.findViewById(R.id.loadJob);
        loadJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadJob();
                // do something
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.buttonTmp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You are inside Job Board Fragment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addJob() {
        Log.e(TAG, "--> Start addJob");

        //TODO: need to link below with job json and automate process

        Jobs jobs = new Jobs();

        String title ="systems developer";
        jobs.setTitle(title);

        String description = "what do you do";
        jobs.setDescription(description);

        String organization = "Seneca";
        jobs.setOrganization(organization);

        String org_location = "York";
        jobs.setOrgLocation(org_location);

        String org_email = "senca@seneca.ca";
        jobs.setOrgEmail(org_email);

        String post_origin ="Linkedin";
        jobs.setPostOrigin(post_origin);

        String post_url ="tester.com";
        jobs.setPostUrl(post_url);

        String post_deadline= "2019-01-02";
        jobs.setPostDeadline(post_deadline);

        String applied_date ="2019-01-01";
        jobs.setAppliedDate(applied_date);

        String interview_date="2019-01-04";
        jobs.setInterviewDate(interview_date);

        String offer_deadline="2019-01-10";
        jobs.setOfferDeadline(offer_deadline);

        String note="pass";
        jobs.setNote(note);

        int org_addr_id = 1;
        jobs.setOrgAddrId(org_addr_id);

        int status_id = 1;
        jobs.setStatusId(status_id);

//        String fnameStr = fname.getText().toString();
//        String lnameStr = lname.getText().toString();
//        String usernameStr = username.getText().toString();
//        String emailStr = email.getText().toString();
//        String pwdStr = pwdConfirm.getText().toString();


        Log.e(TAG, "-->New user: " + jobs);


        dbHandler().addJobHandler(jobs);
        Toast.makeText(getActivity().getApplicationContext(), "Job Added Successfully!", Toast.LENGTH_LONG).show();

        //TODO: Redirect to login page

    }

    public void loadJob() {
        Log.i(TAG, "--> Start loadUser");
        String result = (dbHandler().loadJobHandler());
        System.out.println(result);

    }

    private MyDBHandler dbHandler() {
        return new MyDBHandler(getActivity().getApplicationContext(), null, null, 1);
    }

}
