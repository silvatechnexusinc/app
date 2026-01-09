package com.sci.archive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String fileName;
    
    @NotBlank
    @Size(max = 500)
    @Column(nullable = false)
    private String filePath;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;
    
    @Size(max = 100)
    private String fileType; // e.g., pdf, docx, etc.
    
    private Long fileSize; // in bytes
    
    @Column(length = 500)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;
    
    public enum DocumentType {
        PRODUCT_REQUIREMENTS_DOCUMENT,
        SOFTWARE_DESIGN_DOCUMENT,
        USER_MANUAL,
        TECHNICAL_DOCUMENTATION,
        TEST_PLAN,
        SOURCE_CODE,
        FINAL_REPORT,
        PRESENTATION,
        OTHER
    }
}
