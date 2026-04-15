package com.example.demo;

import com.example.demo.model.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerIT {

  @LocalServerPort
  int port;

  @Autowired
  TestRestTemplate rest;

  @Test
  void createAndGetTodo() {
    Todo todo = new Todo("learn spring", false);
    ResponseEntity<Todo> created = rest.postForEntity("/api/todos", todo, Todo.class);
    Assertions.assertEquals(201, created.getStatusCodeValue());
    Todo body = created.getBody();
    Assertions.assertNotNull(body);
    ResponseEntity<Todo> fetched = rest.getForEntity("/api/todos/" + body.getId(), Todo.class);
    Assertions.assertEquals(200, fetched.getStatusCodeValue());
    Assertions.assertEquals("learn spring", fetched.getBody().getTitle());
  }
}
