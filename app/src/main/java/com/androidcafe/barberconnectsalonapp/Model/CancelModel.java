package com.androidcafe.barberconnectsalonapp.Model;

import com.google.firebase.Timestamp;

public class CancelModel {


   String id,booking_json,cancel_reason,comments,salon_id;

   Timestamp timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBooking_json() {
        return booking_json;
    }

    public void setBooking_json(String booking_json) {
        this.booking_json = booking_json;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(String salon_id) {
        this.salon_id = salon_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
