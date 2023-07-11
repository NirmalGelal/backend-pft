package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.AddGoalDto;
import com.nirmal.personalfinancetracker.dto.response.GoalDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.service.impl.GoalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoalController {

    @Autowired
    private GoalServiceImpl goalServiceImpl;

    @PostMapping("/goal")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<GoalDto>> addGoal(@RequestBody AddGoalDto addGoalDto){
        Response<GoalDto> response = new Response<>();
        GoalDto goalDto = goalServiceImpl.addGoal(addGoalDto);
        response.successResponse(goalDto,"goal   added successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/goal")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<List<GoalDto>>> viewGoalList(){
        Response<List<GoalDto>> response = new Response<>();
        List<GoalDto> goalDtos = goalServiceImpl.viewGoalList();
        response.successResponse(goalDtos,"goal list retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/goal/{goalId}")
    public ResponseEntity<Response<GoalDto>> viewGoalById(@PathVariable int goalId){
        Response<GoalDto> response = new Response<>();
        GoalDto goalDto = goalServiceImpl.viewGoalById(goalId);
        if(goalDto!=null){
            response.successResponse(goalDto,"goal retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("user not authorized");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/goal/{goalId}")
    public ResponseEntity<Response<GoalDto>> updateGoal(@PathVariable int goalId, @RequestBody AddGoalDto addGoalDto){
        Response<GoalDto> response = new Response<>();
        GoalDto goalDto = goalServiceImpl.updateGoal(goalId,addGoalDto);
        if(goalDto!=null){
            response.successResponse(goalDto,"updated successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse("user not authorized");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/goal/{goalId}")
    public ResponseEntity<Response<String>> deleteGoal(@PathVariable int goalId){
        Response<String> response = new Response<>();
        String data = goalServiceImpl.deleteGoal(goalId);
        if(data.equals("success")){
            response.successResponse(data,"deleted successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse("user not authorized");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
