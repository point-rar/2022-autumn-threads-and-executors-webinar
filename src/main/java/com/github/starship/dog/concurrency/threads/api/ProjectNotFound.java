package com.github.starship.dog.concurrency.threads.api;

public class ProjectNotFound extends RuntimeException {
    public ProjectNotFound(String message) {
        super(message);
    }
}
