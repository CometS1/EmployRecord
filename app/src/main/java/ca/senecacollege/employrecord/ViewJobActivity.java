package ca.senecacollege.employrecord;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.StringTokenizer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        Intent intent = getIntent();
        String jobUrl = intent.getStringExtra("jobUrl");
        //Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show();
        new ChosenJobAsyncTask().execute(new String[]{jobUrl});
    }
}
