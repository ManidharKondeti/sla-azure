package com.example.azureSLA.model;

import jakarta.persistence.Id;

public class TicketStatus {
    @Id
    private int StatusId;
    private String description;

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
