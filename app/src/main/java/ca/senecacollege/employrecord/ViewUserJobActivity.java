package ca.senecacollege.employrecord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ca.senecacollege.employrecord.DatabaseHelper.Jobs;
import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;

public class ViewUserJobActivity extends AppCompatActivity {

    private MyDBHandler dbHandler() {
        return new MyDBHandler(getApplicationContext(), null, null, 1);
    }

    private void deleteJob(String title) {
        Jobs job = dbHandler().findJobByTitle(title);

        int idToken = job.getJobId();
        dbHandler().deleteJobHandler(idToken);
        dbHandler().deleteUserJobHandler(idToken);

        int timeout = 1500;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                Intent intent = new Intent(ViewUserJobActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, timeout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_job);

        Intent intent = getIntent();

        String jobTitle = intent.getStringExtra("jobTitle");
        Jobs job = dbHandler().findJobByTitle(jobTitle);

        final String titleToken = job.getTitle();
        TextView titleInfo = (TextView) findViewById(R.id.textViewTitle);
        titleInfo.setText(titleToken);

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
        companyURLInfo.setText(companyURLToken);

        Button deleteJobButton = (Button) findViewById(R.id.deleteJob);
        deleteJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteJob(titleToken);
            }
        });
    }
}
