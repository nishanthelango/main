package duchess.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Represents a grade in a module.
 */
public class Grade {
    private String assessment;
    private int marks;
    private int maxMarks;
    private int weightage;
    private boolean isComplete;

    /**
     * Creates a Grade object.
     *
     * @param assessment description of assessment
     * @param marks marks obtained
     * @param maxMarks maximum marks obtainable
     * @param weightage weightage of assessment out of 100
     * @param isComplete true if the assessment is complete, false otherwise
     */
    @JsonCreator
    public Grade(@JsonProperty("task") String assessment, @JsonProperty("marks") int marks,
                 @JsonProperty("maxMarks") int maxMarks, @JsonProperty("weightage") int weightage,
                 @JsonProperty("isComplete") boolean isComplete) {
        this.assessment = assessment;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
        this.isComplete = isComplete;
    }

    /**
     * Creates a grade object.
     *
     * @param assessment description of assessment
     * @param weightage weightage of assessment out of 100
     */
    public Grade(String assessment, int weightage) {
        this.assessment = assessment;
        this.weightage = weightage;
        this.isComplete = false;
    }

    /**
     * Creates a grade object.
     *
     * @param assessment description of assessment
     * @param marks marks obtained
     * @param maxMarks maximum marks obtainable
     * @param weightage weightage of assessment out of 100
     */
    public Grade(String assessment, int marks, int maxMarks, int weightage) {
        this(assessment, weightage);
        this.marks = marks;
        this.maxMarks = maxMarks;
    }

    @JsonGetter
    public String getTask() {
        return assessment;
    }

    @JsonSetter
    public void setTask(String assessment) {
        this.assessment = assessment;
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

    @JsonGetter
    public boolean getIsComplete(boolean isComplete) {
        return isComplete;
    }

    @JsonSetter
    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public void markAsComplete() {
        this.isComplete = true;
    }

    @Override
    public String toString() {
        return String.format("%s %d%%", assessment, weightage);
    }
}
