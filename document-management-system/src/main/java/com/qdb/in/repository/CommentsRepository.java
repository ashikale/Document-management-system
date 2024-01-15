package com.qdb.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qdb.in.model.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long>{

}
