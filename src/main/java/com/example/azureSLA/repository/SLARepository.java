package com.example.azureSLA.repository;

import java.sql.SQLException;
import java.util.List;

import com.example.azureSLA.model.Comments;

//import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.azureSLA.model.Tickets;
import com.example.azureSLA.model.Users;

public interface SLARepository {

    //void saveUserDetails(OAuth2User principal) throws SQLException;

    Tickets createTicket(Tickets ticket);

    List<Tickets> getTicketDetails();

    List<Tickets> getTicketsByStatusId(int id);

    Comments addComments(Comments comment);

    void updPriority(int ticketId, int priorityId);

    Tickets updStatus(int statusId, Tickets ticket);

    Tickets updAssignTo(int userId, Tickets ticket);

    List<Tickets> getTicketsAssignedTo(int assignedTo);

    List<Tickets> getTicketsCreatedBy(int getTicketsCreatedBy);

    Users createUser(Users user);
    
}
