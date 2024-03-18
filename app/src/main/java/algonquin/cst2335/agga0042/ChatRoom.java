package algonquin.cst2335.agga0042;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, getString(R.string.databaseName)).build();
        ChatMessageDAO mDAO = db.cmDAO();

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {if(messages == null)
        {
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor threads = Executors.newSingleThreadExecutor();
            threads.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recyclerView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }});

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<>());
        }

        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.simpleDateFormat));
            String currentDateAndTime = sdf.format(new Date());
            chatMessage = new ChatMessage(binding.textInput.getText().toString(), currentDateAndTime, true);
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });

        binding.recieveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.simpleDateFormat));
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

    public class MyRowHolder extends RecyclerView.ViewHolder {

        TextView msgText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(clk ->{

                int position = getAbsoluteAdapterPosition();



                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this);
                builder.setMessage(getString(R.string.Alert) + msgText.getText())
                        .setTitle(getString(R.string.ques))
                        .setNegativeButton(getString(R.string.no), (dialog, cl) -> {})
                        .setPositiveButton(getString(R.string.yes), (dialog, cl) -> {

                            ChatMessage removedMessage = messages.get(position);
                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);

                            Snackbar.make(msgText, getString(R.string.snackerbar)+ (position + 1), Snackbar.LENGTH_LONG)
                                    .setAction(getString(R.string.undo), click -> {
                                        messages.add(position, removedMessage);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        })
                        .create().show();
            });
            msgText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }

    }
}