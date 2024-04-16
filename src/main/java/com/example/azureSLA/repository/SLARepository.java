package com.example.azureSLA.repository;

import java.util.List;
import java.util.Map;

import com.example.azureSLA.model.Comments;
import com.example.azureSLA.model.Priority;
import com.example.azureSLA.model.TicketStatus;

import com.example.azureSLA.model.Tickets;
import com.example.azureSLA.model.Users;

public interface SLARepository {
    Tickets createTicket(Tickets ticket);

    List<Tickets> getTicketDetails();

    List<Tickets> getTicketsByStatusId(int id);

    Comments addComments(Comments comment);

    void updPriority(int ticketId, int priorityId);

    void updStatus(int ticketId, int statusId);

    void updAssignTo(int userId, int assginUserId);

    List<Tickets> getTicketsAssignedTo(int assignedTo);

    List<Tickets> getTicketsCreatedBy(int getTicketsCreatedBy);

    Users createUser(Users user);

    List<TicketStatus> getStatus();

    List<Priority> getPriorities();

    Map<String, Integer> getTicketsCount();

    List<Users> getUsers();

}
