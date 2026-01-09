package com.sci.archive.repository;

import com.sci.archive.model.Project;
import com.sci.archive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    List<Project> findByStudent(User student);
    
    List<Project> findByStudentId(Long studentId);
    
    List<Project> findBySupervisor(User supervisor);
    
    List<Project> findBySupervisorId(Long supervisorId);
    
    List<Project> findByStatus(Project.ProjectStatus status);
    
    List<Project> findByAcademicYear(String academicYear);
    
    List<Project> findByCourse(String course);
    
    List<Project> findByAcademicYearAndSemester(String academicYear, String semester);
}
