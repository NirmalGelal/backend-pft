package com.nirmal.personalfinancetracker.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private boolean status;
    private String message;
    private T data;

    public void successResponse(T data, String message){
        this.status = true;
        this.message = message;
        this.data = data;
    }
    public void failureResponse(String message){
        this.status = false;
        this.message = message;
        this.data = null;
    }

}
