package com.srm.coreframework.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 *
 * @author Navaneetha
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubLocationVO extends CommonVO {

	private Integer sublocationId; // Id of Sublocation.

	private Integer id;// Id of location.

	private String userLocationName; // Name of userLocation.

	private String subLocationName; // Name of sublocation.

	private boolean subLocationIsActive; // 0-Active 1-Inactive

	private String subLocationCode;// Code of sublocation.

	private List<Integer> subLocationList; // List of sublocation.

	private Integer[] deleteItem;

	private String status;

	private Integer entityId;

	private Integer screenId;
	private Integer subScreenId;
	

}
