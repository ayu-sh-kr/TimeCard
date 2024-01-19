package dev.archimedes.services;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import dev.archimedes.entities.Employee;
import dev.archimedes.entities.WorkDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WorkDetailService {

    private final List<Employee> employees;
    private LocalDate CURRENT_DATE = null;
    private String CURRENT_POSITION_ID = null;

    private double DAILY_WORK_HOURS = 0;
    private double TOTAL_WORKING_HOURS = 0;
    private int CONSECUTIVE_DAYS = 0;

    public WorkDetailService() {
        this.employees = loadEmployee();
    }


    public List<WorkDetail> getWorkDetails(){

        List<WorkDetail> workDetails = new ArrayList<>();
        WorkDetail workDetail = null;
        for (Employee employee : employees) {

            if (null == workDetail) {
                workDetail = new WorkDetail();
            }
            
            if (StringUtils.isBlank(employee.getTimeCard())) {
                CURRENT_POSITION_ID = employee.getPositionId();
                continue;
            }

            if (CURRENT_POSITION_ID.equals(employee.getPositionId())) {

                LocalDate EMP_DATE = getDate(employee.getTime());

                if (ChronoUnit.DAYS.between(CURRENT_DATE, EMP_DATE) == 0) {
                    TOTAL_WORKING_HOURS += getHours(employee.getTimeCard());
                    DAILY_WORK_HOURS += getHours(employee.getTimeCard());
                } else { // next days working

                    workDetail.addWorkingHours(CURRENT_DATE, DAILY_WORK_HOURS);

                    if(workDetail.getShiftMax() < DAILY_WORK_HOURS){
                        workDetail.addShiftMax(DAILY_WORK_HOURS);
                    }

                    DAILY_WORK_HOURS = getHours(employee.getTimeCard());
                    TOTAL_WORKING_HOURS += DAILY_WORK_HOURS;

                    if (ChronoUnit.DAYS.between(CURRENT_DATE, EMP_DATE) == 1) {
                        CONSECUTIVE_DAYS += 1;
                        CURRENT_DATE = EMP_DATE;
                    } else {
                        if (workDetail.getConsecutiveWorkingDays() < CONSECUTIVE_DAYS) {
                            workDetail.setConsecutiveWorkingDays(CONSECUTIVE_DAYS);
                        }
                        CURRENT_DATE = EMP_DATE;
                        CONSECUTIVE_DAYS = 0;
                    }
                }
            } else {

                if (CONSECUTIVE_DAYS > workDetail.getConsecutiveWorkingDays()) {
                    workDetail.setConsecutiveWorkingDays(CONSECUTIVE_DAYS);
                }
                if (CURRENT_DATE != null) {

                    if(workDetail.getShiftMax() < DAILY_WORK_HOURS){
                        workDetail.addShiftMax(DAILY_WORK_HOURS);
                    }
                    workDetail.addWorkingHours(CURRENT_DATE, DAILY_WORK_HOURS);
                }

                workDetail.setTotalWorkingHours(TOTAL_WORKING_HOURS);
                workDetails.add(workDetail);
                workDetail = new WorkDetail();
                workDetail.setName(employee.getName());
                workDetail.setEmpId(employee.getPositionId());

                CURRENT_POSITION_ID = employee.getPositionId();
                DAILY_WORK_HOURS = getHours(employee.getTimeCard());
                TOTAL_WORKING_HOURS = DAILY_WORK_HOURS;
                CONSECUTIVE_DAYS = 1;
                CURRENT_DATE = getDate(employee.getTime());
            }
        }
        return workDetails;
    }

    private LocalDate getDate(String date){
        return LocalDate.parse(date.split(" ")[0], DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    private double getHours(String timeCard){
        if(StringUtils.isNotBlank(timeCard)){
            int hours = Integer.parseInt(timeCard.split(":")[0]);
            double mins = Integer.parseInt(timeCard.split(":")[1]);

            // Decimal value of hour and min ex: 3:45 = 3.75
            return hours + Double.parseDouble(String.format("%.2f", mins / 60));
        }
        return 0;
    }

    private List<Employee> loadEmployee(){
        try {
            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                    .settings()
                    .dateTimeFormatter(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))
                    .build();
            File file = new File("src/main/resources/Assignment_Timecard.xlsx");
            return Poiji.fromExcel(file, Employee.class, options);
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public static void main(String[] args) {

        // work for seven or more consecutive days
        System.out.println("Work for seven or more consecutive days");
        WorkDetailService workDetailService = new WorkDetailService();
        workDetailService.getWorkDetails().stream()
                .filter(ftr -> ftr.getConsecutiveWorkingDays() >= 7)
                .forEach(details -> System.out.println(MessageFormat.format("Name: {0}  Id: {1}  Days: {2}", details.getName(), details.getEmpId(), details.getConsecutiveWorkingDays())));

        System.out.println();

        // daily working hours greater than 1 and less than equal to 10
        System.out.println("Daily working hours greater than 1 and less than equal to 10");
        workDetailService.getWorkDetails()
                .forEach(ftr -> {
                    for (Map.Entry<LocalDate, Double> value: ftr.getDailyWorkingHours().entrySet()){
                        if(value.getValue() <= 10 && value.getValue() >= 1){
                            System.out.println(MessageFormat.format("Name: {0}  Id: {1}  Date: {2}  Hours: {3}", ftr.getName(), ftr.getEmpId(),value.getKey(), value.getValue()));
                        }
                    }
                });

        System.out.println();

        // working hours greater than or equal to 14 in a single shift
        System.out.println("Working hours greater than or equal to 14 in a single shift");
        workDetailService.getWorkDetails().forEach(workDetail -> {
            if(workDetail.getShiftMax() >= 14){
                System.out.println(MessageFormat.format("Name: {0}  Id: {1}  Shift Max: {2}", workDetail.getName(), workDetail.getEmpId(), workDetail.getShiftMax()));
            }
        });
    }

}
