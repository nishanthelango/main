package duchess.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private String name;
    private String code;
    private List<Grade> grades;
    private double weightageCompleted; // total weightage of completed grades
    private double weightageObtained; // weightage obtained from completed grades
    private double weightageTotal; // total weightage for all the grades

    /**
     * Creates a new Module.
     *
     * @param code the code of the module
     * @param name the mame of the module
     */
    @JsonCreator
    public Module(@JsonProperty("code") String code, @JsonProperty("name") String name) {
        this.code = code;
        this.name = name;
        grades = new ArrayList<>();
    }

    public String toString() {
        return this.code + " " + this.name;
    }

    public boolean isOfCode(String code) {
        return this.code.equalsIgnoreCase(code);
    }

    /**
     * Returns true if two modules share the same code.
     *
     * @param other the other module to compare
     * @return the equality status
     */
    public boolean equals(Object other) {
        if (!(other instanceof Module)) {
            return false;
        }
        Module that = (Module) other;
        return this.code.equalsIgnoreCase(that.code);
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("code")
    public String getCode() {
        return code;
    }

    @JsonSetter("grades")
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    @JsonGetter("grades")
    public List<Grade> getGrades() {
        return grades;
    }

    @JsonSetter("weightageCompleted")
    public void setWeightageCompleted(double weightageCompleted) {
        this.weightageCompleted = weightageCompleted;
    }

    @JsonGetter("weightageCompleted")
    public double getWeightageCompleted() {
        return weightageCompleted;
    }

    @JsonSetter("weightageObtained")
    public void setWeightageObtained(double weightageObtained) {
        this.weightageObtained = weightageObtained;
    }

    @JsonGetter("weightageObtained")
    public double getWeightageObtained() {
        return weightageObtained;
    }

    @JsonSetter("weightageTotal")
    public void setWeightageTotal(double weightageTotal) {
        this.weightageTotal = weightageTotal;
    }

    @JsonGetter("weightageTotal")
    public double getWeightageTotal() {
        return weightageTotal;
    }

    public void deleteGrade(int gradeNo) {
        this.grades.remove(gradeNo);
    }

    public void addGrade(Grade grade) {
        this.grades.add(grade);
        if (grade.getIsComplete()) {
            updateCompleteGradeWeightage(grade.getModulePercentage(), grade.getWeightage());
        } else {
            weightageTotal += grade.getWeightage();
        }
    }

    /**
     * Updates the weightages related to completed grades.
     * Called when a completed grade is added to the list of grades.
     *
     * @param obtainedPercentage marks obtained in the newly added grade as a percentage of the module
     * @param gradeWeightage the weightage of the grade
     */
    private void updateCompleteGradeWeightage(double obtainedPercentage, double gradeWeightage) {
        this.weightageCompleted += gradeWeightage;
        this.weightageTotal += gradeWeightage;
        this.weightageObtained += obtainedPercentage;
    }
}
