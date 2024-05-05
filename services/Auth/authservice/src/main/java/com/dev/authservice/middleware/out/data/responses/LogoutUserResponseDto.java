package com.dev.authservice.middleware.out.data.responses;

import org.springframework.http.HttpStatus;

import com.dev.authservice.constants.session.SessionType;
import com.dev.authservice.middleware.out.data.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;

/**
 * This class represents a Response DTO (Data Transfer Object) used for
 * responses after a successful user logout.
 * It implements the `ResponseDto` interface (likely defining common methods for
 * response objects).
 */
@AllArgsConstructor
public class LogoutUserResponseDto implements ResponseDto {
     /**
      * An injected `ObjectMapper` instance used for converting objects to JSON.
      * This field is final to prevent accidental reassignment.
      */
     private final ObjectMapper mapper;
     /**
      * A message string to be included in the response, likely indicating successful
      * logout.
      */
     private String message;

     /**
      * The username of the logged-out user.
      */
     private String userName;
     /**
      * The session type of the logged-out session.
      */
     private SessionType sessionType;

     /**
      * Implements the `toJsonData` method from the `ResponseDto` interface (likely).
      * This method converts the object data (message and username) into a JSON
      * string representation.
      *
      * @return A String containing the JSON representation of the response data.
      */
     @Override
     public String toJsonData() {
          ObjectNode json = mapper.createObjectNode();
          json.put("message", message);

          ObjectNode userNode = mapper.createObjectNode();
          userNode.put("name", userName);
          userNode.put("type", sessionType.toString());

          json.set("user", userNode);

          try {
               return mapper.writeValueAsString(json);
          } catch (JsonProcessingException e) {
               return e.getMessage();
          }
     }

     /**
      * Implements the `getCode` method from the `ResponseDto` interface (likely).
      * This method returns the HTTP status code associated with the response
      * (typically OK for successful logout).
      *
      * @return An `HttpStatus` object representing the HTTP status code (OK in this
      *         case).
      */
     @Override
     public HttpStatus getCode() {
          return HttpStatus.OK;
     }

}
