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
import android.widget.Spinner;
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

    String spinnerSelected = "Yes";

    //Adaptor created for list of user jobs that add info to list
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


            return rowView;
        }
    }

    //Opens ViewUserJobActivity to show job description
    class openLink implements AdapterView.OnItemClickListener {
        openLink() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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

        // Set action bar title to specified string
        ((MainActivity) getActivity()).setActionBarTitle("Job Board");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_board, container, false);

        return view;


    }

    //Adds spinner on creation
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter adaptor = ArrayAdapter.createFromResource(getActivity(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        Spinner spinner1 = (Spinner) view.findViewById(R.id.categorySpinner);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adaptor);
        spinner1.setOnItemSelectedListener(new JobBoardFragment.fillSpinner());

        loadJob();
    }

    //Finds jobs based on job categories
    class fillSpinner implements AdapterView.OnItemSelectedListener {
        fillSpinner() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String userJobResult = null;
            if (i == 0) {
                userJobResult = dbHandler().loadUserJobHandler();
            } else {
                userJobResult = dbHandler().loadUserJobHandlerByCategoryId(i);
            }

            User currentUser = User.getInstance();

            String[] splitedUserJob = userJobResult.split("@@");
            List<String> list = new ArrayList<String>();
            String jobData = "";
            int userJobNumber = 0;
            Jobs job;

            while (userJobNumber < splitedUserJob.length && splitedUserJob.length > 1) {
                if (currentUser.getID() == Integer.parseInt(splitedUserJob[userJobNumber + 1])) {
                    job = dbHandler().findJobById(Integer.parseInt(splitedUserJob[userJobNumber + 2]));
                    if (job == null) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error, Job not found", Toast.LENGTH_LONG).show();
                    } else {
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

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    //Loads job into listview
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

        while (userJobNumber < splitedUserJob.length && splitedUserJob.length > 1) {
            if (currentUser.getID() == Integer.parseInt(splitedUserJob[userJobNumber + 1])) {
                job = dbHandler().findJobById(Integer.parseInt(splitedUserJob[userJobNumber + 2]));
                if (job == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error, Job not found", Toast.LENGTH_LONG).show();
                } else {
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

    //Allows fragment to access database
    private MyDBHandler dbHandler() {
        return new MyDBHandler(getActivity().getApplicationContext(), null, null, 1);
    }


}
