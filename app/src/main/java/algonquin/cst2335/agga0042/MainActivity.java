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
        ImageView imageView = variableBinding.Image;


    }

}