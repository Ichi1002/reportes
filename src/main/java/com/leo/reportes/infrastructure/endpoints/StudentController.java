package com.leo.reportes.infrastructure.endpoints;

import com.leo.reportes.domain.gateway.StudentGateway;
import com.leo.reportes.domain.models.User;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StudentController {

    private final StudentGateway studentGateway;
    @PreAuthorize("hasAnyAuthority('STUDENT', 'RECTOR')")
    @GetMapping("/report/student/{id}")
    public ResponseEntity<byte[]> getStudentReport(@PathVariable long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        //todo personaliar el nombre del archivo pdf
        headers.setContentDispositionFormData("filename", "report.pdf");
        return new ResponseEntity<byte[]>
                (studentGateway.generateReport(id), headers, HttpStatus.OK);
    }

    @GetMapping("/result/{id}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'RECTOR')")
    public ResponseEntity<User> getStudentResult(@PathVariable Long id) {
        return new ResponseEntity<>
                (studentGateway.generateResult(id), HttpStatus.OK);
    }
    }
