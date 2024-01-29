package algonquin.cst2335.agga0042;

import androidx.lifecycle.MutableLiveData;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import android.os.Bundle;
import android.widget.*;
import algonquin.cst2335.agga0042.R;
import algonquin.cst2335.agga0042.databinding.ActivityMainBinding;
import algonquin.cst2335.agga0042.data.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(variableBinding.getRoot());

        Button btn = variableBinding.mybutton;
        CheckBox checkBox = variableBinding.checkBox;
        RadioButton radioButton = variableBinding.radioButton;
        Switch Switch = variableBinding.Switch;
        ImageView imageView = variableBinding.Image;

        btn.setOnClickListener(click ->
        {
            model.editString.postValue(variableBinding.myedittext.getText().toString());

        });

        model.getEditBoolean().observe(this, editBoolean-> {
            checkBox.setChecked(editBoolean);
            radioButton.setChecked(editBoolean);
            Switch.setChecked(editBoolean);
        });

        variableBinding.checkBox.setOnCheckedChangeListener((compoundButton, isChecked) ->
                model.setEditBoolean(isChecked));
        variableBinding.radioButton.setOnCheckedChangeListener((compoundButton, isChecked) ->
                model.setEditBoolean(isChecked));
        variableBinding.Switch.setOnCheckedChangeListener((compoundButton, isChecked) ->
                model.setEditBoolean(isChecked));

        model.editString.observe(this, isChecked -> {
            String text = "The value is now: " + isChecked;
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(this, text, duration).show();
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int width = imageView.getWidth();
                int height = imageView.getHeight();
                String msg = "The width = "+width+" and height = "+height;
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(MainActivity.this, msg, duration).show();
            }
        });
    }

}