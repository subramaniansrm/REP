package com.srm.coreframework.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_entity_mapping", schema = "common_rta_2_local")
@Data
public class UserEntityMapping {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COMMON_ID")
    private Integer commonId;
 
 
    @Column(name = "USER_ID")
    private Integer userId;
    
    
    @Column(name = "ENTITY_ID")
    private Integer entityId;

    
    @Column(name = "DEFAULT_ID")
    private Integer defaultId;


}
