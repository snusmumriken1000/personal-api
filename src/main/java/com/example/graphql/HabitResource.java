package com.example.graphql;

import com.example.entity.Habit;
import com.example.repository.HabitRepository;
import com.example.repository.UserRepository;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

@GraphQLApi
@Path("/habits")
public class HabitResource {
    
    @Inject
    HabitRepository habitRepository;
    
    @Inject
    UserRepository userRepository;
    
    @Query("habits")
    public List<Habit> getUserHabits(@Name("userId") Long userId) {
        return habitRepository.list("userId", userId);
    }
    
    @Query
    public Habit getHabit(@Name("id") Long id) {
        return habitRepository.findById(id);
    }
    
    @Mutation
    public Habit createHabit(@Name("userId") Long userId, @Name("habit") Habit habit) {
        habit.setUser(userRepository.findById(userId));
        habitRepository.persist(habit);
        return habit;
    }
    
    @PUT
    @Path("/{id}")
    public Response updateHabit(@PathParam("id") Long id, Habit habit) {
        Habit existingHabit = habitRepository.findById(id);
        if (existingHabit == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        existingHabit.setTitle(habit.getTitle());
        existingHabit.setUser(userRepository.findById(habit.getUserId()));
        // ... 他のフィールドの更新 ...
        
        habitRepository.persist(existingHabit);
        return Response.ok(existingHabit).build();
    }
    
    @Mutation
    public boolean deleteHabit(@Name("id") Long id) {
        Habit habit = habitRepository.findById(id);
        if (habit == null) {
            return false;
        }
        habitRepository.delete(habit);
        return true;
    }
    
    @GET
    public List<Habit> getHabits(@QueryParam("userId") Long userId) {
        return habitRepository.find("userId", userId).list();
    }
    
    @POST
    public Response createHabit(Habit habit) {
        habitRepository.persist(habit);
        return Response.ok(habit).build();
    }
} 