package com.IsmailUsman_i180516_i180634;

import android.net.Uri;

public class Mssg {

    String sender_email, receiver_email;

    String textMssg;
    Uri imgMssg;
    boolean mssgType; // if true Text else Img

    public Mssg(String sender_email, String receiver_email, String textMssg, Uri imgMssg, boolean mssgType) {
        this.sender_email = sender_email;
        this.receiver_email = receiver_email;
        this.textMssg = textMssg;
        this.imgMssg = imgMssg;
        this.mssgType = mssgType;
    }

    public String getSender_email() {
        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public String getReceiver_email() {
        return receiver_email;
    }

    public void setReceiver_email(String receiver_email) {
        this.receiver_email = receiver_email;
    }

    public String getTextMssg() {
        return textMssg;
    }

    public void setTextMssg(String textMssg) {
        this.textMssg = textMssg;
    }

    public Uri getImgMssg() {
        return imgMssg;
    }

    public void setImgMssg(Uri imgMssg) {
        this.imgMssg = imgMssg;
    }

    public boolean getMssgType() {
        return mssgType;
    }

    public void setMssgType(boolean mssgType) {
        this.mssgType = mssgType;
    }
}
