/**
 * 
 */
package com.srm.coreframework.util;

import java.io.Serializable;

import lombok.Data;

/**
 *
 */

@Data
public class BaseEntityDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long createdBy=null;
	private String createdAt=null;
	private Long updatedBy=null;
	private String updatedAt=null;
}
