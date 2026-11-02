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

    public int getId() {
        return this.id;
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return String.format("%d %s status: %s createdAt: %s updatedAt: ",
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
