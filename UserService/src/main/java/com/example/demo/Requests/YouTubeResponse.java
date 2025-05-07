package com.example.demo.Requests;


import java.util.List;

public class YouTubeResponse {
    private List<Item> items;

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }



    @Override
    public String toString() {
        return "YouTubeResponse{" +
                "items=" + items +
                '}';
    }
}
