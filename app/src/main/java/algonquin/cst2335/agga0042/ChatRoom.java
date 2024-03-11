package algonquin.cst2335.agga0042;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.agga0042.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.agga0042.databinding.RecieveMessageBinding;
import algonquin.cst2335.agga0042.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    ChatRoomViewModel chatModel ;
    ArrayList<ChatMessage> messages = new ArrayList<>();

    ChatMessage chatMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<>());
        }

        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            chatMessage = new ChatMessage(binding.textInput.getText().toString(), currentDateAndTime, true);
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });

        binding.recieveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(binding.textInput.getText().toString(), currentDateAndTime, false);
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });

        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                } else {
                    // Assuming you have a ReceiveMessageBinding class for the received message layout
                    RecieveMessageBinding binding = RecieveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }
            }


            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position){
                ChatMessage chatMessage = messages.get(position);
                String obj = chatMessage.getMessage();
                String time = chatMessage.getTimeSent();
                holder.msgText.setText(obj);
                holder.timeText.setText(time);
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            public int getItemViewType(int position){
                ChatMessage chatMessage = messages.get(position);
                if(chatMessage.isSend()){
                    return 0;
                }
                else {
                    return 1;
                }
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}