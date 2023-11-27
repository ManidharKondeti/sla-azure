package com.example.azureSLA.service;

import java.util.List;

import com.example.azureSLA.model.Comments;

//import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.azureSLA.model.Tickets;
import com.example.azureSLA.model.Users;

public interface SLAService {

	//void saveUserDetails(OAuth2User principal);

    Tickets createTicket(Tickets createTicket);

    List<Tickets> getTicketDetails();

    List<Tickets> getTicketsByStatusId(int id);

    Comments addComments(Comments comment);

    void updPriority(int ticketId, int priorityId);

    Tickets updStatus(int statusId, Tickets tickets);

    Tickets updAssignTo(int userId, Tickets tickets);

    List<Tickets> getTicketsAssignedTo(int getTicketsAssignedTo);

    List<Tickets> getTicketsCreatedBy(int getTicketsCreatedBy);

    Users createUser(Users user);
    
}
