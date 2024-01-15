package com.qdb.in.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.qdb.in.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{
	
	public Optional<Document> findById(Long id);
	
	public List<Document> findAll();
	
	public List<Document> findByUserId(long id);

}
