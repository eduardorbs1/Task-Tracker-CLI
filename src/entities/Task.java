package entities;

import utils.Status;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Task {

    private final int id;

    private String description;

    private Status status;

    private final Instant createdAt;

    private Instant updatedAt;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = Status.TODO;

        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Task(int id, String description, Status status,  Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.description = description;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return this.id;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public String toString() {
        return String.format("%d %s %s createdAt: %s updatedAt: %s",
                this.id, this.description, this.status, LocalDateTime.ofInstant(this.createdAt, ZoneId.systemDefault()),
                LocalDateTime.ofInstant(this.updatedAt, ZoneId.systemDefault()));
    }

    public void update(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public void markDone() {
        this.status = Status.DONE;
        this.updatedAt = Instant.now();
    }

    public void markInProgress() {
        this.status = Status.IN_PROGRESS;
        this.updatedAt = Instant.now();
    }
}
