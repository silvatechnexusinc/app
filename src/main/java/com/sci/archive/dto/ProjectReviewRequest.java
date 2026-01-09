package com.sci.archive.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectReviewRequest {
    
    @NotBlank
    private String status; // APPROVED, REJECTED, NEEDS_REVISION
    
    private String comments;
    
    @Min(0)
    @Max(100)
    private Integer marks;
}
