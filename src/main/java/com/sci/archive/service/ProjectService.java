package com.sci.archive.service;

import com.sci.archive.model.Document;
import com.sci.archive.model.Project;
import com.sci.archive.model.User;
import com.sci.archive.repository.DocumentRepository;
import com.sci.archive.repository.ProjectRepository;
import com.sci.archive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    public Project createProject(Project project, Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (!student.getRole().equals(User.Role.STUDENT)) {
            throw new RuntimeException("Only students can create projects");
        }
        
        project.setStudent(student);
        project.setStatus(Project.ProjectStatus.SUBMITTED);
        
        return projectRepository.save(project);
    }
    
    public Project updateProject(Long projectId, Project updatedProject, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        // Check if user is the owner
        if (!project.getStudent().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this project");
        }
        
        project.setTitle(updatedProject.getTitle());
        project.setDescription(updatedProject.getDescription());
        project.setCourse(updatedProject.getCourse());
        project.setAcademicYear(updatedProject.getAcademicYear());
        project.setSemester(updatedProject.getSemester());
        
        if (updatedProject.getSupervisor() != null) {
            project.setSupervisor(updatedProject.getSupervisor());
        }
        
        return projectRepository.save(project);
    }
    
    public Document uploadDocument(Long projectId, MultipartFile file, Document.DocumentType documentType, 
                                   String description, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        // Check if user is the owner
        if (!project.getStudent().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to upload documents for this project");
        }
        
        // Store file
        String filePath = fileStorageService.storeFile(file, projectId);
        
        // Create document record
        Document document = new Document();
        document.setFileName(file.getOriginalFilename());
        document.setFilePath(filePath);
        document.setDocumentType(documentType);
        document.setFileType(getFileExtension(file.getOriginalFilename()));
        document.setFileSize(file.getSize());
        document.setDescription(description);
        document.setProject(project);
        
        return documentRepository.save(document);
    }
    
    public Project reviewProject(Long projectId, Project.ProjectStatus status, String comments, 
                                Integer marks, Long lecturerId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        User lecturer = userRepository.findById(lecturerId)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));
        
        if (!lecturer.getRole().equals(User.Role.LECTURER) && !lecturer.getRole().equals(User.Role.ADMIN)) {
            throw new RuntimeException("Only lecturers can review projects");
        }
        
        project.setStatus(status);
        project.setLecturerComments(comments);
        project.setMarks(marks);
        project.setReviewedAt(LocalDateTime.now());
        
        if (project.getSupervisor() == null) {
            project.setSupervisor(lecturer);
        }
        
        return projectRepository.save(project);
    }
    
    public List<Project> getStudentProjects(Long studentId) {
        return projectRepository.findByStudentId(studentId);
    }
    
    public List<Project> getLecturerProjects(Long lecturerId) {
        return projectRepository.findBySupervisorId(lecturerId);
    }
    
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }
    
    public List<Document> getProjectDocuments(Long projectId) {
        return documentRepository.findByProjectId(projectId);
    }
    
    public void deleteProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        if (!project.getStudent().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this project");
        }
        
        // Delete associated files
        List<Document> documents = documentRepository.findByProjectId(projectId);
        for (Document doc : documents) {
            fileStorageService.deleteFile(doc.getFilePath());
        }
        
        projectRepository.delete(project);
    }
    
    public void deleteDocument(Long documentId, Long userId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        
        Project project = document.getProject();
        if (!project.getStudent().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this document");
        }
        
        fileStorageService.deleteFile(document.getFilePath());
        documentRepository.delete(document);
    }
    
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }
}
