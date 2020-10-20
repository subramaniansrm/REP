package com.srm.coreframework.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/properties/picture_path.properties")
public class PicturePath {
	
	/*@Value("${widgetFilePath}")
	private String widgetFilePath;
	*/
	
	@Value("${phoneBookFilePath}")
	private String phoneBookFilePath;
	
	@Value("${requestAttachmentPath}")
	private String requestAttachmentPath;
	
	
	@Value("${phoneBookExcelAttachment}")
	private String phoneBookExcelAttachment;
	
	@Value("${externalLinkFilePath}")
	private String externalLinkFilePath;
	
	@Value("${userFilePath}")
	private String userFilePath;
	

	
	// DownloadPath
	@Value("${widgetDownloadPath}")
	private String widgetDownloadPath;

	@Value("${externalLinkDownloadPath}")
	private String externalLinkDownloadPath;

	@Value("${phoneBookDownloadPath}")
	private String phoneBookDownloadPath;

	@Value("${userDownloadPath}")
	private String userDownloadPath;
	
	
	@Value("${zipPicturePath}")
	private String zipPicturePath;
	
	
	@Value("${defaultImagePath}")
	private String defaultImagePath;
	

	@Value("${defaultImage}")
	private String defaultImage;
	
	@Value("${defaultPlanImage}")
	private String defaultPlanImage;
	
	@Value("${defaultPlanImagePath}")
	private String defaultPlanImagePath;
	
	@Value("${planImageFilePath}")
	private String planImageFilePath;
	
	/**
	 * @return the planImageFilePath
	 */
	public String getPlanImageFilePath() {
		return planImageFilePath;
	}

	/**
	 * @param planImageFilePath the planImageFilePath to set
	 */
	public void setPlanImageFilePath(String planImageFilePath) {
		this.planImageFilePath = planImageFilePath;
	}

	/**
	 * @return the defaultPlanImagePath
	 */
	public String getDefaultPlanImagePath() {
		return defaultPlanImagePath;
	}

	/**
	 * @param defaultPlanImagePath the defaultPlanImagePath to set
	 */
	public void setDefaultPlanImagePath(String defaultPlanImagePath) {
		this.defaultPlanImagePath = defaultPlanImagePath;
	}

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	public String getDefaultImagePath() {
		return defaultImagePath;
	}

	public void setDefaultImagePath(String defaultImagePath) {
		this.defaultImagePath = defaultImagePath;
	}

	public String getPhoneBookFilePath() {
		return phoneBookFilePath;
	}

	public void setPhoneBookFilePath(String phoneBookFilePath) {
		this.phoneBookFilePath = phoneBookFilePath;
	}

	/**
	 * @return the widgetFilePath
	 */
	/*public String getWidgetFilePath() {
		return widgetFilePath;
	}

	*//**
	 * @param widgetFilePath the widgetFilePath to set
	 *//*
	public void setWidgetFilePath(String widgetFilePath) {
		this.widgetFilePath = widgetFilePath;
	}
*/
	/**
	 * @return the externalLinkFilePath
	 */
	public String getExternalLinkFilePath() {
		return externalLinkFilePath;
	}

	/**
	 * @param externalLinkFilePath the externalLinkFilePath to set
	 */
	public void setExternalLinkFilePath(String externalLinkFilePath) {
		this.externalLinkFilePath = externalLinkFilePath;
	}

	/**
	 * @return the userFilePath
	 */
	public String getUserFilePath() {
		return userFilePath;
	}

	/**
	 * @param userFilePath the userFilePath to set
	 */
	public void setUserFilePath(String userFilePath) {
		this.userFilePath = userFilePath;
	}



	public String getWidgetDownloadPath() {
		return widgetDownloadPath;
	}

	public void setWidgetDownloadPath(String widgetDownloadPath) {
		this.widgetDownloadPath = widgetDownloadPath;
	}

	public String getExternalLinkDownloadPath() {
		return externalLinkDownloadPath;
	}

	public void setExternalLinkDownloadPath(String externalLinkDownloadPath) {
		this.externalLinkDownloadPath = externalLinkDownloadPath;
	}

	public String getPhoneBookDownloadPath() {
		return phoneBookDownloadPath;
	}

	public void setPhoneBookDownloadPath(String phoneBookDownloadPath) {
		this.phoneBookDownloadPath = phoneBookDownloadPath;
	}

	public String getUserDownloadPath() {
		return userDownloadPath;
	}

	public void setUserDownloadPath(String userDownloadPath) {
		this.userDownloadPath = userDownloadPath;
	}

	public String getRequestAttachmentPath() {
		return requestAttachmentPath;
	}

	public void setRequestAttachmentPath(String requestAttachmentPath) {
		this.requestAttachmentPath = requestAttachmentPath;
	}

	public String getPhoneBookExcelAttachment() {
		return phoneBookExcelAttachment;
	}

	public void setPhoneBookExcelAttachment(String phoneBookExcelAttachment) {
		this.phoneBookExcelAttachment = phoneBookExcelAttachment;
	}

	public String getZipPicturePath() {
		return zipPicturePath;
	}

	public void setZipPicturePath(String zipPicturePath) {
		this.zipPicturePath = zipPicturePath;
	}

	public String getDefaultPlanImage() {
		return defaultPlanImage;
	}

	public void setDefaultPlanImage(String defaultPlanImage) {
		this.defaultPlanImage = defaultPlanImage;
	}

	
	
	

}
