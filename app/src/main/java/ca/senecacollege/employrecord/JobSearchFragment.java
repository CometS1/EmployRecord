package ca.senecacollege.employrecord;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


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

    class JobAsyncTask extends AsyncTask<String, Void, List<String>> {

        /* renamed from: com.example.noppa_000.myapplication.MainActivity$QuakeAsyncTask$1 */
        class openLink implements AdapterView.OnItemClickListener {
            openLink() {
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*TextView currentLng = (TextView) view.findViewById(R.id.textViewLng);
                String stringLat = ((TextView) view.findViewById(R.id.textViewLat)).getText().toString();
                String stringLng = currentLng.getText().toString();
                StringBuilder URLstring = new StringBuilder();
                URLstring.append("https://www.openstreetmap.org/?mlat=");
                URLstring.append(stringLat);
                URLstring.append("&mlon=");
                URLstring.append(stringLng);
                URLstring.append("#map=5/");
                URLstring.append(stringLat);
                URLstring.append("/");
                URLstring.append(stringLng);*/
                //MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(URLstring.toString())));
                //startActivity(new Intent("android.intent.action.VIEW", Uri.parse(URLstring.toString())));
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=bn21x4CtHcg".toString())));
            }
        }

        JobAsyncTask() {
        }

        protected List<String> doInBackground(String... stringurl) {
            returnArray = Utils.fetchJobData(stringurl[0]);
            return returnArray;
            //return Utils.fetchEarthquakeData(stringurl[0]);
        }

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

    class fillSpinner implements AdapterView.OnItemSelectedListener {
        fillSpinner() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 0) {
                JobSearchFragment.this.spinnerSelected = "Yes";
                return;
            }
            JobSearchFragment.this.spinnerSelected = "No";
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


        //ArrayAdapter adaptor = new ArrayAdapter(getActivity(), 17367057, Arrays.asList(new String[]{"Yes", "No"}));
        ArrayAdapter adaptor = ArrayAdapter.createFromResource(getActivity(),
                R.array.fulltime_array, android.R.layout.simple_spinner_item);
        Spinner spinner1 = (Spinner) view.findViewById(R.id.fullTimeSpinner);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adaptor);
        spinner1.setOnItemSelectedListener(new fillSpinner());

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
                    stringBuilder.append(stringURL);
                    stringBuilder.append(titleValue);
                    stringBuilder.append("&full_time=");
                    stringBuilder.append(fullTime);
                    stringBuilder.append("&location=");
                    stringBuilder.append(locationValue);
                    stringURL = stringBuilder.toString();

                Toast.makeText(getActivity(), fullTime, Toast.LENGTH_SHORT).show();
                    new JobAsyncTask().execute(new String[]{stringURL});
            }
        });

        /*
        view.findViewById(R.id.buttonTmp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You are inside Job Search Fragment", Toast.LENGTH_SHORT).show();
            }
        });
        */

    }

}
