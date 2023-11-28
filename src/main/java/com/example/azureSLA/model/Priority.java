package com.example.azureSLA.model;

import jakarta.persistence.Id;

public class Priority {
    @Id
    private int priorityId;
    private String description;
    
    public int getPriorityId() {
        return priorityId;
    }
    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
