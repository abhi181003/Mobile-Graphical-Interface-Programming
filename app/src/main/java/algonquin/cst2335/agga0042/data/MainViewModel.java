package algonquin.cst2335.agga0042.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel{

    public MutableLiveData<String> editString = new MutableLiveData<>();
    public MutableLiveData<Boolean> editBoolean = new MutableLiveData<>();

    public MutableLiveData<Boolean> getEditBoolean() {
        return editBoolean;
    }

    public void setEditBoolean(boolean value) {
        editBoolean.postValue(value);
    }
}