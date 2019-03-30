package ca.senecacollege.employrecord;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class JobSearchFragment extends Fragment {

    public JobSearchFragment() {
        // Required empty public constructor
    }

    String stringURL = "https://jobs.github.com/positions.json?description=";
    String spinnerSelected = "Yes";
    List<String> returnArray;
    ListView linearLayoutListView;

    //Uses Utils class to access json data for job and open ViewJobActivity with job data
    class JobAsyncTask extends AsyncTask<String, Void, List<String>> {

        class openLink implements AdapterView.OnItemClickListener {
            openLink() {
            }

            //Runs ViewJobActivity with job data using job URL
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String currentJobURL = ((TextView) view.findViewById(R.id.jobURL)).getText().toString();
                StringBuilder URLstring = new StringBuilder();
                URLstring.append(currentJobURL);
                URLstring.append(".json");
                Intent intent = new Intent(getActivity(), ViewJobActivity.class);
                intent.putExtra("jobUrl", URLstring.toString());
                startActivityForResult(intent, 0);
            }
        }

        JobAsyncTask() {
        }

        //Fetches the job data from search in the background
        protected List<String> doInBackground(String... stringurl) {
            returnArray = Utils.fetchJobData(stringurl[0]);
            return returnArray;
        }

        //After receiving data, puts data in Listview
        public void onPostExecute(List<String> postExecuteResult) {
            if(postExecuteResult == null){
                Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
            }
            else{

                CustomListAdapter arrayAdapter = new CustomListAdapter(getActivity(), postExecuteResult);
                linearLayoutListView = (ListView) getActivity().findViewById(R.id.searchResultsList);
                linearLayoutListView.setAdapter(arrayAdapter);
                linearLayoutListView.setOnItemClickListener(new openLink());
            }
        }
    }

    //Fills fulltime spinner with options
    class fillSpinner implements AdapterView.OnItemSelectedListener {
        fillSpinner() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i <= 1) {
                JobSearchFragment.this.spinnerSelected = "Yes";
            }
            else {
                JobSearchFragment.this.spinnerSelected = "No";
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayAdapter adaptor = ArrayAdapter.createFromResource(getActivity(),
                R.array.fulltime_array, android.R.layout.simple_spinner_item);
        Spinner spinner1 = (Spinner) view.findViewById(R.id.fullTimeSpinner);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adaptor);
        spinner1.setOnItemSelectedListener(new fillSpinner());

        //Add search button click functionality to search on click and fill Listview
        view.findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText title = (getActivity().findViewById(R.id.searchValue));
                Spinner spinner1 = (Spinner) getActivity().findViewById(R.id.fullTimeSpinner);
                EditText location = (getActivity().findViewById(R.id.locationValue));

                String titleValue = title.getText().toString();
                String fullTime = (spinner1.getSelectedItem()).toString();
                String locationValue = location.getText().toString();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.setLength(0);
                    stringBuilder.append(stringURL);
                    stringBuilder.append(titleValue);
                    stringBuilder.append("&full_time=");
                    stringBuilder.append(fullTime);
                    stringBuilder.append("&location=");
                    stringBuilder.append(locationValue);
                    String URL = stringBuilder.toString();

                    new JobAsyncTask().execute(new String[]{URL});
            }
        });


    }

}
