package com.c4l.creditCardservice.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCard {


private String id;
private String pseudoCard;
private Date createdDate;
private String status;
private String prodCode;

	
}
