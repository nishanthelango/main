package duchess.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Represents a grade in a module.
 */
public class Grade {
    private String task;
    private int marks;
    private int maxMarks;
    private int weightage;

    @JsonCreator
    public Grade(@JsonProperty("task") String task, @JsonProperty("marks") int marks,
                 @JsonProperty("maxMarks") int maxMarks, @JsonProperty("weightage") int weightage) {
        this.task = task;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
    }

    @JsonGetter
    public String getTask() {
        return task;
    }

    @JsonSetter
    public void setTask(String task) {
        this.task = task;
    }

    @JsonGetter
    public int getMarks() {
        return marks;
    }

    @JsonSetter
    public void setMarks(int marks) {
        this.marks = marks;
    }

    @JsonGetter
    public int getMaxMarks() {
        return maxMarks;
    }

    @JsonSetter
    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    @JsonGetter
    public int getWeightage() {
        return weightage;
    }

    @JsonSetter
    public void setWeightage(int weightage) {
        this.weightage = weightage;
    }

    @Override
    public String toString() {
        return String.format("%s %d/%d", task, marks, maxMarks);
    }
}
