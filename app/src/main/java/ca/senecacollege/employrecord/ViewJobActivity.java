package ca.senecacollege.employrecord;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.StringTokenizer;

import ca.senecacollege.employrecord.DatabaseHelper.Jobs;
import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;
import ca.senecacollege.employrecord.DatabaseHelper.UserJob;

import static android.support.constraint.Constraints.TAG;

public class ViewJobActivity extends AppCompatActivity {

    List<String> returnArray;


    class ChosenJobAsyncTask extends AsyncTask<String, Void, List<String>> {

        ChosenJobAsyncTask() {
        }


        private String removeHtml(String html) {
            html = html.replaceAll("<(.*?)\\>"," ");
            html = html.replaceAll("<(.*?)\\\n"," ");
            html = html.replaceFirst("(.*?)\\>", " ");
            html = html.replaceAll("&nbsp;"," ");
            html = html.replaceAll("&amp;"," ");
            return html;
        }

        protected List<String> doInBackground(String... stringurl) {
            returnArray = Utils.fetchSpecificJobData(stringurl[0]);
            return returnArray;
        }

        public void onPostExecute(List<String> postExecuteResult) {
            if(postExecuteResult == null){
                Toast.makeText(ViewJobActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
            }
            else{
                StringTokenizer tokens = new StringTokenizer( postExecuteResult.get(0), "@@");

                String titleToken = tokens.nextToken();
                TextView textInfo = (TextView) findViewById(R.id.textViewTitle);
                textInfo.setText(titleToken);

                String locationToken = tokens.nextToken();
                TextView locationInfo = (TextView) findViewById(R.id.textViewLocation);
                locationInfo.setText(locationToken);

                String descriptionToken = tokens.nextToken();
                descriptionToken = removeHtml(descriptionToken);
                TextView descriptionInfo = (TextView) findViewById(R.id.textViewDescription);
                descriptionInfo.setText(descriptionToken);

                String fullTimeToken = tokens.nextToken();
                TextView fullTimeInfo = (TextView) findViewById(R.id.textViewFullTime);
                fullTimeInfo.setText(fullTimeToken);

                String creationDateToken = tokens.nextToken();
                TextView creationDateInfo = (TextView) findViewById(R.id.textViewCreationDate);
                creationDateInfo.setText(creationDateToken);

                String companyToken = tokens.nextToken();
                TextView companyInfo = (TextView) findViewById(R.id.textViewCompany);
                companyInfo.setText(companyToken);

                String companyURLToken = tokens.nextToken();
                TextView companyURLInfo = (TextView) findViewById(R.id.textViewCompanyUrl);
                companyURLInfo.setText(companyURLToken);
            }
        }
    }

    private MyDBHandler dbHandler() {
        return new MyDBHandler(getApplicationContext(), null, null, 1);
    }

    private void addJob() {
        Log.e(TAG, "--> Start addJob");

        //TODO: need to link below with job json and automate process

        Jobs jobs = new Jobs();

        TextView titleView = (findViewById(R.id.textViewTitle));
        String title = titleView.getText().toString();
        jobs.setTitle(title);

        TextView descView = (findViewById(R.id.textViewDescription));
        String desc = descView.getText().toString();
        jobs.setDescription(desc);

        TextView orgView = (findViewById(R.id.textViewCompany));
        String organization = orgView.getText().toString();
        jobs.setOrganization(organization);

        TextView orgLocationView = (findViewById(R.id.textViewLocation));
        String org_location = orgLocationView.getText().toString();
        jobs.setOrgLocation(org_location);

        String org_email = null;
        jobs.setOrgEmail(org_email);

        String post_origin ="Github Jobs";
        jobs.setPostOrigin(post_origin);

        TextView postUrlView = (findViewById(R.id.textViewJobURL));
        String post_url = postUrlView.getText().toString();
        jobs.setPostUrl(post_url);

        TextView postDeadlineView = (findViewById(R.id.textViewCreationDate));
        String post_deadline = postDeadlineView.getText().toString();
        jobs.setPostDeadline(post_deadline);

        //Complete dates and notes later
        String applied_date = null;
        jobs.setAppliedDate(applied_date);

        String interview_date= null;
        jobs.setInterviewDate(interview_date);

        String offer_deadline= null;
        jobs.setOfferDeadline(offer_deadline);

        String note= null;
        jobs.setNote(note);

        int org_addr_id = 1;
        jobs.setOrgAddrId(org_addr_id);

        int status_id = 1;
        jobs.setStatusId(status_id);

        UserJob userJob = new UserJob();
        //Figure out how to add job to user job table with user info
        //userJob.addUserJobHandler();


        Log.e(TAG, "-->New user: " + jobs);


        dbHandler().addJobHandler(jobs);
        Toast.makeText(getApplicationContext(), "Job Added Successfully!", Toast.LENGTH_LONG).show();

        //TODO: Redirect to login page

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        Intent intent = getIntent();
        String jobUrl = intent.getStringExtra("jobUrl");
        //If job is a job posting
        if (jobUrl != null) {
            TextView textInfo = (TextView) findViewById(R.id.textViewJobURL);
            textInfo.setText(jobUrl);
            //Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show();
            new ChosenJobAsyncTask().execute(new String[]{jobUrl});

            Button addJobButton = (Button) findViewById(R.id.addJob);
            addJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addJob();
                }
            });
        }
        //If job is a user job
        else{
            /*String jobTitle = intent.getStringExtra("jobTitle");
            Jobs job = dbHandler().findJobByTitle(jobTitle);

            String titleToken = job.getTitle();
            TextView textInfo = (TextView) findViewById(R.id.textViewTitle);
            textInfo.setText(titleToken);

            String locationToken = job.getOrg_location();
            TextView locationInfo = (TextView) findViewById(R.id.textViewLocation);
            locationInfo.setText(locationToken);

            String descriptionToken = job.getDescription();
            //descriptionToken = removeHtml(descriptionToken);
            TextView descriptionInfo = (TextView) findViewById(R.id.textViewDescription);
            descriptionInfo.setText(descriptionToken);

            String creationDateToken = job.getPost_deadline();
            TextView creationDateInfo = (TextView) findViewById(R.id.textViewCreationDate);
            creationDateInfo.setText(creationDateToken);

            String companyToken = job.getOrganization();
            TextView companyInfo = (TextView) findViewById(R.id.textViewCompany);
            companyInfo.setText(companyToken);

            //Change to company URL later
            String companyURLToken = job.getPost_url();
            TextView companyURLInfo = (TextView) findViewById(R.id.textViewCompanyUrl);
            companyURLInfo.setText(companyURLToken);*/
        }
    }
}
