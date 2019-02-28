package ca.senecacollege.employrecord;

import android.app.Activity;
import android.content.Context;
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
    TextView resultView;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RegistrationActivity.class);
        return intent;
    }

    private MyDBHandler dbHandler() {
        return new MyDBHandler(this, null, null, 1);
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateDataEntered()) {
                    addUser();

                    fname.setText("");
                    lname.setText("");
                    username.setText("");
                    email.setText("");
                    pwd.setText("");
                    pwdConfirm.setText("");
                }
            }
        });

        // TODO: below is temp fix for collapsing the keyboard, need to find another way
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
        // TODO: above this line is temp fix
    }

    private boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean isPasswordMatch(EditText password, EditText confirmPassword) {
        return password.getText().toString().equals(confirmPassword.getText().toString());
    }

    private boolean isValidPassword(EditText password) {
        Matcher matcher;
        //Minimum five characters, at least one letter, one number and one special character
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{5,}$";
        matcher = Pattern.compile(PASSWORD_PATTERN).matcher(password.getText().toString());

        return matcher.matches();
    }

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

        Log.e(TAG, "--> Start validateDataEntered");

        if (isEmpty(fname)) {
            fname.setError("First name is required");
        } else {
            validFname = true;
        }

        if (isEmpty(lname)) {
            lname.setError("Last name is required");
        } else {
            validLname = true;
        }
        if (isEmpty(username)) {
            username.setError("Username is required");
        } else {
            validUsername = true;
            if (dbHandler().isUser(username.getText().toString())) {
                username.setError("Username already exists!");
            } else {
                validUser = true;
            }
        }

        if (!isEmail(email) || isEmpty(email)) {
            email.setError("Valid email is required");
        } else {
            validEmail = true;
        }

        if (isEmpty(pwd)) {
            pwd.setError("Password is required");
        } else {
            validPwd = true;
            if (!isValidPassword(pwd)) {
                pwd.setError("Password must have minimum 5 characters, at least 1 letter, 1 number and 1 special character");
            } else {
                validPwdFormat = true;
            }
        }

        if (isEmpty(pwdConfirm)) {
            pwdConfirm.setError("Confirm password is required");
        } else {
            validConfirmPwd = true;
        }

        if (!isPasswordMatch(pwd, pwdConfirm)) {
            pwdConfirm.setError("Your passwords must match");
        } else {
            validPwdMatch = true;
        }

        return (validFname && validLname && validUsername && validUser && validEmail && validPwd && validPwdFormat && validConfirmPwd && validPwdMatch);
    }

    private void addUser() {
        Log.e(TAG, "--> Start addUser");

        String fnameStr = fname.getText().toString();
        String lnameStr = lname.getText().toString();
        String usernameStr = username.getText().toString();
        String emailStr = email.getText().toString();
        String pwdStr = pwdConfirm.getText().toString();

        User user = new User(emailStr, usernameStr, pwdStr, fnameStr, lnameStr);
        Log.e(TAG, "-->New user: " + user);

        dbHandler().addUserHandler(user);
        Toast.makeText(RegistrationActivity.this, "Registered Successfully!", Toast.LENGTH_LONG).show();

        //TODO: Redirect to login page

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // TODO: Will need to remove loadUser once testing is done
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

    // TODO: Will need to remove deleteUser once testing is done
    public void deleteUser (View view) {
        Log.i(TAG, "--> delete User");
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String userName = "";

        dbHandler.deleteUserHandler(userName);
        Toast.makeText(RegistrationActivity.this, "delete user test!", Toast.LENGTH_LONG).show();
    }

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
