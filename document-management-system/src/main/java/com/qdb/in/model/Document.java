package com.qdb.in.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "document")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_id")
	private Long id;

	private String fileName;

	@Lob
	private byte[] data;

	private long userId;

	private String postBody;

	private Date date;

	
	

}
