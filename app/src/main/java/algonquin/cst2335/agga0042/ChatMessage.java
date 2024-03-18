package algonquin.cst2335.agga0042;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @ColumnInfo(name="message")
    protected String message;

    @ColumnInfo(name="timeSent")
    protected String timeSent;

    @ColumnInfo(name="isSend")
    protected boolean isSend;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    public ChatMessage() {
    }

    public ChatMessage(String m, String t, boolean sent)
    {
        message = m;
        timeSent = t;
        isSend = sent;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSend() {
        return isSend;
    }
}
