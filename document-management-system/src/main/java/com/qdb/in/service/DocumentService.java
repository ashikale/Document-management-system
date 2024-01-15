package com.qdb.in.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.qdb.in.bean.PDFFile;
import com.qdb.in.model.Comments;
import com.qdb.in.model.Document;


public interface DocumentService {
	
	public Document saveFile(MultipartFile file,long userId) throws IOException;
	
	public Document createPost(long id, Map<String,Object> fields);
	
	public Document getPdfById(Long id);
	
	public List<Document> getAllPdf();
	
	public List<Document> getPdfByUserId(Long userId);
	
	public void removeFileById(Long id);
	
	public void removeFileByUserId(Long id);
	
	public Document updateDocumentByFields(long id, Map<String,Object> fields);
	
	public PDFFile createComment(long id, Comments comments);
	

}
