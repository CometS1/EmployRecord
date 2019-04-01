package ca.senecacollege.employrecord;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;
import ca.senecacollege.employrecord.DatabaseHelper.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragmentChangePassword.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ProfileFragmentChangePassword extends Fragment {

    private EditText mOriginalPasswordView;
    private EditText mNewPasswordView;
    private EditText mConfirmPasswordView;


    private OnFragmentInteractionListener mListener;

    public ProfileFragmentChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set action bar title to specified string
        ((MainActivity)getActivity()).setActionBarTitle("Update Password");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragment_change_password, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button updatePassword = (Button)view.findViewById(R.id.ConfirmChangePass);
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean updatePassword;


                updatePassword = updatePassword();
                if (updatePassword) {

                // Create new fragment and transaction
                Fragment newFragment = new ProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.main_screen_area, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

                }
            }
        });

        Button changePasswordFragment = (Button)view.findViewById(R.id.CancelChangePass);
        changePasswordFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create new fragment and transaction
                Fragment newFragment = new ProfileFragment();
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

    private Boolean updatePassword () {
        User currentUser = User.getInstance();

        mOriginalPasswordView = (EditText) getActivity().findViewById(R.id.enterOldPassword);
        mNewPasswordView = (EditText) getActivity().findViewById(R.id.enterNewPassword);
        mConfirmPasswordView = (EditText) getActivity().findViewById(R.id.confirmNewPassword);

        boolean cancel = false;
        boolean valid = true;
        View focusView = null;
        String originPassword = mOriginalPasswordView.getText().toString();
        String newPassword = mNewPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        System.out.println(originPassword);
        if (TextUtils.isEmpty(originPassword)) {
            mOriginalPasswordView.setError(getString(R.string.error_field_required));
            focusView = mOriginalPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(newPassword)) {
            mNewPasswordView.setError(getString(R.string.error_field_required));
            focusView = mNewPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_field_required));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        MyDBHandler dbHandler = new MyDBHandler(this.getActivity(), null, null, 1);
        String originPasswordDb = currentUser.getPassword();

            if (!((originPasswordDb.equals(originPassword)))){
                cancel = true;
                focusView = mOriginalPasswordView;
                mOriginalPasswordView.setError(getString(R.string.incorrect_orig_password));
                valid = false;
            }

            if (!((newPassword).equals(confirmPassword))){
                cancel = true;
                focusView = mNewPasswordView;
                mNewPasswordView.setError(getString(R.string.incorrect_new_password));
                focusView = mConfirmPasswordView;
                mConfirmPasswordView.setError(getString(R.string.incorrect_confirm_password));
                valid = false;
            }

            if (valid){
                currentUser.setPassword(newPassword);
                dbHandler.updateUserHandler(currentUser);

                Toast.makeText(getActivity(), "Password update successful", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
            }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
