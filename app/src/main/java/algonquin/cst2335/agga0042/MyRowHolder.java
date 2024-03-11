package algonquin.cst2335.agga0042;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRowHolder extends RecyclerView.ViewHolder {

    TextView msgText;
    TextView timeText;

    public MyRowHolder(@NonNull View itemView){
        super(itemView);
        msgText = itemView.findViewById(R.id.message);
        timeText = itemView.findViewById(R.id.time);
    }

}
