package ca.senecacollege.employrecord;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ca.senecacollege.employrecord.DatabaseHelper.Jobs;
import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;
import ca.senecacollege.employrecord.DatabaseHelper.User;
import ca.senecacollege.employrecord.DatabaseHelper.UserJob;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class JobBoardFragment extends Fragment {

    public JobBoardFragment() {
        // Required empty public constructor

    }

    class JobBoardAdapter extends ArrayAdapter<String> {
        Activity context;
        List<String> itemname1;

        public JobBoardAdapter(Activity activity, List<String> itemnameA) {
            super(activity, R.layout.job_board_layout, itemnameA);
            this.context = activity;
            this.itemname1 = itemnameA;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = this.context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.job_board_layout, null, true);
            StringTokenizer tokens = new StringTokenizer((String) this.itemname1.get(position), "@@");

            String titleToken = "Title: " + tokens.nextToken();
            TextView textInfo = (TextView) rowView.findViewById(R.id.textViewTitle);
            textInfo.setText(titleToken);

            String locationToken = "Location: " + tokens.nextToken();
            TextView locationInfo = (TextView) rowView.findViewById(R.id.textViewLocation);
            locationInfo.setText(locationToken);

            String companyToken = "Company: " + tokens.nextToken();
            TextView companyInfo = (TextView) rowView.findViewById(R.id.textViewCompany);
            companyInfo.setText(companyToken);

            /*Button deleteJobButton = (Button) rowView.findViewById(R.id.deleteButton);
            deleteJobButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //dbHandler().deleteJobHandler();
                    Toast.makeText(getActivity().getApplicationContext(), "Delete Job", Toast.LENGTH_LONG).show();
                }
            });*/

            return rowView;
        }
    }

    class openLink implements AdapterView.OnItemClickListener {
        openLink() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Add information on click for jobs later

            Toast.makeText(getActivity().getApplicationContext(), "open job description", Toast.LENGTH_LONG).show();

            /*String currentJobURL = ((TextView) view.findViewById(R.id.jobURL)).getText().toString();
            StringBuilder URLstring = new StringBuilder();
            //URLstring.append("https://jobs.github.com/positions/");
            URLstring.append(currentJobURL);
            URLstring.append(".json");
            Intent intent = new Intent(getActivity(), ViewJobActivity.class);
            intent.putExtra("jobUrl", URLstring.toString());
            startActivityForResult(intent, 0);*/
            Intent intent = new Intent(getActivity(), ViewUserJobActivity.class);
            String jobTitle = ((TextView) view.findViewById(R.id.textViewTitle)).getText().toString();
            String tempWord = "Title: ";
            jobTitle = jobTitle.replaceAll(tempWord, "");
            intent.putExtra("jobTitle", jobTitle);
            startActivityForResult(intent, 0);
        }
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
            }
        });

        Button loadJobButton = (Button) view.findViewById(R.id.loadJob);
        loadJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadJob();
            }
        });

        Button deleteJobButton = (Button) view.findViewById(R.id.deleteJob);
        deleteJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteJob();
            }
        });

        Button updateJobButton = (Button) view.findViewById(R.id.updateJob);
        updateJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateJob();
            }
        });

        Button addUserJobButton = (Button) view.findViewById(R.id.addUserJob);
        addUserJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addUserJob();
            }
        });

        Button loadUserJobButton = (Button) view.findViewById(R.id.loadUserJob);
        loadUserJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadUserJob();
            }
        });

        Button deleteUserJobButton = (Button) view.findViewById(R.id.deleteUserJob);
        deleteUserJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteUserJob();
            }
        });

        Button updateUserJobButton = (Button) view.findViewById(R.id.updateUserJob);
        updateUserJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateUserJob();
            }
        });

        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*view.findViewById(R.id.buttonTmp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You are inside Job Board Fragment", Toast.LENGTH_SHORT).show();
            }
        });*/
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

        Log.e(TAG, "-->New user: " + jobs);
        Toast.makeText(getActivity().getApplicationContext(), "Job Added Successfully!", Toast.LENGTH_LONG).show();

        //TODO: Redirect to login page

    }

    public void loadJob() {
        Log.i(TAG, "--> Start loadUser");

        //Get current User data
        User currentUser = User.getInstance();

        String userJobResult = dbHandler().loadUserJobHandler();
        String[] splitedUserJob = userJobResult.split("@@");

        List<String> list = new ArrayList<String>();
        String jobData = "";
        int userJobNumber = 0;
        Jobs job;

        while(userJobNumber < splitedUserJob.length) {
            if (currentUser.getID() == Integer.parseInt(splitedUserJob[userJobNumber + 1])) {
               job = dbHandler().findJobById(Integer.parseInt(splitedUserJob[userJobNumber + 2]));
               if (job == null){
                   Toast.makeText(getActivity().getApplicationContext(), "Error, Job not found", Toast.LENGTH_LONG).show();
               }
               else {
                   jobData = job.getTitle() + "@@" + job.getOrg_location() + "@@" + job.getOrganization();
                   list.add(jobData);
               }
            }
            userJobNumber += 3;
        }

        JobBoardAdapter arrayAdapter = new JobBoardAdapter(getActivity(), list);

        ListView linearLayoutListView = (ListView) getActivity().findViewById(R.id.jobResultsList);
        linearLayoutListView.setAdapter(arrayAdapter);
        linearLayoutListView.setOnItemClickListener(new JobBoardFragment.openLink());

    }

    public void deleteJob() {
        Log.i(TAG, "--> delete job");
        String userName = "";

        //TODO: need to update code below so that id delete is fetch from specific job when accessing list
        dbHandler().deleteJobHandler(2);
        Toast.makeText(getActivity().getApplicationContext(), "delete user by id 2!", Toast.LENGTH_LONG).show();
    }

    public void updateJob() {
        Log.i(TAG, "--> update Job");

        Jobs jobs = new Jobs();

        String title ="Systems Architect";
        jobs.setTitle(title);

        String description = "senior?";
        jobs.setDescription(description);

        String organization = "Seneca";
        jobs.setOrganization(organization);

        String org_location = "Microsoft";
        jobs.setOrgLocation(org_location);

        String org_email = "microsoft@seneca.ca";
        jobs.setOrgEmail(org_email);

        String post_origin ="Linkedin";
        jobs.setPostOrigin(post_origin);

        String post_url ="microsoft.com";
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

        // Todo: when we have proper page, when accessing specific job, must fetch out jobid.
        dbHandler().updateJobHandler(jobs,1);
        Toast.makeText(getActivity().getApplicationContext(), "update Job test by id 1", Toast.LENGTH_LONG).show();


    }

    private void addUserJob() {
        Log.e(TAG, "--> Start addJob");

        //TODO: need to link below with job json and automate process

        UserJob userJob = new UserJob();

        int user_id = 1;
        userJob.setUserId(user_id);

        int job_id = 1;
        userJob.set_job_id(job_id);

        Log.e(TAG, "-->New user: " + userJob);

        dbHandler().addUserJobHandler(userJob);
        Toast.makeText(getActivity().getApplicationContext(), "User Job Add Test", Toast.LENGTH_LONG).show();

    }

    public void loadUserJob() {
        Log.i(TAG, "--> Start loadUser");
        String result = (dbHandler().loadUserJobHandler());
        System.out.println(result);

    }

    public void deleteUserJob() {
        Log.i(TAG, "--> delete User job");
        String userName = "";

        //TODO: need to update code below so that id delete is fetch from specific job when accessing list
        dbHandler().deleteUserJobHandler(1);
        Toast.makeText(getActivity().getApplicationContext(), "delete user by id 1!", Toast.LENGTH_LONG).show();
    }

    public void updateUserJob() {
        Log.i(TAG, "--> update User Job");

        UserJob userJob = new UserJob();

        int job_id = 2;
        userJob.set_job_id(job_id);

        int user_id = 2;
        userJob.setUserId(user_id);

        // Todo: when we have proper page, when accessing specific job, must fetch out jobid.
        dbHandler().updateUserJobHandler(userJob);
        Toast.makeText(getActivity().getApplicationContext(), "update Job test by id 1", Toast.LENGTH_LONG).show();


    }

    private MyDBHandler dbHandler() {
        return new MyDBHandler(getActivity().getApplicationContext(), null, null, 1);
    }



}
