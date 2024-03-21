package algonquin.cst2335.agga0042;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    ChatMessageDAO mDAO;
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


        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, getString(R.string.databaseName)).build();
            mDAO = db.cmDAO();

            messages.addAll( mDAO.getAllMessages());
            runOnUiThread( () ->
                    binding.recyclerView.setAdapter( myAdapter ));
        });

        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.simpleDateFormat));
            String currentDateAndTime = sdf.format(new Date());
            chatMessage = new ChatMessage(binding.textInput.getText().toString(), currentDateAndTime, true);
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");

            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(() -> {
                chatMessage.id = (int) mDAO.insertMessage(chatMessage);
            });
        });

        binding.recieveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.simpleDateFormat));
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(binding.textInput.getText().toString(), currentDateAndTime, false);
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");

            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(() -> {
                chatMessage.id = (int) mDAO.insertMessage(chatMessage);
            });
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

        setSupportActionBar(binding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_1) {

            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete_title))
                    .setMessage(getString(R.string.delete_confirm_message))
                    .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                        messages.clear();
                        myAdapter.notifyDataSetChanged();
                        Toast.makeText(this, getString(R.string.delete_confirmation), Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show();
            return true;
        } else if (id == R.id.about) {
            Toast.makeText(this, getString(R.string.about), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
                            Executor thread1 = Executors.newSingleThreadExecutor();
                            thread1.execute(() -> {
                                removedMessage.id = (int) mDAO.deleteMessage(removedMessage);
                            });
                            myAdapter.notifyItemRemoved(position);

                            Snackbar.make(msgText, getString(R.string.snackerbar)+ (position + 1), Snackbar.LENGTH_LONG)
                                    .setAction(getString(R.string.undo), click -> {
                                        messages.add(position, removedMessage);
                                        Executor thread2 = Executors.newSingleThreadExecutor();
                                        thread2.execute(() -> {
                                            removedMessage.id = (int) mDAO.insertMessage(removedMessage);
                                        });
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