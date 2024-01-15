package com.qdb.in.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.qdb.in.model.Comments;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PDFFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long userId;
	
	private String filename;
	
	private byte[] allDoc;
	
	 private String postBody;
	    
	 private Date date;
	 
	 private List<Comments> comments;

}
