package dev.archimedes.entities;

import org.springframework.boot.jackson.JsonComponent;

import java.time.LocalDate;
import java.util.*;

@JsonComponent
@SuppressWarnings("unused")
public class WorkDetail {
    private String empId;
    private String name;
    private double totalWorkingHours;
    private final Map<LocalDate, Double> dailyWorkingHours = new HashMap<>();
    private int consecutiveWorkingDays;
    private double shiftMax;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(double totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public Map<LocalDate, Double> getDailyWorkingHours() {
        return dailyWorkingHours;
    }

    public int getConsecutiveWorkingDays() {
        return consecutiveWorkingDays;
    }

    public void setConsecutiveWorkingDays(int consecutiveWorkingDays) {
        this.consecutiveWorkingDays = consecutiveWorkingDays;
    }

    public void addWorkingHours(LocalDate date, double hours){
        this.dailyWorkingHours.put(date, dailyWorkingHours.getOrDefault(date, 0.0) + hours);
    }

    public double getShiftMax() {
        return shiftMax;
    }

    public void setShiftMax(double shiftMax) {
        this.shiftMax = shiftMax;
    }

    public void addShiftMax(double hours){
        this.shiftMax = hours;
    }

    @Override
    public String toString() {
        return "WorkDetails{" +
                "empId='" + empId + '\'' +
                ", name='" + name + '\'' +
                ", totalWorkingHours=" + totalWorkingHours +
                ", dailyWorkingHours=" + dailyWorkingHours +
                ", consecutiveWorkingDays=" + consecutiveWorkingDays +
                '}';
    }
}
