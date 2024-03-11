package algonquin.cst2335.agga0042;

public class ChatMessage {

    String message;
    String timeSent;
    boolean isSend;

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
