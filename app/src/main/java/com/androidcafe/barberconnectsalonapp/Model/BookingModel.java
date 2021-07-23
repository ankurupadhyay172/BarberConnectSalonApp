package com.androidcafe.barberconnectsalonapp.Model;

import com.google.firebase.Timestamp;

public class BookingModel {

    String id,barber_id,barber_name,city,customer_name,end_time,payment_method,payment_status,salon_address,salon_id,salon_json,services,slot_date,start_time,time;
    boolean done,isPayOnline;
    Timestamp timestamp;
    double total_amount;
    String customer_image;
    String booking_status;
    String user_id;
    String cancel_reason,cancel_comments;

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getCancel_comments() {
        return cancel_comments;
    }

    public void setCancel_comments(String cancel_comments) {
        this.cancel_comments = cancel_comments;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    int slot_no;


    public int getSlot_no() {
        return slot_no;
    }

    public void setSlot_no(int slot_no) {
        this.slot_no = slot_no;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public String getCustomer_image() {
        return customer_image;
    }

    public void setCustomer_image(String customer_image) {
        this.customer_image = customer_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarber_id() {
        return barber_id;
    }

    public void setBarber_id(String barber_id) {
        this.barber_id = barber_id;
    }

    public String getBarber_name() {
        return barber_name;
    }

    public void setBarber_name(String barber_name) {
        this.barber_name = barber_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getSalon_address() {
        return salon_address;
    }

    public void setSalon_address(String salon_address) {
        this.salon_address = salon_address;
    }

    public String getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(String salon_id) {
        this.salon_id = salon_id;
    }

    public String getSalon_json() {
        return salon_json;
    }

    public void setSalon_json(String salon_json) {
        this.salon_json = salon_json;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getSlot_date() {
        return slot_date;
    }

    public void setSlot_date(String slot_date) {
        this.slot_date = slot_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isPayOnline() {
        return isPayOnline;
    }

    public void setPayOnline(boolean payOnline) {
        isPayOnline = payOnline;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}
