package com.example.azureSLA.repositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.azureSLA.DataSource.dataSourceConfig;
import com.example.azureSLA.model.Comments;
import com.example.azureSLA.model.Priority;
import com.example.azureSLA.model.TicketStatus;
import com.example.azureSLA.model.Tickets;
import com.example.azureSLA.model.Users;
import com.example.azureSLA.repository.SLARepository;

@Repository
public class SLARepositoryImpl implements SLARepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public Tickets createTicket(Tickets ticket) {
        String insertQuery = "INSERT INTO dbo.Tickets (Title, Description, StatusId, PriorityId, CreatedBy, AssignedTo, CreatedAt, DueDate, isEmailSent) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, ticket.getTitle());
            preparedStatement.setString(2, ticket.getDescription());
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, ticket.getPriorityId());
            preparedStatement.setInt(5, ticket.getCreatedby());
            preparedStatement.setInt(6, 2);// AdminId

            Date createdDate = new Date();
            java.sql.Timestamp sqlCreatedDate = convertDate(createdDate);
            preparedStatement.setTimestamp(7, new java.sql.Timestamp(createdDate.getTime()));

            Calendar cal = Calendar.getInstance();
            cal.setTime(createdDate);
            if (ticket.getPriorityId() == 1) {
                cal.add(Calendar.MINUTE, 3);
            }
            if (ticket.getPriorityId() == 2) {
                cal.add(Calendar.MINUTE, 5);
            }
            if (ticket.getPriorityId() == 3) {
                cal.add(Calendar.MINUTE, 10);
            }
            preparedStatement.setTimestamp(8, new java.sql.Timestamp(cal.getTime().getTime()));
            preparedStatement.setBoolean(9, ticket.isEmailSent());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("The ticket has been created successfully ");
            } else {
                System.out.println("There is an issue in creating ticket");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    private java.sql.Timestamp convertDate(Date createdDate) {

        java.sql.Timestamp createdSqlDate = new Timestamp(createdDate.getTime());
        return createdSqlDate;
    }

    @Override
    public List<Tickets> getTicketDetails() {
        List<Tickets> resultList = new ArrayList<>();
        // String getTicketsQuery = "SELECT * FROM dbo.Tickets";
        String getAllTicketsQuery = "SELECT " +
                "    t.TicketId," +
                "    t.Title, " +
                "    t.Description," +
                "    t.StatusId," +
                "    sl.Description AS Status," +
                "    t.PriorityId," +
                "    pl.Description AS Priority," +
                "    t.CreatedBy," +
                "    u.FirstName AS CreatedByFirstName," +
                "    u.LastName AS CreatedByLastName," +
                "    t.AssignedTo," +
                "    u2.FirstName AS AssignedToFirstName," +
                "    u2.LastName AS AssignedToLastName," +
                "    t.CreatedAt," +
                "    t.DueDate," +
                "    t.isEmailSent" +
                "    FROM" +
                "    dbo.Tickets t " +
                " JOIN" +
                "    dbo.StatusLookup sl ON t.StatusId = sl.StatusId" +
                " JOIN " +
                "    dbo.PriorityLookup pl ON t.PriorityId = pl.PriorityId" +
                " JOIN " +
                "    dbo.Users u ON t.CreatedBy = u.UserId" +
                " JOIN " +
                "    dbo.Users u2 ON t.AssignedTo = u2.UserId";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getAllTicketsQuery)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Tickets ticket = new Tickets();
                ticket.setTicketId(result.getInt("ticketId"));
                ticket.setTitle(result.getString("title"));
                ticket.setDescription(result.getString("description"));
                ticket.setStatusId(result.getInt("statusId"));
                ticket.setStatus(result.getString("Status"));
                ticket.setPriorityId(result.getInt("priorityId"));
                ticket.setPriority(result.getString("Priority"));
                ticket.setCreatedby(result.getInt("createdby"));
                ticket.setCreatedByFirstName(result.getString("createdByFirstName"));
                ticket.setCreatedByLastName(result.getString("createdByLastName"));
                ticket.setAssignedTo(result.getInt("assignedTo"));
                ticket.setAssignedToFirstName(result.getString("assignedToFirstName"));
                ticket.setAssignedToLastName(result.getString("assignedToLastName"));
                ticket.setCreatedAt(result.getTimestamp("createdAt"));
                ticket.setDueDate(result.getTimestamp("dueDate"));
                ticket.setEmailSent(result.getBoolean("isEmailSent"));

                List<Comments> comments = getCommentsForTicket(ticket.getTicketId());
                ticket.setComments(comments);

                resultList.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;

    }

    @Override
    public List<Tickets> getTicketsByStatusId(int id) {
        List<Tickets> resultList = new ArrayList<>();
        // String getTicketDetailsById = "SELECT * FROM dbo.Tickets where StatusId = ?";
        String getTicketsByStatusIdQuery = "SELECT " +
                "    t.TicketId," +
                "    t.Title, " +
                "    t.Description," +
                "    t.StatusId," +
                "    sl.Description AS Status," +
                "    t.PriorityId," +
                "    pl.Description AS Priority," +
                "    t.CreatedBy," +
                "    u.FirstName AS CreatedByFirstName," +
                "    u.LastName AS CreatedByLastName," +
                "    t.AssignedTo," +
                "    u2.FirstName AS AssignedToFirstName," +
                "    u2.LastName AS AssignedToLastName," +
                "    t.CreatedAt," +
                "    t.DueDate," +
                "    t.isEmailSent" +
                "    FROM" +
                "    dbo.Tickets t " +
                " JOIN" +
                "    dbo.StatusLookup sl ON t.StatusId = sl.StatusId" +
                " JOIN " +
                "    dbo.PriorityLookup pl ON t.PriorityId = pl.PriorityId" +
                " JOIN " +
                "    dbo.Users u ON t.CreatedBy = u.UserId" +
                " JOIN " +
                "    dbo.Users u2 ON t.AssignedTo = u2.UserId" +
                " WHERE t.StatusId= ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getTicketsByStatusIdQuery)) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Tickets ticket = new Tickets();
                ticket.setTicketId(result.getInt("ticketId"));
                ticket.setTitle(result.getString("title"));
                ticket.setDescription(result.getString("description"));
                ticket.setStatusId(result.getInt("statusId"));
                ticket.setStatus(result.getString("Status"));
                ticket.setPriorityId(result.getInt("priorityId"));
                ticket.setPriority(result.getString("Priority"));
                ticket.setCreatedby(result.getInt("createdby"));
                ticket.setCreatedByFirstName(result.getString("createdByFirstName"));
                ticket.setCreatedByLastName(result.getString("createdByLastName"));
                ticket.setAssignedTo(result.getInt("assignedTo"));
                ticket.setAssignedToFirstName(result.getString("assignedToFirstName"));
                ticket.setAssignedToLastName(result.getString("assignedToLastName"));
                ticket.setCreatedAt(result.getTimestamp("createdAt"));
                ticket.setDueDate(result.getTimestamp("dueDate"));
                ticket.setEmailSent(result.getBoolean("isEmailSent"));

                List<Comments> comments = getCommentsForTicket(ticket.getTicketId());
                ticket.setComments(comments);

                resultList.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private List<Comments> getCommentsForTicket(int ticketId) {
        List<Comments> commentsList = new ArrayList<>();
        String getCommentsQuery = "SELECT c.CommentId, c.TicketId, c.CommentText, c.CreatedBy," +
                "u.FirstName AS CreatedByFirstName,u.LastName AS CreatedByLastName," +
                " c.CreatedAt FROM Comments c " +
                "JOIN " +
                " dbo.Users u ON c.CreatedBy = u.UserId " +
                "WHERE " +
                "c.TicketId = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getCommentsQuery)) {
            preparedStatement.setInt(1, ticketId);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Comments comment = new Comments();
                comment.setCommentId(result.getInt("commentId"));
                comment.setTicketId(result.getInt("ticketId"));
                comment.setCreatedBy(result.getInt("createdBy"));
                comment.setCreatedByFirstName(result.getString("createdByFirstName"));
                comment.setCreatedByLastName(result.getString("createdByLastName"));
                comment.setCommentText(result.getString("commentText"));
                comment.setCreatedAt(result.getTimestamp("createdAt"));

                commentsList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentsList;
    }

    @Override
    public Comments addComments(Comments comment) {
        String insertCmnt = "INSERT INTO dbo.Comments (TicketId, CommentText, CreatedBy, CreatedAt) VALUES (?,?,?, CURRENT_TIMESTAMP)";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertCmnt)) {

            preparedStatement.setInt(1, comment.getTicketId());
            preparedStatement.setString(2, comment.getCommentText());
            preparedStatement.setInt(3, comment.getCreatedBy());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("The comment has been added successfully ");
            } else {
                System.out.println("There is an issue in adding comment");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comment;
    }

    @Override
    public void updPriority(int ticketId, int priorityId) {

        String updPriorityQuery = "UPDATE dbo.Tickets SET PriorityId = ? , CreatedAt = ? , DueDate = ? , isEmailSent =? where TicketId = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updPriorityQuery)) {

            preparedStatement.setInt(1, priorityId);

            Date createdDate = new Date();
            java.sql.Timestamp sqlCreatedDate = convertDate(createdDate);
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(createdDate.getTime()));

            Calendar cal = Calendar.getInstance();
            cal.setTime(createdDate);
            if (priorityId == 1) {
                cal.add(Calendar.MINUTE, 3);
            }
            if (priorityId == 2) {
                cal.add(Calendar.MINUTE, 5);
            }
            if (priorityId == 3) {
                cal.add(Calendar.MINUTE, 10);
            }
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(cal.getTime().getTime()));
            preparedStatement.setBoolean(4, false);

            preparedStatement.setInt(5, ticketId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {

                System.out.println("The ticket priority has been updated successfully ");
            } else {
                System.out.println("There is an issue in updating ticket priority");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return ticket;
    }

    @Override
    public void updStatus(int ticketId, int statusId) {
        String updStatusQuery = "UPDATE dbo.Tickets SET StatusId = ? where TicketId = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updStatusQuery)) {

            preparedStatement.setInt(1, statusId);
            preparedStatement.setInt(2, ticketId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("The ticket status has been updated successfully ");
            } else {
                System.out.println("There is an issue in updating ticket status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updAssignTo(int ticketId, int assginUserId) {
        String updStatusQuery = "UPDATE dbo.Tickets SET StatusId= ? , AssignedTo = ?  where TicketId = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updStatusQuery)) {

            preparedStatement.setInt(1, 2);// changing to inprogress
            preparedStatement.setInt(2, assginUserId);
            preparedStatement.setInt(3, ticketId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("The ticket  has been assigned successfully ");
            } else {
                System.out.println("There is an issue in assigning ticket status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Tickets> getTicketsAssignedTo(int assignedTo) {

        List<Tickets> resultList = new ArrayList<>();
        // String getTicketDetailsById = "SELECT * FROM dbo.Tickets where AssignedTo =
        // ?";
        String getTicketsAssignedToQuery = "SELECT " +
                "    t.TicketId," +
                "    t.Title, " +
                "    t.Description," +
                "    t.StatusId," +
                "    sl.Description AS Status," +
                "    t.PriorityId," +
                "    pl.Description AS Priority," +
                "    t.CreatedBy," +
                "    u.FirstName AS CreatedByFirstName," +
                "    u.LastName AS CreatedByLastName," +
                "    t.AssignedTo," +
                "    u2.FirstName AS AssignedToFirstName," +
                "    u2.LastName AS AssignedToLastName," +
                "    t.CreatedAt," +
                "    t.DueDate," +
                "    t.isEmailSent" +
                "    FROM" +
                "    dbo.Tickets t " +
                " JOIN" +
                "    dbo.StatusLookup sl ON t.StatusId = sl.StatusId" +
                " JOIN " +
                "    dbo.PriorityLookup pl ON t.PriorityId = pl.PriorityId" +
                " JOIN " +
                "    dbo.Users u ON t.CreatedBy = u.UserId" +
                " JOIN " +
                "    dbo.Users u2 ON t.AssignedTo = u2.UserId" +
                " WHERE t.AssignedTo= ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getTicketsAssignedToQuery)) {
            preparedStatement.setInt(1, assignedTo);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Tickets ticket = new Tickets();
                ticket.setTicketId(result.getInt("ticketId"));
                ticket.setTitle(result.getString("title"));
                ticket.setDescription(result.getString("description"));
                ticket.setStatusId(result.getInt("statusId"));
                ticket.setStatus(result.getString("Status"));
                ticket.setPriorityId(result.getInt("priorityId"));
                ticket.setPriority(result.getString("Priority"));
                ticket.setCreatedby(result.getInt("createdby"));
                ticket.setCreatedByFirstName(result.getString("createdByFirstName"));
                ticket.setCreatedByLastName(result.getString("createdByLastName"));
                ticket.setAssignedTo(result.getInt("assignedTo"));
                ticket.setAssignedToFirstName(result.getString("assignedToFirstName"));
                ticket.setAssignedToLastName(result.getString("assignedToLastName"));
                ticket.setCreatedAt(result.getTimestamp("createdAt"));
                ticket.setDueDate(result.getTimestamp("dueDate"));
                ticket.setEmailSent(result.getBoolean("isEmailSent"));

                List<Comments> comments = getCommentsForTicket(ticket.getTicketId());
                ticket.setComments(comments);

                resultList.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<Tickets> getTicketsCreatedBy(int getTicketsCreatedBy) {
        List<Tickets> resultList = new ArrayList<>();
        String ticketsCreatedByQuery = "SELECT " +
                "    t.TicketId," +
                "    t.Title, " +
                "    t.Description," +
                "    t.StatusId," +
                "    sl.Description AS Status," +
                "    t.PriorityId," +
                "    pl.Description AS Priority," +
                "    t.CreatedBy," +
                "    u.FirstName AS CreatedByFirstName," +
                "    u.LastName AS CreatedByLastName," +
                "    t.AssignedTo," +
                "    u2.FirstName AS AssignedToFirstName," +
                "    u2.LastName AS AssignedToLastName," +
                "    t.CreatedAt," +
                "    t.DueDate," +
                "    t.isEmailSent" +
                "    FROM" +
                "    dbo.Tickets t " +
                " JOIN" +
                "    dbo.StatusLookup sl ON t.StatusId = sl.StatusId" +
                " JOIN " +
                "    dbo.PriorityLookup pl ON t.PriorityId = pl.PriorityId" +
                " JOIN " +
                "    dbo.Users u ON t.CreatedBy = u.UserId" +
                " JOIN " +
                "    dbo.Users u2 ON t.AssignedTo = u2.UserId" +
                " WHERE t.CreatedBy= ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ticketsCreatedByQuery)) {
            preparedStatement.setInt(1, getTicketsCreatedBy);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Tickets ticket = new Tickets();
                ticket.setTicketId(result.getInt("ticketId"));
                ticket.setTitle(result.getString("title"));
                ticket.setDescription(result.getString("description"));
                ticket.setStatusId(result.getInt("statusId"));
                ticket.setStatus(result.getString("Status"));
                ticket.setPriorityId(result.getInt("priorityId"));
                ticket.setPriority(result.getString("Priority"));
                ticket.setCreatedby(result.getInt("createdby"));
                ticket.setCreatedByFirstName(result.getString("createdByFirstName"));
                ticket.setCreatedByLastName(result.getString("createdByLastName"));
                ticket.setAssignedTo(result.getInt("assignedTo"));
                ticket.setAssignedToFirstName(result.getString("assignedToFirstName"));
                ticket.setAssignedToLastName(result.getString("assignedToLastName"));
                ticket.setCreatedAt(result.getTimestamp("createdAt"));
                ticket.setDueDate(result.getTimestamp("dueDate"));
                ticket.setEmailSent(result.getBoolean("isEmailSent"));

                List<Comments> comments = getCommentsForTicket(ticket.getTicketId());
                ticket.setComments(comments);

                resultList.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Users createUser(Users user) {
        int createdUserId = 0;
        String selectUserByEmailQuery = "SELECT * FROM dbo.Users WHERE Email = ?";
        String createUserQuery = "INSERT INTO dbo.Users (FirstName, LastName, Address, City, Phone, Email, Password, IsAdmin, Fine) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(selectUserByEmailQuery)) {
            selectStatement.setString(1, user.getEmail());
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    user.setUserId(resultSet.getInt("UserId"));
                    user.setFirstName(resultSet.getString("FirstName"));
                    user.setLastName(resultSet.getString("LastName"));
                    user.setAddress(resultSet.getString("Address"));
                    user.setCity(resultSet.getString("City"));
                    user.setPhone(resultSet.getString("Phone"));
                    user.setEmail(resultSet.getString("Email"));
                    user.setPswrd(resultSet.getString("Password"));
                    user.setAdmin(resultSet.getBoolean("IsAdmin"));
                    user.setFine(resultSet.getInt("Fine"));

                    System.out.println("User with email " + user.getEmail() + " already exists.");

                } else {

                    try (PreparedStatement preparedStatement1 = connection.prepareStatement(createUserQuery)) {

                        preparedStatement1.setString(1, user.getFirstName());
                        preparedStatement1.setString(2, user.getLastName());
                        preparedStatement1.setString(3, user.getAddress());
                        preparedStatement1.setString(4, user.getCity());
                        preparedStatement1.setString(5, user.getPhone());
                        preparedStatement1.setString(6, user.getEmail());
                        preparedStatement1.setString(7, user.getPswrd());
                        preparedStatement1.setBoolean(8, false);
                        preparedStatement1.setInt(9, 0);

                        int rowsAffected = preparedStatement1.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("The User has been created successfully ");

                            // Retrieve the newly created user by querying the database
                            String selectUserQuery = "SELECT * FROM dbo.Users WHERE Email = ?";
                            try (PreparedStatement selectStatement2 = connection.prepareStatement(selectUserQuery)) {
                                selectStatement2.setString(1, user.getEmail());
                                try (ResultSet resultSet2 = selectStatement.executeQuery()) {
                                    if (resultSet2.next()) {
                                        createdUserId = resultSet2.getInt("UserId");
                                        user.setUserId(createdUserId);
                                    } else {
                                        throw new RuntimeException("Failed to retrieve the newly created user");
                                    }
                                }
                            }

                            int rowCount = insertUserRole(createdUserId);

                        } else {
                            System.out.println("There is an issue in creating user");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private int insertUserRole(int createdUserId) {
        int rowCount = 0;
        String createUserRoleQuery = "INSERT INTO dbo.UserRole (UserId, RoleId) VALUES (?,?)";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement1 = connection.prepareStatement(createUserRoleQuery)) {

            preparedStatement1.setInt(1, createdUserId);
            preparedStatement1.setInt(2, 2);

            rowCount = preparedStatement1.executeUpdate();
            if (rowCount > 0) {
                System.out.println("The role  has been assigned to user successfully ");
            } else {
                System.out.println("There is an issue in assigning role to user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    @Override
    public List<TicketStatus> getStatus() {
        List<TicketStatus> resultList = new ArrayList<>();
        String getStatusQuery = "SELECT * from dbo.StatusLookup";
        try (Connection connection = DriverManager.getConnection(dataSourceConfig.DB_URL, dataSourceConfig.DB_USERNAME, dataSourceConfig.DB_PWD);
                PreparedStatement preparedStatement = connection.prepareStatement(getStatusQuery)) {

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                TicketStatus status = new TicketStatus();
                status.setStatusId(result.getInt("statusId"));
                status.setDescription(result.getString("description"));

                resultList.add(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<Priority> getPriorities() {
        List<Priority> resultList = new ArrayList<>();
        String getPrioritiesQuery = "SELECT * from dbo.PriorityLookup";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getPrioritiesQuery)) {

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Priority priority = new Priority();
                priority.setPriorityId(result.getInt("priorityId"));
                priority.setDescription(result.getString("description"));

                resultList.add(priority);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Map<String, Integer> getTicketsCount() {
        String query = "SELECT sl.Description AS Status, COUNT(t.TicketId) AS TicketCount " +
                "FROM dbo.Tickets t " +
                "RIGHT JOIN dbo.StatusLookup sl ON t.StatusId = sl.StatusId " +
                "GROUP BY sl.Description";

        Map<String, Integer> ticketCounts = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String status = resultSet.getString("Status");
                int count = resultSet.getInt("TicketCount");
                ticketCounts.put(status, count);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticketCounts;
    }

    @Override
    public List<Users> getUsers() {
        List<Users> usersList = new ArrayList<>();
        String userListQuery = "SELECT * from dbo.Users";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(userListQuery)) {

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Users user = new Users();
                user.setUserId(result.getInt("userId"));
                user.setFirstName(result.getString("firstName"));
                user.setLastName(result.getString("lastName"));
                user.setAddress(result.getString("address"));
                user.setAdmin(result.getBoolean("isAdmin"));
                user.setCity(result.getString("city"));
                user.setEmail(result.getString("email"));
                user.setPhone(result.getString("phone"));

                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;

    }
}
