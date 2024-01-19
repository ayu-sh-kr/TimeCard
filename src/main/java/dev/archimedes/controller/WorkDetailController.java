package dev.archimedes.controller;

import dev.archimedes.entities.WorkDetail;
import dev.archimedes.services.WorkDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/details")
public class WorkDetailController {

    private final WorkDetailService workDetailService;

    public WorkDetailController(WorkDetailService workDetailService) {
        this.workDetailService = workDetailService;
    }

    @GetMapping("/get")
    public List<WorkDetail> getWorkDetails(){
        return workDetailService.getWorkDetails();
    }


    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> parseExceptionResponse(DateTimeParseException dateTimeParseException){
        String stringBuilder = dateTimeParseException.getLocalizedMessage();
        return new ResponseEntity<>(stringBuilder, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
