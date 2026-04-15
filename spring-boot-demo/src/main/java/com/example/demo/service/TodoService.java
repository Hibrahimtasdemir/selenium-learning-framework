package com.example.demo.service;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
  private final TodoRepository repository;

  public TodoService(TodoRepository repository) {
    this.repository = repository;
  }

  public List<Todo> findAll() {
    return repository.findAll();
  }

  public Optional<Todo> findById(Long id) {
    return repository.findById(id);
  }

  public Todo save(Todo todo) {
    return repository.save(todo);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}
