package ca.senecacollege.employrecord;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.senecacollege.employrecord.DatabaseHelper.MyDBHandler;
import ca.senecacollege.employrecord.DatabaseHelper.User;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";

    EditText fname;
    EditText lname;
    EditText username;
    EditText email;
    EditText pwd;
    EditText pwdConfirm;

    Button btnRegister;
    Button btnCancel;
    TextView resultView;

    // Returns a new Database Handler
    private MyDBHandler dbHandler() {
        return new MyDBHandler(this, null, null, 1);
    }

    // When the Registration Activity is opened/started, these actions are executed
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
        btnCancel = findViewById(R.id.btnCancel);
        resultView = findViewById(R.id.resultView);

        // When clicking Register button, these actions execute
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateDataEntered()) {
                    addUserAndRedirect();

                    fname.setText("");
                    lname.setText("");
                    username.setText("");
                    email.setText("");
                    pwd.setText("");
                    pwdConfirm.setText("");
                }
            }
        });

        // When clicking Cancel button, these actions execute
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Request to start the Login Activity
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                RegistrationActivity.this.startActivity(intent);

                // Finish the Registration Activity
                finish();
            }
        });

        // Methods to collapse the keyboard when clicking out of a text box (screen area)
        fname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        lname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        pwdConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    // Returns TRUE if there is an email and it's in the correct format, otherwise FALSE
    private boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    // Returns TRUE if input text is blank, otherwise FALSE
    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    // Returns TRUE if password inputs match, otherwise FALSE
    private boolean isPasswordMatch(EditText password, EditText confirmPassword) {
        return password.getText().toString().equals(confirmPassword.getText().toString());
    }

    // Returns TRUE if the password is valid, otherwise FALSE
    private boolean isValidPassword(EditText password) {
        Matcher matcher;
        //Minimum five characters, at least one letter, one number and one special character
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{5,}$";
        matcher = Pattern.compile(PASSWORD_PATTERN).matcher(password.getText().toString());

        return matcher.matches();
    }

    // Validate the data input by the user
    private boolean validateDataEntered() {
        boolean validFname = false;
        boolean validLname = false;
        boolean validUsername = false;
        boolean validUser = false;
        boolean validEmail = false;
        boolean validPwd = false;
        boolean validPwdFormat = false;
        boolean validConfirmPwd = false;
        boolean validPwdMatch = false;

        // Log comment
        Log.e(TAG, "--> Start validateDataEntered");

        // Set an error message if first name is blank
        if (isEmpty(fname)) {
            fname.setError("First name is required");
        } else {
            validFname = true;
        }

        // Set an error message if last name is blank
        if (isEmpty(lname)) {
            lname.setError("Last name is required");
        } else {
            validLname = true;
        }

        // Set an error message if username is blank
        if (isEmpty(username)) {
            username.setError("Username is required");
        } else {
            validUsername = true;

            // Set an error message if username already exists in database
            if (dbHandler().isUser(username.getText().toString())) {
                username.setError("Username already exists!");
            } else {
                validUser = true;
            }
        }

        // Set an error message if email is blank
        if (!isEmail(email) || isEmpty(email)) {
            email.setError("Valid email is required");
        } else {
            validEmail = true;
        }

        // Set an error message if password is blank
        if (isEmpty(pwd)) {
            pwd.setError("Password is required");
        } else {
            validPwd = true;

            // Set an error message if password is invalid
            if (!isValidPassword(pwd)) {
                pwd.setError("Password must have minimum 5 characters, at least 1 letter, 1 number and 1 special character");
            } else {
                validPwdFormat = true;
            }
        }

        // Set an error message if password is blank
        if (isEmpty(pwdConfirm)) {
            pwdConfirm.setError("Confirm password is required");
        } else {
            validConfirmPwd = true;
        }

        // Set an error message if password does not match
        if (!isPasswordMatch(pwd, pwdConfirm)) {
            pwdConfirm.setError("Your passwords must match");
        } else {
            validPwdMatch = true;
        }

        // Returns TRUE if all data is valid
        return (validFname && validLname && validUsername && validUser && validEmail && validPwd && validPwdFormat && validConfirmPwd && validPwdMatch);
    }


    // Add user to database and redirect to login page
    private void addUserAndRedirect() {
        String fnameStr = fname.getText().toString();
        String lnameStr = lname.getText().toString();
        String usernameStr = username.getText().toString();
        String emailStr = email.getText().toString();
        String pwdStr = pwdConfirm.getText().toString();

        // Create new user with all the input data
        User user = new User(emailStr, usernameStr, pwdStr, fnameStr, lnameStr);

        // Add the new user to the database
        dbHandler().addUserHandler(user);

        // Display pop up message after user has been added to database
        Toast.makeText(RegistrationActivity.this, "Registered Successfully!", Toast.LENGTH_LONG).show();

        // Visible for 1.2 seconds
        int timeout = 1200;

        // This makes the current activity visible for the amount of "timeout" before redirecting
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();

                // Redirect to login page
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, timeout);

    }

    // Hids the keyboard
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // TODO: REMOVE THIS TESTER - Loads list of registered users
    public void loadUser(View view) {
        Log.i(TAG, "--> Start loadUser");
        resultView.setText(dbHandler().loadUserHandler());

        fname.setText("");
        lname.setText("");
        username.setText("");
        email.setText("");
        pwd.setText("");
        pwdConfirm.setText("");
    }

    // TODO: REMOVE THIS TESTER - Manually remove user
    public void deleteUser (View view) {
        Log.i(TAG, "--> delete User");
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String userName = "";

        dbHandler.deleteUserHandler(userName);
        Toast.makeText(RegistrationActivity.this, "deleted user!", Toast.LENGTH_LONG).show();
    }

    // TODO: REMOVE THIS TESTER - updates user manually
    public void updateUser (View view) {
        Log.i(TAG, "--> update User");
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        User user = new User();

        user.setUsername("test");
        user.setEmail("tester@gmail.com");
        user.setPassword("tester");
        user.setFirstName("te");
        user.setLastName("st");

        dbHandler.updateUserHandler(user);
        Toast.makeText(RegistrationActivity.this, "update user test!", Toast.LENGTH_LONG).show();
    }

}
