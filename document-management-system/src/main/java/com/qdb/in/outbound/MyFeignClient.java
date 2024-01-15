package com.qdb.in.outbound;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.qdb.in.bean.PDFFile;
import com.qdb.in.model.Comments;
import com.qdb.in.model.Document;



@FeignClient(name= "JSONPLACEHOLDER" ,url = "https://jsonplaceholder.typicode.com")
public interface MyFeignClient {
	
	    @PostMapping("/posts")
	    public Document createResource(@RequestBody Document document);
	    
	    @PostMapping("/posts")
	    public Comments createComment(@RequestBody Comments comment);
	    
	   

}
