package org.tbbtalent.server.request.education.level;

import org.tbbtalent.server.model.Status;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateEducationLevelRequest {

    @NotBlank
    private String name;
    @NotNull
    private Status status;
    @Min(value = 0L, message = "The value must be positive")
    private int level;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
