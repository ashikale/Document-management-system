package com.qdb.in.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.qdb.in.bean.ApiException;
import com.qdb.in.bean.PDFFile;
import com.qdb.in.exception.ResourceNotFoundException;
import com.qdb.in.model.Comments;
import com.qdb.in.model.Document;
import com.qdb.in.outbound.MyFeignClient;
import com.qdb.in.repository.CommentsRepository;
import com.qdb.in.repository.DocumentRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;
	
	
	@Autowired
	private CommentsRepository commentsRepository;

	@Autowired
	private MyFeignClient myFeignClient;
	private static final String SERVICE_NAME = "document-management-system";

	

	@Override
	public Document saveFile(MultipartFile file,long userId) throws IOException {
		Document document = new Document();
		document.setFileName(file.getOriginalFilename());
		document.setData(file.getBytes());
	    document.setUserId(userId);
		return documentRepository.save(document);

	}


	
	@Override
	public Document getPdfById(Long id) {
		Optional<Document> doc = documentRepository.findById(id);
		if(doc.isEmpty()) {
			return null;
		}else {
		Document document = doc.get();
		return document;
		}
	
	}

	@Override
	public List<Document> getAllPdf() {
		return Optional.ofNullable(documentRepository.findAll()).get();
	}

	

	@Override
	public List<Document> getPdfByUserId(Long userId) {
		return Optional.ofNullable(documentRepository.findByUserId(userId)).get();
	}

	@Override
	public void removeFileById(Long id) {
		documentRepository.deleteById(id);	
	}

	@Override
	public void removeFileByUserId(Long id) {
		documentRepository.deleteAll();
	}



	@Override
	public Document createPost(long id, Map<String,Object> fields) {
		Document response=null;
		if(null!=getPdfById(id)) {
			Document updateDocumentByFields = updateDocumentByFields(id, fields);
			myFeignClient.createResource(updateDocumentByFields);
			response= updateDocumentByFields;
		}else {
			response= Optional.ofNullable(response).orElseThrow(()->new ResourceNotFoundException("No File Exist with ID"+id));
		}
		return response;
		
	}

	@Override
	public Document updateDocumentByFields(long id, Map<String, Object> fields) {
		
		Document exitingDoc = getPdfById(id);
		fields.forEach((key,value)->{
			Field field=ReflectionUtils.findField(Document.class, key);
		field.setAccessible(true);
		ReflectionUtils.setField(field, exitingDoc, value);
		});
		exitingDoc.setDate(new Date());
		return Optional.ofNullable(documentRepository.save(exitingDoc)).get();
	}


    //Circuit Breaker pattern coding
	//
	@Override
	@CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "fallbackMethod")
	public PDFFile createComment(long id, Comments comments) {
		PDFFile response=null;
		Document exitingDoc = getPdfById(id);
		List<Comments> commentslist= new ArrayList<>();
		if(null!=getPdfById(id)) {
			commentslist.add(comments);
			//Consuming third party api using feign client
			Comments feigncomments=myFeignClient.createComment(comments);
			commentslist.add(feigncomments);
			commentsRepository.saveAll(commentslist);
			response= PDFFile.builder().allDoc(exitingDoc.getData()).comments(commentslist).filename(exitingDoc.getFileName()).postBody(exitingDoc.getPostBody()).date(exitingDoc.getDate()).id(exitingDoc.getId()).userId(exitingDoc.getUserId()).build();
		}else {
			response=Optional.ofNullable(response).orElseThrow(()->new ResourceNotFoundException("No File Exist with ID"+id));
		}
		return response;
	}
    
    //FallBack Method for CB pattern
    private PDFFile fallbackMethod(long id, Comments comments,Exception e) {
        return PDFFile.builder().postBody("Fallback method is called, 3rd party api currently not responding").build();
    }
	

	


}
