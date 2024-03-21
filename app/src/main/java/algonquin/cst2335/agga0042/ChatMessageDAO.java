package algonquin.cst2335.agga0042;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMessageDAO {

    @Insert
    public long insertMessage(ChatMessage m);

    @Query("Select * from ChatMessage")
    public List<ChatMessage> getAllMessages();

    @Delete
    int deleteMessage(ChatMessage m);

    @Query("Delete from ChatMessage")
    public void deleteAllMessages();
}