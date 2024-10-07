package com.dslabb1.dslabb1.controller;

import com.dslabb1.dslabb1.model.OrderItem;

import java.sql.Timestamp;
import java.util.List;

public class OrderInfo {
    private int id;
    private Timestamp date;
    private String status;
    private List<OrderItem> items;

    public OrderInfo(int id, Timestamp date, String status, List<OrderItem> items) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
