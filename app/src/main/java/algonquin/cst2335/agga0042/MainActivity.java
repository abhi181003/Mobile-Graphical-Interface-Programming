package algonquin.cst2335.agga0042;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class to check the password complexity entered by the user
 * @version 1.1
 * @author Abhishek Aggarwal
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This holds some text at the centre of he screen
     */
    private TextView passText;
    /**
     * This is where the user writes the password to be tested
     */
    private EditText editPass;
    /**
     * this is the button user presses to check his entered password
     */
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passText = findViewById(R.id.textViewPass);
        editPass = findViewById(R.id.editTextPass);
        login = findViewById(R.id.btnLogin);

        String success = getString(R.string.success);
        String fail = getString(R.string.fail);


        login.setOnClickListener( clk -> {
            String pass = editPass.getText().toString();

            if(checkPasswordComplexity(pass)){
                passText.setText(success);
            }
            else{
                passText.setText(fail);
            }
        });
    }

    /**
     *Method to check the complexity of the password entered by the user
     * @param password
     * @return true if complexity up to the standard, false if not
     */
    public boolean checkPasswordComplexity(String password){

        boolean foundUpperCase = false;
        boolean foundLowerCase = false;
        boolean foundNumber = false;
        boolean foundSpecialChar = false;
        String mpt = getString(R.string.Empty);
        String up = getString(R.string.up);
        String low = getString(R.string.low);
        String digit = getString(R.string.digit);
        String specialChar = getString(R.string.specialChar);

        if(password.length() == 0){
            Toast.makeText(this, mpt, Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            for (int i=0;i<password.length();i++){
                char a = password.charAt(i);
                if(Character.isUpperCase(a) ){
                    foundUpperCase = true;
                }
                if(Character.isLowerCase(a)){
                    foundLowerCase = true;
                }
                if(Character.isDigit(a)){
                    foundNumber = true;
                }
                if(isSpecialCharacter(a)){
                    foundSpecialChar = true;
                }
            }
            if(!foundUpperCase) {
                Toast.makeText(this, up, Toast.LENGTH_SHORT).show(); ;// Say that they are missing an upper case letter;
                return false;
            }

            else if( ! foundLowerCase) {
                Toast.makeText(this, low, Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
                return false;
            }

            else if( ! foundNumber) {
                Toast.makeText(this, digit, Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
                return false;
            }

            else if(! foundSpecialChar) {
                Toast.makeText(this, specialChar, Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
                return false;
            }

            else
                return true;
        }
    }

    /**
     *Method to check the password for special case characters
     * @param a
     * @return true if password contains it, false if not
     */
    public boolean isSpecialCharacter(char a){
        switch (a){
            case '#':
            case '$':
            case '%':
            case '&':
            case '*':
            case '!':
            case '?':
            case '@':
            case '^':
                return true;
            default:
                return false;
        }
    }
}