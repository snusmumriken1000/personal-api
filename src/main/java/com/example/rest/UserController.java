package com.example.rest;

import com.example.entity.User;
import com.example.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User Management", description = "User CRUD operations")
public class UserController {
    
    @Inject
    UserRepository userRepository;
    
    @GET
    @Operation(summary = "Get all users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Get user by ID")
    public Response getUserById(@PathParam("id") Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }
    
    @POST
    @Operation(summary = "Create new user")
    public Response createUser(User user) {
        User created = userRepository.create(user);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
    
    @PUT
    @Path("/{id}")
    @Operation(summary = "Update existing user")
    public Response updateUser(@PathParam("id") Long id, User user) {
        user.setId(id);
        User updated = userRepository.update(user);
        return Response.ok(updated).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete user")
    public Response deleteUser(@PathParam("id") Long id) {
        userRepository.delete(id);
        return Response.noContent().build();
    }
} 