package ca.senecacollege.employrecord;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;
import ca.senecacollege.employrecord.DatabaseHelper.User;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private MyDBHandler db;

    private TextView firstName;
    private TextView lastName;
    private TextView username;
    private TextView email;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set action bar title to specified string
        ((MainActivity)getActivity()).setActionBarTitle("Profile");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstName = this.getActivity().findViewById(R.id.firstname);
        lastName = this.getActivity().findViewById(R.id.lastname);
        username = this.getActivity().findViewById(R.id.username);
        email = this.getActivity().findViewById(R.id.email);

        // Use Shared Preferences to retrieve saved username and load data from database
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String savedUsername = sharedpreferences.getString("usernameKey","");

        if (savedUsername != null && !savedUsername.isEmpty()) {
            User user = new User();

            db = new MyDBHandler(this.getActivity(), null, null, 1);
            user = db.loadUserHandler(savedUsername);

            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            username.setText(user.getUsername());
            email.setText(user.getEmail());

        } else {
            Log.e(TAG, "ERROR: There is no username saved in Shared Preferences!");
        }

        Button changePasswordFragment = view.findViewById(R.id.btn_change_pwd);
        changePasswordFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create new fragment and transaction
                Fragment newFragment = new ProfileFragmentChangePassword();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.main_screen_area, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
    }

}
