package com.example.azureSLA.service;

import java.util.List;
import java.util.Map;

import com.example.azureSLA.model.Comments;
import com.example.azureSLA.model.Priority;
import com.example.azureSLA.model.TicketStatus;

import com.example.azureSLA.model.Tickets;
import com.example.azureSLA.model.Users;

public interface SLAService {

    Tickets createTicket(Tickets createTicket);

    List<Tickets> getTicketDetails();

    List<Tickets> getTicketsByStatusId(int id);

    Comments addComments(Comments comment);

    void updPriority(int ticketId, int priorityId);

    void updStatus(int ticketId, int statusId);

    void updAssignTo(int ticketId, int assginUserId);

    List<Tickets> getTicketsAssignedTo(int getTicketsAssignedTo);

    List<Tickets> getTicketsCreatedBy(int getTicketsCreatedBy);

    Users createUser(Users user);

    List<TicketStatus> getStatus();

    List<Priority> getPriorities();

    Map<String, Integer> getTicketsCount();

    List<Users> getUsers();

}
