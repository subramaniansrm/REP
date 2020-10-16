package com.srm.repg.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RealEstateDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5817394834619607983L;
	
	public String colorCode;
	
	
	private List<RealEstateDetailsDTO>listDetails=new ArrayList<>();


	/**
	 * @return the listDetails
	 */
	public List<RealEstateDetailsDTO> getListDetails() {
		return listDetails;
	}


	/**
	 * @param listDetails the listDetails to set
	 */
	public void setListDetails(List<RealEstateDetailsDTO> listDetails) {
		this.listDetails = listDetails;
	}


	/**
	 * @return the colorCode
	 */
	public String getColorCode() {
		return colorCode;
	}


	/**
	 * @param colorCode the colorCode to set
	 */
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
}
