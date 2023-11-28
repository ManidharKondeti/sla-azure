package com.example.azureSLA.controller;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.annotation.Get;
import com.example.azureSLA.model.Comments;
import com.example.azureSLA.model.Priority;
import com.example.azureSLA.model.TicketStatus;
import com.example.azureSLA.model.Tickets;
import com.example.azureSLA.model.Users;
import com.example.azureSLA.service.SLAService;

@RestController
@RequestMapping("/sla")
@CrossOrigin(origins = "http://localhost:4200")
public class SLAController {

    @Autowired
    SLAService slaservice;


    @GetMapping("/msg")
    public String message(){
        return "Testing the Azure Deployment";
    }

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

    
    @CrossOrigin(origins = "http://localhost:4200/")
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


    @PatchMapping("/ticket/{ticketId}/priority/{priorityId}")
    public void updPriority(@PathVariable int ticketId, @PathVariable int priorityId){
        try {
            slaservice.updPriority(ticketId,priorityId);
        } catch (Exception e) {
            System.err.println("Error in Creating a new Ticket in repository" + e.getMessage());
        }
    }

    @PatchMapping("/ticket/{ticketId}/status/{statusId}")
    public void updStatus(@PathVariable int ticketId, @PathVariable int statusId){
        try {
            slaservice.updStatus(ticketId,statusId);
        } catch (Exception e) {
            System.err.println("Error in Creating a new Ticket in repository" + e.getMessage());
        }
    }

    @PatchMapping("ticket/{ticketId}/updassignTo/{assginUserId}")
    public void updAssignTo(@PathVariable int ticketId, @PathVariable int assginUserId){
        try {
           slaservice.updAssignTo(ticketId,assginUserId);
        } catch (Exception e) {
            System.err.println("Error in Creating a new Ticket in repository" + e.getMessage());
        }
    }

    @GetMapping("/getTicketsCount")
    public Map<String, Integer> getTicketsCount(){
        Map<String, Integer> ticketsCountMap = new HashMap<>();
        try {
            ticketsCountMap = slaservice.getTicketsCount();
        } catch (Exception e) {
             System.err.println("Error in fetching Tickets count from  repository" + e.getMessage());
        }
        return ticketsCountMap;
    }

    //display the status
    @GetMapping("/getStatus")
    public List<TicketStatus> getStatus(){
        List<TicketStatus> statusList = new ArrayList<>();
        try{
           statusList = slaservice.getStatus();
        }catch (Exception e) {
             System.err.println("Error in fetching Tickets count from  repository" + e.getMessage());
        }
        return statusList;
    }

    @GetMapping("/getPriority")
    public List<Priority> getPriorities(){
        List<Priority> priorityList = new ArrayList<>();
        try{
           priorityList = slaservice.getPriorities();
        }catch (Exception e) {
             System.err.println("Error in fetching Tickets count from  repository" + e.getMessage());
        }
        return priorityList;
    }

    //tickets created by
    //getAllstatuses
    //getAllpriori
    //getItsupport roles
    //getTicketbyid
}
