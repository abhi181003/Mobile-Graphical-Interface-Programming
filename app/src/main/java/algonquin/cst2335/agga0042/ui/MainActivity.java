package algonquin.cst2335.agga0042.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import android.os.Bundle;
import android.widget.*;

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
        ImageButton imagebutton = variableBinding.imagebutton;

        btn.setOnClickListener(click ->
        {
            model.editString.postValue(variableBinding.myedittext.getText().toString());

            String text = "The value is now: " + variableBinding.myedittext.getText().toString();
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(this, text, duration).show();

        });

        model.editString.observe(this,s -> {
            variableBinding.textview.setText("Your edit text has: " + s);
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

        CompoundButton.OnCheckedChangeListener compoundButtonListener = (compoundButton, isChecked) -> {
            model.setEditBoolean(isChecked);
            String toastMsg = "Compound Button has been " + (isChecked ? "checked" : "unchecked");
            Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
        };

        variableBinding.checkBox.setOnCheckedChangeListener(compoundButtonListener);

        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int width = variableBinding.imagebutton.getWidth();
                int height = variableBinding.imagebutton.getHeight();
                String msg = "The width = "+width+" and height = "+height;
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(MainActivity.this, msg, duration).show();
            }
        });
    }

}