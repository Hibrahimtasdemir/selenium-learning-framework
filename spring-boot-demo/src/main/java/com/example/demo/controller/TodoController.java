package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
  private final TodoService service;

  public TodoController(TodoService service) {
    this.service = service;
  }

  @GetMapping
  public List<Todo> all() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Todo> get(@PathVariable Long id) {
    return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Todo> create(@RequestBody Todo todo) {
    Todo saved = service.save(todo);
    return ResponseEntity.created(URI.create("/api/todos/" + saved.getId())).body(saved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Todo> update(@PathVariable Long id, @RequestBody Todo todo) {
    return service.findById(id)
        .map(existing -> {
          existing.setTitle(todo.getTitle());
          existing.setCompleted(todo.isCompleted());
          Todo saved = service.save(existing);
          return ResponseEntity.ok(saved);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (service.findById(id).isPresent()) {
      service.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
