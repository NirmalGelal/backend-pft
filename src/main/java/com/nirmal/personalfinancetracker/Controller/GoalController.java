package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.AddGoalDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.Goal;
import com.nirmal.personalfinancetracker.service.impl.GoalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoalController {

    @Autowired
    private GoalServiceImpl goalServiceImpl;

    @PostMapping("/goal")
    public ResponseEntity<Response<Goal>> addGoal(@RequestBody AddGoalDto addGoalDto){
        Response<Goal> response = new Response<>();
        Goal goal = goalServiceImpl.addGoal(addGoalDto);
        response.setData(goal);
        response.setMessage("goal added successfully");
        response.setStatus(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/goals")
    public ResponseEntity<Response<List<Goal>>> viewGoalList(){
        Response<List<Goal>> response = new Response<>();
        List<Goal> goals = goalServiceImpl.viewGoalList();
        response.setStatus(true);
        response.setData(goals);
        response.setMessage("goal list retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/goals/{id}")
    public ResponseEntity<Response<Goal>> viewGoalById(@PathVariable int id){
        Response<Goal> response = new Response<>();
        Goal goal = goalServiceImpl.viewGoalById(id);
        if(goal!=null){
            response.setStatus(true);
            response.setData(goal);
            response.setMessage("goal retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("invalid Id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/goal/{id}")
    public ResponseEntity<Response<Goal>> updateGoal(@PathVariable int id, @RequestBody AddGoalDto addGoalDto){
        Response<Goal> response = new Response<>();
        Goal goal = goalServiceImpl.updateGoal(id,addGoalDto);
        if(goal!=null){
            response.setMessage("updated successfully");
            response.setStatus(true);
            response.setData(goal);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setMessage("invalid Id");
        response.setStatus(false);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/goal/{id}")
    public ResponseEntity<Response<String>> deleteGoal(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = goalServiceImpl.deleteGoal(id);
        if(data=="success"){
            response.setData(data);
            response.setStatus(true);
            response.setMessage("deleted successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setMessage("invalid Id");
        response.setStatus(false);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
