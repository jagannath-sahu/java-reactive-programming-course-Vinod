package com.rp.sec11.assignment;

public class SlackMessage {

    private static final String FORMAT = "[%s -> %s] : %s";

    private String sender;
    private String receiver;
    private String message;

    @Override
    public String toString(){
        return String.format(FORMAT, this.sender, this.receiver, this.message);
    }

    public String getSender() {
      return sender;
    }

    public void setSender(String sender) {
      this.sender = sender;
    }

    public String getReceiver() {
      return receiver;
    }

    public void setReceiver(String receiver) {
      this.receiver = receiver;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public static String getFormat() {
      return FORMAT;
    }

}
