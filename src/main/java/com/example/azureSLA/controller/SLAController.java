package com.example.azureSLA.controller;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.azureSLA.model.Comments;
import com.example.azureSLA.model.Tickets;
import com.example.azureSLA.model.Users;
import com.example.azureSLA.service.SLAService;

@RestController
@RequestMapping("/sla")
public class SLAController {

    @Autowired
    SLAService slaservice;


    @GetMapping("/msg")
    public String message(){
        return "Testing the Azure Deployment";
    }

    // @GetMapping("/user")
    // public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
	// 	Map<String, Object> hm = new HashMap<String, Object>(); 
	// 	hm.put("name", principal.getAttribute("name"));
	// 	hm.put("email", principal.getAttribute("email"));
    //     //saveUserDetails(principal);
    //     return hm;
    // }
  
    // public void saveUserDetails(@AuthenticationPrincipal OAuth2User principal){
    //     slaservice.saveUserDetails(principal);
    // }

    @PostMapping("/user/create")
    public Users createUser(@RequestBody Users user){
        Users createUser = new Users();
        try {
            createUser = slaservice.createUser(user);
        } catch (Exception e) {
            System.err.println("Error in Creating a new Ticket in repository" + e.getMessage());
        }
        return createUser;
    }


    @PostMapping("/ticket/create")
    public Tickets createTicket(@RequestBody Tickets tickets){
        Tickets createTicket = new Tickets();
        try {
            createTicket = slaservice.createTicket(tickets);
        } catch (Exception e) {
            System.err.println("Error in Creating a new Ticket in repository" + e.getMessage());
        }
        return createTicket;
    }

    //getAll
    @GetMapping("/ticket/getAll")
    public List<Tickets> getTicketDetails(){
        List<Tickets> allTickets = new ArrayList<>();
        try {
            allTickets = slaservice.getTicketDetails();
        } catch (Exception e) {
            System.err.println("Error in Fetching Ticket details from repository" + e.getMessage());
        }
        return allTickets;
    }


    @GetMapping("/ticket/get/status/{getTicketsByStatusId}")
    public List<Tickets> getTicketsByStatusId(@PathVariable int getTicketsByStatusId){
        List<Tickets> ticketDetailsById = new ArrayList<>();
        try {
            ticketDetailsById = slaservice.getTicketsByStatusId(getTicketsByStatusId);
        } catch (Exception e) {
            System.err.println("Error in Fetching Ticket details by statusId from repository" + e.getMessage());
        }
        return ticketDetailsById;
    }

     @GetMapping("/ticket/get/assign/{getTicketsAssignedTo}")
    public List<Tickets> getTicketsAssignedTo(@PathVariable int getTicketsAssignedTo){
        List<Tickets> ticketsAssignedTo = new ArrayList<>();
        try {
            ticketsAssignedTo = slaservice.getTicketsAssignedTo(getTicketsAssignedTo);
        } catch (Exception e) {
            System.err.println("Error in Fetching Tickets assigned to user from repository" + e.getMessage());
        }
        return ticketsAssignedTo;
    }

    @GetMapping("/ticket/get/{getTicketsCreatedBy}")
    public List<Tickets> getTicketsCreatedBy(@PathVariable int getTicketsCreatedBy){
        List<Tickets> ticketsCreatedBy = new ArrayList<>();
        try {
            ticketsCreatedBy = slaservice.getTicketsCreatedBy(getTicketsCreatedBy);
        } catch (Exception e) {
            System.err.println("Error in Fetching Tickets created by user from repository" + e.getMessage());
        }
        return ticketsCreatedBy;
    }

    //getTicketsCreatedBy


    @PostMapping("/ticket/addcomment")
    public Comments addComments(@RequestBody Comments comment){
        Comments addComments = new Comments();
        try {
            addComments = slaservice.addComments(comment);
        } catch (Exception e) {
            System.err.println("Error in Creating a new Ticket in repository" + e.getMessage());
        }
        return addComments;
    }


    //only priorityId and ticketId will be the parameters
    @PatchMapping("/ticket/{ticketId}/priority/{priorityId}")
    public void updPriority(@PathVariable int ticketId, @PathVariable int priorityId){
         //Tickets updPriority = new Tickets();
        try {
            //updPriority = slaservice.updPriority(ticketId,priorityId);
            slaservice.updPriority(ticketId,priorityId);
        } catch (Exception e) {
            System.err.println("Error in Creating a new Ticket in repository" + e.getMessage());
        }
        //return updPriority;
    }

    //only statusId and ticketId will be the parameters
    //tickets/changeStatus
    @PatchMapping("/ticket/{ticketId}/status/{statusId}")
    public Tickets updStatus(@PathVariable int statusId, @RequestBody Tickets tickets){
         Tickets updStatus = new Tickets();
        try {
            updStatus = slaservice.updStatus(statusId,tickets);
        } catch (Exception e) {
            System.err.println("Error in Creating a new Ticket in repository" + e.getMessage());
        }
        return updStatus;
    }

    //assginUserId, ticketId
    @PatchMapping("ticket/{ticketId}/updassignTo/{userId}")
    public Tickets updAssignTo(@PathVariable int userId, @RequestBody Tickets tickets){
         Tickets updAssignTo = new Tickets();
        try {
            updAssignTo = slaservice.updAssignTo(userId,tickets);
        } catch (Exception e) {
            System.err.println("Error in Creating a new Ticket in repository" + e.getMessage());
        }
        return updAssignTo;
    }

    //tickets created by
    //getAllstatuses
    //getAllpriori
    //getItsupport roles
    //getTicketbyid
}
