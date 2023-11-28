package com.example.azureSLA.serviceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.azureSLA.model.Comments;
import com.example.azureSLA.model.Priority;
import com.example.azureSLA.model.TicketStatus;
import com.example.azureSLA.model.Tickets;
import com.example.azureSLA.model.Users;
import com.example.azureSLA.repository.SLARepository;
import com.example.azureSLA.service.SLAService;

@Service
public class SLAServiceImpl implements SLAService {

    @Autowired
    SLARepository slarepository;

    // @Override
    // public void saveUserDetails(OAuth2User principal) {
    //     try {
    //         slarepository.saveUserDetails(principal);
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

    @Override
    public Tickets createTicket(Tickets ticket) {
        Tickets createTicket = new Tickets();
        if (ticket != null) {
            createTicket = slarepository.createTicket(ticket);
            return createTicket;
        } else {
            return createTicket;
        }
    }

    @Override
    public List<Tickets> getTicketDetails() {
        List<Tickets> getTicketDetails = new ArrayList<>();
        getTicketDetails = slarepository.getTicketDetails();
        return getTicketDetails;
        
    }

    @Override
    public List<Tickets> getTicketsByStatusId(int id) {
        List<Tickets> getTicketsByStatusId = new ArrayList<>();
        getTicketsByStatusId = slarepository.getTicketsByStatusId(id);
        return getTicketsByStatusId;
    }

    @Override
    public Comments addComments(Comments comment) {
        Comments addComments = new Comments();
        if (comment != null) {
            addComments = slarepository.addComments(comment);
            return addComments;
        } else {
            return addComments;
        }
       
    }

    @Override
    public void updPriority(int ticketId,int priorityId) {
        if (ticketId != 0 && priorityId !=0) {
            slarepository.updPriority(ticketId,priorityId);
        }
    }

    @Override
    public void updStatus(int ticketId, int statusId) {
        if (ticketId != 0 && statusId !=0) {
            slarepository.updStatus(ticketId, statusId);
        }
    }

    @Override
    public void updAssignTo(int ticketId, int assginUserId) {
       if (ticketId != 0 && assginUserId !=0) {
           slarepository.updAssignTo(ticketId,assginUserId);
        }
    }

    @Override
    public List<Tickets> getTicketsAssignedTo(int assignedTo) {
        List<Tickets> getTicketsAssignedTo = new ArrayList<>();
        getTicketsAssignedTo = slarepository.getTicketsAssignedTo(assignedTo);
        return getTicketsAssignedTo;
    }

    @Override
    public List<Tickets> getTicketsCreatedBy(int getTicketsCreatedBy) {
        List<Tickets> ticketsCreatedBy = new ArrayList<>();
        ticketsCreatedBy = slarepository.getTicketsCreatedBy(getTicketsCreatedBy);
        return ticketsCreatedBy;
    }

    @Override
    public Users createUser(Users user) {
        Users createUser = new Users();
        if (user != null) {
            createUser = slarepository.createUser(user);
            return createUser;
        } else {
            return createUser;
        }
    }

    @Override
    public List<TicketStatus> getStatus() {
        List<TicketStatus> list= new ArrayList<>();
        list= slarepository.getStatus();
        return list;
    }
    

    @Override
    public List<Priority> getPriorities() {
        List<Priority> list= new ArrayList<>();
        list= slarepository.getPriorities();
        return list;
    }

    @Override
    public Map<String, Integer> getTicketsCount() {
        Map<String, Integer> ticketsCountMap = new HashMap<>();
        ticketsCountMap = slarepository.getTicketsCount();
        return ticketsCountMap;
    }
}
