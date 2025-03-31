package com.leo.reportes.infrastructure.endpoints;


import com.leo.reportes.domain.gateway.RectorGateway;
import com.leo.reportes.domain.models.User;
import com.leo.reportes.infrastructure.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rector")
@RequiredArgsConstructor
public class RectorController {

    private final RectorGateway rectorGateway;
    @PreAuthorize("hasAuthority('RECTOR')")
    @GetMapping("/students")
    public ResponseEntity<List<String>> getAllStudents(){
        return new ResponseEntity<>(rectorGateway.getAllStudents(),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('RECTOR')")
    @PostMapping("/students/{studentName}")
    public ResponseEntity<User> addStudent(@PathVariable String studentName){
        return new ResponseEntity<>(rectorGateway.addStudent(studentName),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('RECTOR')")
    @GetMapping("/result")
    public ResponseEntity<List<User>> getAllResults(){
        return new ResponseEntity<>(rectorGateway.getAllResults(),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('RECTOR')")
    @GetMapping("/report/global")
    public ResponseEntity<byte[]> getGlobalReport(){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "report.pdf");
            return new ResponseEntity<>
                    (rectorGateway.generateGlobalReport(), headers, HttpStatus.OK);
    }
}
