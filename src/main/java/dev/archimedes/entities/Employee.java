package dev.archimedes.entities;

import com.poiji.annotation.ExcelCell;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class Employee {

    @ExcelCell(7)
    private String name;

    @ExcelCell(0)
    private String positionId;

    @ExcelCell(1)
    private String positionStatus;

    @ExcelCell(2)
    private String time;

    @ExcelCell(3)
    private String timeOut;

    @ExcelCell(4)
    private String timeCard;

    @ExcelCell(5)
    private String payCycleStart;

    @ExcelCell(6)
    private String payCycleEnd;

    @ExcelCell(8)
    private long fileNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionStatus() {
        return positionStatus;
    }

    public void setPositionStatus(String positionStatus) {
        this.positionStatus = positionStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
//        this.timeOut = LocalDateTime.parse(timeOut, dateTimeFormatter);
        this.timeOut = timeOut;
    }

    public String getTimeCard() {
        return timeCard;
    }

    public void setTimeCard(String timeCard) {
        this.timeCard = timeCard;
    }

    public String getPayCycleStart() {
        return payCycleStart;
    }

    public void setPayCycleStart(String payCycleStart) {
        this.payCycleStart = payCycleStart;
    }

    public String getPayCycleEnd() {
        return payCycleEnd;
    }

    public void setPayCycleEnd(String payCycleEnd) {
        this.payCycleEnd = payCycleEnd;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", positionId='" + positionId + '\'' +
                ", positionStatus='" + positionStatus + '\'' +
                ", time='" + time + '\'' +
                ", timeOut='" + timeOut + '\'' +
                ", timeCard='" + timeCard + '\'' +
                ", payCycleStart='" + payCycleStart + '\'' +
                ", payCycleEnd='" + payCycleEnd + '\'' +
                ", fileNumber=" + fileNumber +
                '}';
    }
}
