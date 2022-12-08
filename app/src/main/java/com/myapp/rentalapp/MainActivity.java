package com.myapp.rentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.annotations.Nullable;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 2;
    private BeginSignInRequest signInRequest;
    private SignInClient oneTapClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText edit_name = (EditText) findViewById(R.id.editName);
        final EditText edit_pos = (EditText) findViewById(R.id.editPosition);
        Button btn = (Button) findViewById(R.id.btn_submit);
        DAOEmployee dao = new DAOEmployee();
        btn.setOnClickListener(v -> {
                    Employee employee = new Employee(edit_name.getText().toString(), edit_pos.getText().toString());
                    dao.add(employee).addOnSuccessListener(suc -> {
                        Toast.makeText(this, "Inserted!", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(fai -> {
                        Toast.makeText(this, "Failed :( " + fai.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
        );
        mAuth = FirebaseAuth.getInstance();
        createRequest();
        SignInButton signIn = (SignInButton) findViewById(R.id.signInGoogle);
        signIn.setOnClickListener(v -> {
            signIn();
        });
    }

    public void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail() //shows emails synced on user device
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        /*signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();*/
    }
    public void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(Objects.requireNonNull(account));
            } catch (ApiException e) {
                Log.w("hhm", "Google signin failed", e);
            }
        }
    }
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(getApplicationContext(), VehicleCategorySelection.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Login failed :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            Intent i = new Intent(getApplicationContext(), VehicleCategorySelection.class);
            startActivity(i);
        }
    }
}