package com.srm.coreframework.vo;

import java.util.List;

import lombok.Data;

@Data
public class UserEntityMappingVo extends CommonVO {

	private Integer commonId;

	private Integer userId;

	private Integer entityId;

	private Integer defaultId;
	
	private List<EntityLicenseVO> entityLicenseVOList;
}
