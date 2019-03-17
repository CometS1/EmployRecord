package ca.senecacollege.employrecord;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/* compiled from: MainActivity */
class CustomListAdapter extends ArrayAdapter<String> {
    Activity context;
    List<String> itemname1;

    public CustomListAdapter(Activity activity, List<String> itemnameA) {
        super(activity, R.layout.joblistlayout, itemnameA);
        this.context = activity;
        this.itemname1 = itemnameA;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.joblistlayout, null, true);
        StringTokenizer tokens = new StringTokenizer((String) this.itemname1.get(position), "@@");

        String jobIdToken = tokens.nextToken();
        TextView idInfo = (TextView) rowView.findViewById(R.id.jobURL);
        idInfo.setText(jobIdToken);

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
