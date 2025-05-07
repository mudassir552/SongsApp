package com.example.demo.Requests;

public class Item {
    private Id id;

    public Id getId() { return id; }
    public void setId(Id id) { this.id = id; }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                '}';
    }
}
