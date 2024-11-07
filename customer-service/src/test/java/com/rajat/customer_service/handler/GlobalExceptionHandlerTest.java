//package com.rajat.customer_service.handler;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import feign.FeignException;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//@WebMvcTest(controllers = GlobalExceptionHandler.class)
//class GlobalExceptionHandlerTest {
//
//  @Autowired private MockMvc mockMvc;
//
//  @MockBean private FeignException.NotFound feignNotFoundException;
//
//  @Test
//  void handleNotFoundException_ShouldReturnNotFoundStatusAndApiResponse() throws Exception {
//    // Set up FeignException.NotFound mock
//    when(feignNotFoundException.getMessage()).thenReturn("Resource not found");
//
//    mockMvc
//        .perform(get("/non-existing-endpoint").contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isNotFound())
//        .andExpect(jsonPath("$.success").value(false))
//        .andExpect(jsonPath("$.message[0]").value(containsString("Resource not found")));
//  }
//
//  @Test
//  void handleGenericException_ShouldReturnInternalServerErrorAndApiResponse() throws Exception {
//    // Simulate a generic Exception
//    Exception genericException = new Exception("Unexpected error occurred");
//
//    mockMvc
//        .perform(get("/trigger-exception").contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isInternalServerError())
//        .andExpect(jsonPath("$.success").value(false))
//        .andExpect(jsonPath("$.message[0]").value(containsString("Unexpected error occurred")));
//  }
//}
