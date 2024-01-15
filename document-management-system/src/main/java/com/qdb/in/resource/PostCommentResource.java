package com.qdb.in.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qdb.in.bean.PDFFile;
import com.qdb.in.model.Comments;
import com.qdb.in.model.Document;
import com.qdb.in.service.DocumentService;


//This is Contoller Class for our application

@RestController
public class PostCommentResource {

	@Autowired
    private DocumentService documentService;
	
	
	
	//Post Method to upload a pdf file into a database
    @PostMapping(value="/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam long userId) {
        try {
            Document savedDocument = documentService.saveFile(file,userId);
            return ResponseEntity.ok("File uploaded successfully. Document ID: " + savedDocument.getId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading the file.");
        }
    }
    
  //Patch Method to post the file using third party api with feignclient
    @PatchMapping(value="/upload/post")
    public ResponseEntity<Document> createPost(@RequestParam long id,@RequestBody Map<String,Object> fields){
    	ResponseEntity<Document> response=null;
    	Document document = documentService.createPost(id, fields);
    	if(null !=document) {
    		response= ResponseEntity.ok().body(document);
    	}else {
    		response= ResponseEntity.noContent().build();
    	}
    	return response;
    }
    
  //Post Method to update comments for a pdf file  using third party api with feignclient
    @PostMapping(value="/upload/post/comment")
    public ResponseEntity<PDFFile> createPost(@RequestParam long id,@RequestBody Comments comments){
    	ResponseEntity<PDFFile> response=null;
    	PDFFile pdfresponsefile = documentService.createComment(id, comments);
    	if(null !=pdfresponsefile) {
    		response= ResponseEntity.ok().body(pdfresponsefile);
    	}else {
    		response= ResponseEntity.noContent().build();
    	}
    	return response;
    }
    
    
    //GET method to retrieve PDF file by document ID.
    @GetMapping(value="/upload/getpdfbyid")
    public ResponseEntity<byte[]> getPdfById(@RequestParam long id) throws IOException{
    	ResponseEntity<byte[]> response=null;
    	Document pdfbyId = documentService.getPdfById(id);
    	if(null !=pdfbyId) {
    		byte[] data = pdfbyId.getData();
    		response= ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + pdfbyId.getFileName())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(data);
    	}else {
    		response= ResponseEntity.noContent().build();
    	}
    	return response;
    	
    }
    
  //GET method to retrieve PDF file by document userid.
    @GetMapping(value="/upload/getpdfbyuserid")
    public ResponseEntity<List<PDFFile>> getPdfByuserId(@RequestParam Long userId) throws IOException{
    	ResponseEntity<List<PDFFile>> response=null;
    	List<Document> allPdf  = documentService.getPdfByUserId(userId);
    	List<PDFFile> byteList= new ArrayList<>();
    	allPdf.stream().forEach(s->byteList.add(PDFFile.builder().postBody(s.getPostBody()).date(s.getDate()).userId(s.getUserId()).filename(s.getFileName()).allDoc(s.getData()).id(s.getId()).build()));
    	if(!CollectionUtils.isEmpty(byteList)) {
        	response= ResponseEntity.ok().body(byteList);
        }else {
        	response= ResponseEntity.noContent().build();
        	
    }
        return response;
    }
    
  //GET method to ALL retrieve PDF file from database.
    @GetMapping(value="/upload/allpdf")
    public ResponseEntity<List<PDFFile>> getAllPdf() throws IOException{
    	ResponseEntity<List<PDFFile>> response=null;
    	List<PDFFile> byteList= new ArrayList<>();
    	List<Document> allPdf = documentService.getAllPdf();
    	allPdf.stream().forEach(s->byteList.add(PDFFile.builder().postBody(s.getPostBody()).date(s.getDate()).userId(s.getUserId()).filename(s.getFileName()).allDoc(s.getData()).id(s.getId()).build()));
        if(!CollectionUtils.isEmpty(byteList)) {
        	response= ResponseEntity.ok().body(byteList);
        }else {
        	response= ResponseEntity.noContent().build();
        	
    }
        return response;
    }
    
    //DELETE Request to remove pdf file from database by document id
    @DeleteMapping(value="/upload/removepdfbyid")
    public  ResponseEntity<String> removePdfById(@RequestParam long id) throws IOException{
    	documentService.removeFileById(id);
    	if(documentService.getPdfById(id)==null) {
    		return ResponseEntity.ok().body("File Removed successfully. Document ID: " + id);
        } else {
            return ResponseEntity.status(500).body("Error in removing the file.");
        }
    	
    }
    
  //DELETE Request to remove pdf file from database by user id
    @DeleteMapping(value="/upload/removepdfbyuserid")
    public  ResponseEntity<String> removePdfByUserId(@RequestParam long userId) throws IOException{
    	documentService.removeFileByUserId(userId);
    	if(org.springframework.util.CollectionUtils.isEmpty(documentService.getPdfByUserId(userId))) {
    		return ResponseEntity.ok().body("File Removed successfully. user ID: " + userId);
        } else {
            return ResponseEntity.status(500).body("Error in removing the file.");
        }
    	
    }
    

}
