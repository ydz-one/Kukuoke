package pennapps.com.kukuoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/*
This class is for the Sign Up page for new users
 */

public class SignUpActivity extends AppCompatActivity {

    private Button mSignUpButton;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mNameField;
    private TextView mGoBackLink;

    //for communicating with the firebase database for all users
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //connect all buttons to their view in xml
        mSignUpButton = (Button) findViewById(R.id.signUpButton);
        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);
        mNameField = (EditText) findViewById(R.id.nameField);
        mGoBackLink = (TextView) findViewById(R.id.goBackText);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSignUp();
            }
        });

        //when you click go back, go back to main activity (login page)
        mGoBackLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //if a user signs up, open the rest of the app to him/her
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    //startActivity(new Intent(SignUpActivity.this, MainActivity.class));

                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    /*
    This method is for signing up and adding user to database
     */
    private void startSignUp() {
        final String email = mEmailField.getText().toString();
        final String password = mPasswordField.getText().toString();
        final String name = mNameField.getText().toString();

        //make sure user enters something
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(SignUpActivity.this, "Fields are too short", Toast.LENGTH_LONG).show();

        } else {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Log.e("debug", "onComplete: Failed=" + task.getException().getMessage());

                        Toast.makeText(SignUpActivity.this, "Sign up Error", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(SignUpActivity.this, "Sign up success", Toast.LENGTH_LONG).show();
                        User user = new User(MainActivity.FBU.getUid(), email, name);

                        mDatabase.child("users").child(MainActivity.FBU.getUid()).setValue(user);
                    }
                }
            });
        }
    }

}
