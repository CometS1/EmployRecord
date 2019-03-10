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

        String fullTimeToken = tokens.nextToken();
        TextView fullTimeInfo = (TextView) rowView.findViewById(R.id.textViewFullTime);
        fullTimeInfo.setText(fullTimeToken);




        /*String titleToken = tokens.nextToken();
        String timeToken = tokens.nextToken();
        String urlToken = tokens.nextToken();
        String latToken = tokens.nextToken();
        String lngToken = tokens.nextToken();
        String magToken = tokens.nextToken();
        TextView textInfo = (TextView) rowView.findViewById(R.id.textViewTitle);
        textInfo.setText(titleToken);
        Double magDouble = Double.valueOf(Double.parseDouble(magToken));
        if (magDouble.doubleValue() >= 7.5d) {
            textInfo.setBackgroundColor(this.context.getResources().getColor(17170454));
        }
        textInfo = (TextView) rowView.findViewById(R.id.textViewDate);
        textInfo.setText(new Date(Long.parseLong(timeToken)).toString());
        if (magDouble.doubleValue() >= 7.5d) {
            textInfo.setBackgroundColor(this.context.getResources().getColor(17170454));
        }
        ((TextView) rowView.findViewById(R.id.textViewURL)).setText(urlToken);
        ((TextView) rowView.findViewById(R.id.textViewLat)).setText(latToken);
        ((TextView) rowView.findViewById(R.id.textViewLng)).setText(lngToken);
        ((TextView) rowView.findViewById(R.id.textViewMag)).setText(magToken);*/
        return rowView;
    }
}
