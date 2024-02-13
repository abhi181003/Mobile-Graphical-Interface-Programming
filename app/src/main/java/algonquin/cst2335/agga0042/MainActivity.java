package algonquin.cst2335.agga0042;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import algonquin.cst2335.agga0042.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private ActivityMainBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logBtn = findViewById(R.id.loginButton);
        EditText emailEditText = findViewById(R.id.editEmail);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        emailEditText.setText(emailAddress);

        Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
        logBtn.setOnClickListener(click -> {
            nextPage.putExtra( "EmailAddress", emailEditText.getText().toString() );
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", emailEditText.getText().toString());
            editor.apply();
            startActivity( nextPage);
        });

        Log.w( TAG, "In onCreate() - Loading Widgets" );
        Log.w( TAG, "onCreate() is the first function that gets created when an application is launched." );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w( TAG, "onStart() - The application is now visible on screen." );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( TAG, "onResume() - The application is now responding to user input" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w( TAG, "onPause()- The application no longer responds to user input" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w( TAG, "onStop() - The application is no longer visible." );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( TAG, "onDestroy() - Any memory used by the application is freed" );
    }
}