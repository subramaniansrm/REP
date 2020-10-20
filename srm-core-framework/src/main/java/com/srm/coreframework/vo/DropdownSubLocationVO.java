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
public class DropdownSubLocationVO extends CommonVO {

	private Integer sublocationId; // Id of Sublocation.

	private String subLocationName; // Name of sublocation.

}
