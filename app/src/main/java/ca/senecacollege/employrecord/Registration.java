package ca.senecacollege.employrecord;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;
import ca.senecacollege.employrecord.DatabaseHelper.User;

public class Registration extends AppCompatActivity {

    private static final String TAG = "Registration";

    EditText fname;
    EditText lname;
    EditText username;
    EditText email;
    EditText pwd;
    EditText pwdConfirm;

    Button btnRegister;
    TextView resultView;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, Registration.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fname = findViewById(R.id.register_fname);
        lname = findViewById(R.id.register_lname);
        username = findViewById(R.id.register_username);
        email = findViewById(R.id.register_email);
        pwd = findViewById(R.id.register_pwd);
        pwdConfirm = findViewById(R.id.register_pwd_confirm);

        btnRegister = findViewById(R.id.btn_register);
        resultView = findViewById(R.id.resultView);
    }

    public void loadUser(View view) {
        Log.i(TAG, "--> Start loadUser");
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        // Display in result view is for testing purposes only -- will need to remove when profile works
        resultView.setText(dbHandler.loadUserHandler());

        fname.setText("");
        lname.setText("");
        username.setText("");
        email.setText("");
        pwd.setText("");
        pwdConfirm.setText("");
    }

    public void addUser(View view) {
        Log.i(TAG, "--> Start addUser");
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String fnameStr = fname.getText().toString();
        String lnameStr = lname.getText().toString();
        String usernameStr = username.getText().toString();
        String emailStr = email.getText().toString();
        String pwdStr = pwdConfirm.getText().toString();

        User user = new User(emailStr, usernameStr, pwdStr, fnameStr, lnameStr);
        Log.i(TAG, "-->New user: " + user);

        dbHandler.addUserHandler(user);
        Toast.makeText(Registration.this, "Registered! Load user!", Toast.LENGTH_LONG).show();
    }


}
