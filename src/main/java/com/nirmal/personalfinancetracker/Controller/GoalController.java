package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.AddGoalDto;
import com.nirmal.personalfinancetracker.dto.response.GoalDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
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
    public ResponseEntity<Response<GoalDto>> addGoal(@RequestBody AddGoalDto addGoalDto){
        Response<GoalDto> response = new Response<>();
        GoalDto goalDto = goalServiceImpl.addGoal(addGoalDto);
        response.successResponse(goalDto,"goalDto added successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/goals")
    public ResponseEntity<Response<List<GoalDto>>> viewGoalList(){
        Response<List<GoalDto>> response = new Response<>();
        List<GoalDto> goalDtos = goalServiceImpl.viewGoalList();
        response.successResponse(goalDtos,"goal list retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/goals/{id}")
    public ResponseEntity<Response<GoalDto>> viewGoalById(@PathVariable int id){
        Response<GoalDto> response = new Response<>();
        GoalDto goalDto = goalServiceImpl.viewGoalById(id);
        if(goalDto!=null){
            response.successResponse(goalDto,"goalDto retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("invalid Id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/goal/{id}")
    public ResponseEntity<Response<GoalDto>> updateGoal(@PathVariable int id, @RequestBody AddGoalDto addGoalDto){
        Response<GoalDto> response = new Response<>();
        GoalDto goalDto = goalServiceImpl.updateGoal(id,addGoalDto);
        if(goalDto!=null){
            response.successResponse(goalDto,"updated successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse("invalid Id");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/goal/{id}")
    public ResponseEntity<Response<String>> deleteGoal(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = goalServiceImpl.deleteGoal(id);
        if(data.equals("success")){
            response.successResponse(data,"deleted successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse("invalid Id");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
