package com.sci.archive.repository;

import com.sci.archive.model.Document;
import com.sci.archive.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    List<Document> findByProject(Project project);
    
    List<Document> findByProjectId(Long projectId);
    
    List<Document> findByDocumentType(Document.DocumentType documentType);
    
    List<Document> findByProjectIdAndDocumentType(Long projectId, Document.DocumentType documentType);
}
