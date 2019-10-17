package duchess.model;

public class Grade {
    private String task;
    private int marks;
    private int maxMarks;
    private int weightage;

    public Grade(String task, int marks, int maxMarks) {
        this(task, marks, maxMarks, 0);
    }

    public Grade(String task, int marks, int maxMarks, int weightage) {
        this.task = task;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
    }
}
