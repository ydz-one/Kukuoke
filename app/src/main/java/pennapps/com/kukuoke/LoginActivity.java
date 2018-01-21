package pennapps.com.kukuoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


/*
This is the Main page the user sees when he/she opens the app
(unless he/she did not sign out when last used).
It contains a section for signing in and a button for new users to sign up.
 */
public class LoginActivity extends AppCompatActivity {

    FirebaseStorage mstorage = FirebaseStorage.getInstance();
    StorageReference mstorageRef = mstorage.getReference();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mLoginButton;
    private TextView mSignUpLink;

    private ImageView mLogoPicView;
    private ImageView mLogoTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //connect all buttons, fields, and images to their view in xml
        mLogoPicView = (ImageView) findViewById(R.id.chickenLogo);
        mLogoTitleView = (ImageView) findViewById(R.id.kukuokeLogo);

        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);

        mLoginButton = (Button) findViewById(R.id.loginButton);
        mSignUpLink = (TextView) findViewById(R.id.signUpLink);

        //This gets the chicken image from the database
        mstorageRef.child("/chickenlogo.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(LoginActivity.this).load(uri).fit().centerCrop().into(mLogoPicView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Failed image in Login 1", Toast.LENGTH_LONG).show();
            }
        });

        //This gets the kukuoke title image from the database
        mstorageRef.child("/kukuokelogo.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(LoginActivity.this).load(uri).fit().centerCrop().into(mLogoTitleView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Failed image in Login 2", Toast.LENGTH_LONG).show();
            }
        });

        //login stuff begins here
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                /*
                If the logged in user exists, bring him/her to home page of app
                 */
                if (firebaseAuth.getCurrentUser() != null) {

                    MainActivity.FBU = firebaseAuth.getCurrentUser();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
            }
        };

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

        mSignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    /*
    This method is for finding the user in the database and properly signing in
     */
    private void startSignIn() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();

        } else {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in Error", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(LoginActivity.this, "Sign in success", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
    }

    /*
    For new users, when you click the sign up button
     */
    private void goToSignUp() {

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);

        startActivity(intent);

    }
}
