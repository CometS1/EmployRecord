package ca.senecacollege.employrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ca.senecacollege.employrecord.DatabaseHelper.User;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User currentUser = User.getInstance();

        String usernameToken = currentUser.getUsername();
        TextView usernameInfo = (TextView) getActivity().findViewById(R.id.username);
        usernameInfo.setText(usernameToken);

        String firstNameToken = currentUser.getFirstName();
        TextView firstNameInfo = (TextView) getActivity().findViewById(R.id.firstname);
        firstNameInfo.setText(firstNameToken);

        String lastNameToken = currentUser.getLastName();
        TextView lastNameInfo = (TextView) getActivity().findViewById(R.id.lastname);
        lastNameInfo.setText(lastNameToken);

        String emailToken = currentUser.getEmail();
        TextView emailInfo = (TextView) getActivity().findViewById(R.id.email);
        emailInfo.setText(emailToken);

        Button changePasswordFragment = (Button)view.findViewById(R.id.btn_change_pwd);
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
