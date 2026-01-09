package com.sci.archive.controller;

import com.sci.archive.dto.MessageResponse;
import com.sci.archive.dto.ProjectRequest;
import com.sci.archive.dto.ProjectReviewRequest;
import com.sci.archive.model.Document;
import com.sci.archive.model.Project;
import com.sci.archive.model.User;
import com.sci.archive.repository.UserRepository;
import com.sci.archive.security.UserDetailsImpl;
import com.sci.archive.service.FileStorageService;
import com.sci.archive.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectRequest projectRequest,
                                          Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            
            Project project = new Project();
            project.setTitle(projectRequest.getTitle());
            project.setDescription(projectRequest.getDescription());
            project.setCourse(projectRequest.getCourse());
            project.setAcademicYear(projectRequest.getAcademicYear());
            project.setSemester(projectRequest.getSemester());
            
            if (projectRequest.getSupervisorId() != null) {
                User supervisor = userRepository.findById(projectRequest.getSupervisorId())
                        .orElseThrow(() -> new RuntimeException("Supervisor not found"));
                project.setSupervisor(supervisor);
            }
            
            Project savedProject = projectService.createProject(project, userDetails.getId());
            return ResponseEntity.ok(savedProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> updateProject(@PathVariable Long id,
                                          @Valid @RequestBody ProjectRequest projectRequest,
                                          Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            
            Project project = new Project();
            project.setTitle(projectRequest.getTitle());
            project.setDescription(projectRequest.getDescription());
            project.setCourse(projectRequest.getCourse());
            project.setAcademicYear(projectRequest.getAcademicYear());
            project.setSemester(projectRequest.getSemester());
            
            if (projectRequest.getSupervisorId() != null) {
                User supervisor = userRepository.findById(projectRequest.getSupervisorId())
                        .orElseThrow(() -> new RuntimeException("Supervisor not found"));
                project.setSupervisor(supervisor);
            }
            
            Project updatedProject = projectService.updateProject(id, project, userDetails.getId());
            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
    
    @PostMapping("/{id}/documents")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> uploadDocument(@PathVariable Long id,
                                           @RequestParam("file") MultipartFile file,
                                           @RequestParam("documentType") String documentType,
                                           @RequestParam(value = "description", required = false) String description,
                                           Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            
            Document.DocumentType docType = Document.DocumentType.valueOf(documentType.toUpperCase());
            
            Document document = projectService.uploadDocument(id, file, docType, description, userDetails.getId());
            return ResponseEntity.ok(document);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/my-projects")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<Project>> getMyProjects(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Project> projects = projectService.getStudentProjects(userDetails.getId());
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/supervised")
    @PreAuthorize("hasRole('LECTURER') or hasRole('ADMIN')")
    public ResponseEntity<List<Project>> getSupervisedProjects(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Project> projects = projectService.getLecturerProjects(userDetails.getId());
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('LECTURER') or hasRole('ADMIN')")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}/documents")
    public ResponseEntity<List<Document>> getProjectDocuments(@PathVariable Long id) {
        List<Document> documents = projectService.getProjectDocuments(id);
        return ResponseEntity.ok(documents);
    }
    
    @PostMapping("/{id}/review")
    @PreAuthorize("hasRole('LECTURER') or hasRole('ADMIN')")
    public ResponseEntity<?> reviewProject(@PathVariable Long id,
                                          @Valid @RequestBody ProjectReviewRequest reviewRequest,
                                          Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            
            Project.ProjectStatus status = Project.ProjectStatus.valueOf(reviewRequest.getStatus().toUpperCase());
            
            Project reviewedProject = projectService.reviewProject(
                    id, status, reviewRequest.getComments(), reviewRequest.getMarks(), userDetails.getId());
            
            return ResponseEntity.ok(reviewedProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/documents/{documentId}/download")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId) {
        try {
            Document document = projectService.getProjectDocuments(document.getProject().getId())
                    .stream()
                    .filter(doc -> doc.getId().equals(documentId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Document not found"));
            
            Resource resource = fileStorageService.loadFileAsResource(document.getFilePath());
            
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "attachment; filename=\"" + document.getFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> deleteProject(@PathVariable Long id, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            projectService.deleteProject(id, userDetails.getId());
            return ResponseEntity.ok(new MessageResponse("Project deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/documents/{documentId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> deleteDocument(@PathVariable Long documentId, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            projectService.deleteDocument(documentId, userDetails.getId());
            return ResponseEntity.ok(new MessageResponse("Document deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
}
