package com.shf.makerspace.repository;

import com.shf.makerspace.models.AttachedDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachedDocumentsRepository extends JpaRepository<AttachedDocuments, Long> {

    AttachedDocuments findAttachedDocumentsById(Long id);

    @Query(value = "select attachment from AttachedDocuments attachment where attachment.moduleId in(:moduleIds) and " +
            " attachment.moduleType =:moduleType")
    List<AttachedDocuments> findAttachedDocumentsByModuleIdAndModuleType(List<Long> moduleIds, String moduleType);

    List<AttachedDocuments> findAttachedDocumentsByModuleIdAndModuleType(Long moduleId, String moduleType);
    List<AttachedDocuments> findAllDocumentsByModuleType(String moduleType);
}
