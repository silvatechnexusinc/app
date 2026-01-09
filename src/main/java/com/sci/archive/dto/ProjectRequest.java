package com.sci.archive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectRequest {
    
    @NotBlank
    @Size(max = 200)
    private String title;
    
    @Size(max = 2000)
    private String description;
    
    @Size(max = 100)
    private String course;
    
    @Size(max = 50)
    private String academicYear;
    
    @Size(max = 50)
    private String semester;
    
    private Long supervisorId;
}
