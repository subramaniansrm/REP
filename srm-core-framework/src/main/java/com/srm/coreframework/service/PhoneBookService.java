package com.srm.coreframework.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.PicturePath;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.PhoneBookDAO;
import com.srm.coreframework.entity.PhoneBookEntity;
import com.srm.coreframework.repository.PhoneBookRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.util.DateUtil;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.PhoneBookVO;

@Service
public class PhoneBookService extends CommonController<PhoneBookVO> {
	Logger logger = LoggerFactory.getLogger(PhoneBookService.class);

	@Autowired
	private PhoneBookRepository phoneBookRepo;

	@Autowired
	private PhoneBookDAO phoneBookDao;

	

	@Autowired
	private PicturePath picturePath;

	/**
	 * Method is used to get all the details of phone Booking
	 * 
	 * @return
	 */
	@Transactional()
	public List<PhoneBookVO> getAll(AuthDetailsVo authDetailsVo) {

		List<Object[]> phoneBookEntityList = null;

		List<PhoneBookVO> phoneBookVo = new ArrayList<PhoneBookVO>();

		// Get all the details of phone book in DB
		try {

			phoneBookEntityList = phoneBookDao.getAll(authDetailsVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

		// Set all the details in VO
		try {

			if (phoneBookEntityList != null && phoneBookEntityList.size() > 0)
				phoneBookVo = getAllList(phoneBookEntityList);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new CommonException(getMessage("copyFailure",authDetailsVo));
		}

		return phoneBookVo;

	}

	/**
	 * Method is used to Load the Phone Booking Details
	 * 
	 * @param phoneBook
	 *            PhoneBookVo
	 * @param authDetailsVo 
	 * @return phoneBookVo List<PhoneBookVo>
	 */
	@Transactional
	public PhoneBookVO load(PhoneBookVO phoneBook, AuthDetailsVo authDetailsVo) {

		PhoneBookVO phoneBookVo = new PhoneBookVO();
		Object[] phoneBookEntityList = null;

		// Get the details of phone book in DB using Phone Book ID
		try {

			phoneBookEntityList = phoneBookDao.load(phoneBook.getPhoneBookId(),authDetailsVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

		// Set all the details in VO
		try {

			phoneBookVo = getAllListLoad(phoneBookEntityList, phoneBook);
			return phoneBookVo;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new CommonException(getMessage("copyFailure",authDetailsVo));
		}

	}

	/**
	 * 
	 * Method used to view PhoneBook
	 * 
	 * @param phoneBook
	 * @param bookVo
	 * @return
	 * @throws IOException 
	 */

	private PhoneBookVO getAllListLoad(Object[] phoneBook, PhoneBookVO bookVo) throws IOException {
		PhoneBookVO phoneBookviewVo = new PhoneBookVO();

		BeanUtils.copyProperties(bookVo, phoneBookviewVo);
		if (0 != (int) ((Object[]) phoneBook)[0]) {
			phoneBookviewVo.setPhoneBookId((int) ((Object[]) phoneBook)[0]);
		}
		if (null != (String) ((Object[]) phoneBook)[1]) {
			phoneBookviewVo.setEmployeeId((String) ((Object[]) phoneBook)[1]);
		}
		if (null != (String) ((Object[]) phoneBook)[2]) {
			phoneBookviewVo.setEmployeeName((String) ((Object[]) phoneBook)[2]);
		}
		if (0 != (int) ((Object[]) phoneBook)[3]) {
			phoneBookviewVo.setUserDepartmentId((int) ((Object[]) phoneBook)[3]);
		}
		if (null != (String) ((Object[]) phoneBook)[4]) {
			phoneBookviewVo.setUserDepartment((String) ((Object[]) phoneBook)[4]);
		}
		if (0 != (int) ((Object[]) phoneBook)[5]) {
			phoneBookviewVo.setUserLocationId((int) ((Object[]) phoneBook)[5]);
		}
		if (null != (String) ((Object[]) phoneBook)[6]) {
			phoneBookviewVo.setLocation((String) ((Object[]) phoneBook)[6]);
		}
		if (0 != (int) ((Object[]) phoneBook)[7]) {
			phoneBookviewVo.setSublocationId((int) ((Object[]) phoneBook)[7]);
		}
		if (null != (String) ((Object[]) phoneBook)[8]) {
			phoneBookviewVo.setSubLocation((String) ((Object[]) phoneBook)[8]);
		}
		if (null != (String) ((Object[]) phoneBook)[9]) {
			phoneBookviewVo.setMobileNumberC((String) ((Object[]) phoneBook)[9]);
		}
		if (null != (String) ((Object[]) phoneBook)[10]) {
			phoneBookviewVo.setMobileNumberP((String) ((Object[]) phoneBook)[10]);
		}
		if (null != (String) ((Object[]) phoneBook)[11]) {
			phoneBookviewVo.setPhoneNumber((String) ((Object[]) phoneBook)[11]);
		}
		if (null != (String) ((Object[]) phoneBook)[12]) {
			phoneBookviewVo.setExtensionNumber((String) ((Object[]) phoneBook)[12]);
		}
		if (null != (String) ((Object[]) phoneBook)[13]) {
			phoneBookviewVo.setEmailId((String) ((Object[]) phoneBook)[13]);
		}
		if (null != (String) ((Object[]) phoneBook)[14]) {
			phoneBookviewVo.setSkypeId((String) ((Object[]) phoneBook)[14]);
		}
		if (null != ((Object[]) phoneBook)[15] && (int) ((Object[]) phoneBook)[15] == 1)
			phoneBookviewVo.setPhoneBookIsActive(true);
		else if (null != ((Object[]) phoneBook)[15] && (int) ((Object[]) phoneBook)[15] == 0)
			phoneBookviewVo.setPhoneBookIsActive(false);

		if (null != (String) ((Object[]) phoneBook)[16]) {

			/*StringBuffer modifiedQuery = new StringBuffer(picturePath.getPhoneBookDownloadPath());
			File file = new File((String) ((Object[]) phoneBook)[16]);
			modifiedQuery.append(file.getName());*/
			phoneBookviewVo.setPhoneBookProfile((String) ((Object[]) phoneBook)[16]);
			
		}
		try {
			// image loading
			if (null != phoneBookviewVo.getPhoneBookProfile()) {
				imageLoading(phoneBookviewVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try{
			
			if(null == phoneBookviewVo.getPhoneBookProfile() ){
				phoneBookviewVo.setImageLoad(defaultImage(picturePath.getDefaultImagePath()));
			}
		}catch(Exception e){
			
		}
		
		return phoneBookviewVo;
	}
	

	private PhoneBookVO imageLoading(PhoneBookVO phoneBookVO) throws IOException {
		BufferedImage originalImage;
		byte[] imageInByte;
		originalImage = ImageIO.read(new File(phoneBookVO.getPhoneBookProfile()));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, getMediaType1(phoneBookVO.getPhoneBookProfile()) , baos);
		baos.flush();
		imageInByte = baos.toByteArray();
		phoneBookVO.setImageLoad(imageInByte);
		return phoneBookVO;
	}

	
	/**
	 * Method is used to Get all search the phone booking details.
	 * 
	 * @param phoneBookVo
	 *            PhoneBookVo
	 * @param authDetailsVo 
	 * @return phoneBookVoList List<PhoneBookVo>
	 */
	@Transactional
	public List<PhoneBookVO> getAllSearch(PhoneBookVO phoneBookVo, AuthDetailsVo authDetailsVo) {

		List<Object[]> phoneBookEntity = null;

		List<PhoneBookVO> phoneBookVoList = null;

		// Get the details of phone book in DB using Phone Book ID
		try {

			phoneBookEntity = phoneBookDao.getAllSearch(phoneBookVo,authDetailsVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

		// Set all the details in VO
		try {

			phoneBookVoList = getAllList(phoneBookEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("copyFailure",authDetailsVo));
		}

		return phoneBookVoList;
	}
	
	
	private List<PhoneBookVO> getAllSearchList(List<Object[]> phoneBookEntityList) throws IOException {

		List<PhoneBookVO> phoneBookVoList = new ArrayList<PhoneBookVO>();

		for (Object phoneBook : phoneBookEntityList) {
			PhoneBookVO phoneBookVo = new PhoneBookVO();

			if (null != (Integer) ((Object[]) phoneBook)[0]) {
				phoneBookVo.setPhoneBookId((int) ((Object[]) phoneBook)[0]);
			}
			if (null != (String) ((Object[]) phoneBook)[1]) {
				phoneBookVo.setEmployeeId((String) ((Object[]) phoneBook)[1]);
			}
			if (null != (String) ((Object[]) phoneBook)[2]) {
				phoneBookVo.setEmployeeName((String) ((Object[]) phoneBook)[2]);
			}
			if (null!= (Integer) ((Object[]) phoneBook)[3]) {
				phoneBookVo.setUserDepartmentId((int) ((Object[]) phoneBook)[3]);
			}
			if (null != (String) ((Object[]) phoneBook)[4]) {
				phoneBookVo.setUserDepartment((String) ((Object[]) phoneBook)[4]);
			}
			if (null != (Integer) ((Object[]) phoneBook)[5]) {
				phoneBookVo.setUserLocationId((int) ((Object[]) phoneBook)[5]);
			}
			if (null != (String) ((Object[]) phoneBook)[6]) {
				phoneBookVo.setLocation((String) ((Object[]) phoneBook)[6]);
			}
			if (null!= (Integer) ((Object[]) phoneBook)[7]) {
				phoneBookVo.setSublocationId((int) ((Object[]) phoneBook)[7]);
			}
			if (null != (String) ((Object[]) phoneBook)[8]) {
				phoneBookVo.setSubLocation((String) ((Object[]) phoneBook)[8]);
			}
			if (null != (String) ((Object[]) phoneBook)[9]) {
				phoneBookVo.setMobileNumberC((String) ((Object[]) phoneBook)[9]);
			}
			if (null != (String) ((Object[]) phoneBook)[10]) {
				phoneBookVo.setMobileNumberP((String) ((Object[]) phoneBook)[10]);
			}
			if (null != (String) ((Object[]) phoneBook)[11]) {
				phoneBookVo.setPhoneNumber((String) ((Object[]) phoneBook)[11]);
			}
			if (null != (String) ((Object[]) phoneBook)[12]) {
				phoneBookVo.setExtensionNumber((String) ((Object[]) phoneBook)[12]);
			}
			if (null != (String) ((Object[]) phoneBook)[13]) {
				phoneBookVo.setEmailId((String) ((Object[]) phoneBook)[13]);
			}
			if (null != (String) ((Object[]) phoneBook)[14]) {
				phoneBookVo.setSkypeId((String) ((Object[]) phoneBook)[14]);
			}
			if (null != ((Object[]) phoneBook)[15] && (int) ((Object[]) phoneBook)[15] == 1) {
				phoneBookVo.setPhoneBookIsActive(true);
				phoneBookVo.setStatus(CommonConstant.Active);
			} else if (null != ((Object[]) phoneBook)[15] && (int) ((Object[]) phoneBook)[15] == 0) {
				phoneBookVo.setPhoneBookIsActive(false);
				phoneBookVo.setStatus(CommonConstant.InActive);
			}
			if (null != (String) ((Object[]) phoneBook)[16]) {

				/*StringBuffer modifiedQuery = new StringBuffer(picturePath.getPhoneBookDownloadPath());
				File file = new File((String) ((Object[]) phoneBook)[16]);
				modifiedQuery.append(file.getName());
				phoneBookVo.setPhoneBookProfile(modifiedQuery.toString());*/
				phoneBookVo.setPhoneBookProfile((String) ((Object[]) phoneBook)[16]);
			}
			try{
			//image loading
			if(null!= phoneBookVo.getPhoneBookProfile()){
				imageLoading(phoneBookVo);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				
				if(null == phoneBookVo.getPhoneBookProfile() ){
					phoneBookVo.setImageLoad(defaultImage(picturePath.getDefaultImagePath()));
				}
			}catch(Exception e){
				
			}
			phoneBookVoList.add(phoneBookVo);

		}
		return phoneBookVoList;
	}


	
	
	
	/**
	 * Method is to set the parameters in the phone booking
	 * 
	 * @param phoneBookEntityList
	 *            List<Object[]>
	 * @return phoneBookVoList List<PhoneBookVo>
	 * @throws IOException 
	 */
	private List<PhoneBookVO> getAllList(List<Object[]> phoneBookEntityList) throws IOException {

		List<PhoneBookVO> phoneBookVoList = new ArrayList<PhoneBookVO>();

		for (Object phoneBook : phoneBookEntityList) {
			PhoneBookVO phoneBookVo = new PhoneBookVO();

			if (null != (Integer) ((Object[]) phoneBook)[0]) {
				phoneBookVo.setPhoneBookId((int) ((Object[]) phoneBook)[0]);
			}
			if (null != (String) ((Object[]) phoneBook)[1]) {
				phoneBookVo.setEmployeeId((String) ((Object[]) phoneBook)[1]);
			}
			if (null != (String) ((Object[]) phoneBook)[2]) {
				phoneBookVo.setEmployeeName((String) ((Object[]) phoneBook)[2]);
			}
			/*if (null!= (Integer) ((Object[]) phoneBook)[3]) {
				phoneBookVo.setUserDepartmentId((int) ((Object[]) phoneBook)[3]);
			}*/
			if (null != (String) ((Object[]) phoneBook)[4]) {
				phoneBookVo.setUserDepartment((String) ((Object[]) phoneBook)[4]);
			}
		/*	if (null != (Integer) ((Object[]) phoneBook)[5]) {
				phoneBookVo.setUserLocationId((int) ((Object[]) phoneBook)[5]);
			}*/
			if (null != (String) ((Object[]) phoneBook)[6]) {
				phoneBookVo.setLocation((String) ((Object[]) phoneBook)[6]);
			}
		/*	if (null!= (Integer) ((Object[]) phoneBook)[7]) {
				phoneBookVo.setSublocationId((int) ((Object[]) phoneBook)[7]);
			}*/
			if (null != (String) ((Object[]) phoneBook)[8]) {
				phoneBookVo.setSubLocation((String) ((Object[]) phoneBook)[8]);
			}
		/*	if (null != (String) ((Object[]) phoneBook)[9]) {
				phoneBookVo.setMobileNumberC((String) ((Object[]) phoneBook)[9]);
			}
			if (null != (String) ((Object[]) phoneBook)[10]) {
				phoneBookVo.setMobileNumberP((String) ((Object[]) phoneBook)[10]);
			}*/
			if (null != (String) ((Object[]) phoneBook)[11]) {
				phoneBookVo.setPhoneNumber((String) ((Object[]) phoneBook)[11]);
			}
			/*if (null != (String) ((Object[]) phoneBook)[12]) {
				phoneBookVo.setExtensionNumber((String) ((Object[]) phoneBook)[12]);
			}*/
			if (null != (String) ((Object[]) phoneBook)[13]) {
				phoneBookVo.setEmailId((String) ((Object[]) phoneBook)[13]);
			}
		/*	if (null != (String) ((Object[]) phoneBook)[14]) {
				phoneBookVo.setSkypeId((String) ((Object[]) phoneBook)[14]);
			}*/
			if (null != ((Object[]) phoneBook)[15] && (int) ((Object[]) phoneBook)[15] == 1) {
				phoneBookVo.setPhoneBookIsActive(true);
				phoneBookVo.setStatus(CommonConstant.Active);
			} else if (null != ((Object[]) phoneBook)[15] && (int) ((Object[]) phoneBook)[15] == 0) {
				phoneBookVo.setPhoneBookIsActive(false);
				phoneBookVo.setStatus(CommonConstant.InActive);
			}
			if (null != (String) ((Object[]) phoneBook)[16]) {

				/*StringBuffer modifiedQuery = new StringBuffer(picturePath.getPhoneBookDownloadPath());
				File file = new File((String) ((Object[]) phoneBook)[16]);
				modifiedQuery.append(file.getName());
				phoneBookVo.setPhoneBookProfile(modifiedQuery.toString());*/
				//phoneBookVo.setPhoneBookProfile((String) ((Object[]) phoneBook)[16]);
			}
		/*	try{
			//image loading
			if(null!= phoneBookVo.getPhoneBookProfile()){
				imageLoading(phoneBookVo);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				
				if(null == phoneBookVo.getPhoneBookProfile() ){
					phoneBookVo.setImageLoad(defaultImage(picturePath.getDefaultImagePath()));
				}
			}catch(Exception e){
				
			}*/
			phoneBookVoList.add(phoneBookVo);

		}
		return phoneBookVoList;
	}

	/**
	 * Method used to create new Phone Book Details
	 * 
	 * @param phoneBookVo
	 */
	@Transactional
	public void create(PhoneBookVO phoneBookVo,AuthDetailsVo authDetailsVo) {

		PhoneBookEntity phoneBookEntity = new PhoneBookEntity();

		// Set all the details in Entity
		try {

			if (phoneBookVo.getEmployeeId() != null) {
				phoneBookEntity.setEmployeeId(phoneBookVo.getEmployeeId());
			}
			if ((phoneBookVo.getEmployeeName() != null)) {
				phoneBookEntity.setEmployeeName(phoneBookVo.getEmployeeName());
			}
			if (phoneBookVo.getUserDepartmentId() != 0) {
				phoneBookEntity.setUserDepartmentId(phoneBookVo.getUserDepartmentId());
			}
			if (phoneBookVo.getUserLocationId() != 0) {
				phoneBookEntity.setUserLocationId(phoneBookVo.getUserLocationId());
			}
			if (phoneBookVo.getSublocationId() != 0) {
				phoneBookEntity.setSublocationId(phoneBookVo.getSublocationId());
			}
			if (phoneBookVo.getMobileNumberC() != null) {
				phoneBookEntity.setMobileNumberC(phoneBookVo.getMobileNumberC());
			}
			if (phoneBookVo.getExtensionNumber() != null) {
				phoneBookEntity.setExtensionNumber(phoneBookVo.getExtensionNumber());
			}
			if (phoneBookVo.getEmailId() != null) {
				phoneBookEntity.setEmailId(phoneBookVo.getEmailId());
			}
			if (phoneBookVo.getMobileNumberP() != null) {
				phoneBookEntity.setMobileNumberP(phoneBookVo.getMobileNumberP());
			}

			if (phoneBookVo.getPhoneNumber() != null) {
				phoneBookEntity.setPhoneNumber(phoneBookVo.getPhoneNumber());
			}
			if (phoneBookVo.getSkypeId() != null) {
				phoneBookEntity.setSkypeId(phoneBookVo.getSkypeId());
			}

			phoneBookEntity.setPhoneBookIsActive(phoneBookVo.getPhoneBookIsActive());

			phoneBookEntity = setUserDetails(phoneBookEntity,authDetailsVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("copyFailure",authDetailsVo));
		}

		// Create the new phone book details
		try {

			phoneBookRepo.save(phoneBookEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

	/**
	 * Method used to set user details
	 * 
	 * @param phoneBookEntity
	 * @return
	 */
	@Transactional
	private PhoneBookEntity setUserDetails(PhoneBookEntity phoneBookEntity,AuthDetailsVo authDetailsVo) {

		phoneBookEntity.setCreateBy(authDetailsVo.getUserId());
		phoneBookEntity.setCreateDate(DateUtil.getCalenderDate());
		phoneBookEntity.setUpdateBy(authDetailsVo.getUserId());
		phoneBookEntity.setUpdateDate(DateUtil.getCalenderDate());

		return phoneBookEntity;
	}

	/**
	 * Method used to update Phone Book Details
	 * 
	 * @param phoneBookVo
	 */
	@Transactional
	public void update(PhoneBookVO phoneBookVo,AuthDetailsVo authDetailsVo) {

		PhoneBookEntity phoneBookEntity = new PhoneBookEntity();

		// Get the details of phone booking using ID
		try {

			phoneBookEntity = phoneBookRepo.findOne(phoneBookVo.getPhoneBookId());

		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noResultFound",authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noRecordFound",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

		// Set all the details in entity
		try {

			if (phoneBookVo.getPhoneBookId() != 0) {
				phoneBookEntity.setPhoneBookId(phoneBookVo.getPhoneBookId());
			}

			if (phoneBookVo.getEmployeeId() != null) {
				phoneBookEntity.setEmployeeId(phoneBookVo.getEmployeeId());
			}
			if ((phoneBookVo.getEmployeeName() != null)) {
				phoneBookEntity.setEmployeeName(phoneBookVo.getEmployeeName());
			}
			if (phoneBookVo.getUserDepartmentId() != 0) {
				phoneBookEntity.setUserDepartmentId(phoneBookVo.getUserDepartmentId());
			}
			if (phoneBookVo.getUserLocationId() != 0) {
				phoneBookEntity.setUserLocationId(phoneBookVo.getUserLocationId());
			}
			if (phoneBookVo.getSublocationId() != 0) {
				phoneBookEntity.setSublocationId(phoneBookVo.getSublocationId());
			}
			if (phoneBookVo.getMobileNumberC() != null) {
				phoneBookEntity.setMobileNumberC(phoneBookVo.getMobileNumberC());
			}
			if (phoneBookVo.getExtensionNumber() != null) {
				phoneBookEntity.setExtensionNumber(phoneBookVo.getExtensionNumber());
			}
			if (phoneBookVo.getEmailId() != null) {
				phoneBookEntity.setEmailId(phoneBookVo.getEmailId());
			}
			if (phoneBookVo.getMobileNumberP() != null) {
				phoneBookEntity.setMobileNumberP(phoneBookVo.getMobileNumberP());
			}
			if (phoneBookVo.getSkypeId() != null) {
				phoneBookEntity.setSkypeId(phoneBookVo.getSkypeId());
			}

			if (phoneBookVo.getPhoneNumber() != null) {
				phoneBookEntity.setPhoneNumber(phoneBookVo.getPhoneNumber());
			}

			phoneBookEntity.setPhoneBookIsActive(phoneBookVo.getPhoneBookIsActive());

			phoneBookEntity = setUserUpdateDetails(phoneBookEntity,authDetailsVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("copyFailure",authDetailsVo));
		}

		// Update the phone book details
		try {

			phoneBookRepo.save(phoneBookEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("modifyFailure",authDetailsVo));
		}

	}

	/**
	 * Method used to create User Updated details
	 * 
	 * @param phoneBookEntity
	 * @return
	 */
	private PhoneBookEntity setUserUpdateDetails(PhoneBookEntity phoneBookEntity,AuthDetailsVo  authDetailsVo) {

		phoneBookEntity.setUpdateBy(authDetailsVo.getUserId());
		phoneBookEntity.setUpdateDate(DateUtil.getCalenderDate());

		return phoneBookEntity;
	}

	/**
	 * 
	 * method used for delete Phone Book Details
	 * 
	 * @param phoneBookVo
	 * @return
	 */

	@Transactional
	public void delete(PhoneBookVO phoneBookVo,AuthDetailsVo authDetailsVo) {

		for (int id : phoneBookVo.getDeleteItem()) {

			PhoneBookEntity phoneBookEntity = null;

			// Get the phone book details using ID
			try {

				phoneBookEntity = phoneBookRepo.findOne(id);

			} catch (NoResultException e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("noResultFound",authDetailsVo));
			} catch (NonUniqueResultException e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("noRecordFound",authDetailsVo));
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("dataFailure",authDetailsVo));
			}

			// Delete the phone book details
			try {

				phoneBookEntity.setDeleteFlag(CommonConstant.FLAG_ONE);
				phoneBookEntity = setUserUpdateDetails(phoneBookEntity,authDetailsVo);

				phoneBookRepo.save(phoneBookEntity);

			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("dataFailure",authDetailsVo));
			}

		}

	}

	/**
	 * Method used to create attchment
	 * 
	 * @param phoneBookVo
	 * @param uploadingFiles
	 */
	@Transactional
	public void saveAttachment(PhoneBookVO phoneBookVo, MultipartFile[] uploadingFiles,AuthDetailsVo authDetailsVo) {

		PhoneBookEntity phoneBookEntity = new PhoneBookEntity();

		// Copy the phone book details from VO to Entity
		try {

			BeanUtils.copyProperties(phoneBookVo, phoneBookEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("copyFailure",authDetailsVo));
		}

		if (uploadingFiles != null) {
			phoneBookEntity = saveAttachment(phoneBookEntity, uploadingFiles,authDetailsVo);
		}
		phoneBookEntity.setCreateBy(authDetailsVo.getUserId());
		phoneBookEntity.setCreateDate(CommonConstant.getCalenderDate());
		phoneBookEntity.setUpdateBy(authDetailsVo.getUserId());
		phoneBookEntity.setUpdateDate(CommonConstant.getCalenderDate());
		phoneBookEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
		phoneBookEntity.setEntityLicenseId(authDetailsVo.getEntityId());

		// Create the new phone book details
		try {

			phoneBookRepo.save(phoneBookEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}

	/**
	 * Method is used fore save the attachment
	 * 
	 * @param phoneBookEntity
	 * @param uploadingFiles
	 * @return
	 */
	@Transactional
	public PhoneBookEntity saveAttachment(PhoneBookEntity phoneBookEntity, MultipartFile[] uploadingFiles,AuthDetailsVo authDetailsVo) {

		try {
			for (MultipartFile uploadedFile : uploadingFiles) {

				String fileName = dateAppend(uploadedFile);
				String path = picturePath.getPhoneBookFilePath();
				File fileToCreate = new File(path);
				if (fileToCreate.exists()) {
					path = path + CommonConstant.SLASH;
					if (!fileToCreate.exists()) {
						fileToCreate.mkdir();
					}
				} else {
					fileToCreate.mkdir();
					path = path + CommonConstant.SLASH;
					fileToCreate = new File(path);
					fileToCreate.mkdir();
				}
				fileToCreate = new File(path + fileName);

				uploadedFile.transferTo(fileToCreate);
				path = path + fileName;
				phoneBookEntity.setPhoneBookProfile(path);

			}
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		return phoneBookEntity;
	}

	/**
	 * Method used to append Date for Uploaded File
	 * 
	 * @param uploadedFile
	 * @return
	 */
	public String dateAppend(MultipartFile uploadedFile) {

		String fileName = uploadedFile.getOriginalFilename().substring(0,
				uploadedFile.getOriginalFilename().lastIndexOf("."));

		String date = CommonConstant.formatDatetoString(new Date(), CommonConstant.FILE_NAME_FORMAT_DATE);

		fileName = fileName + date;

		String format = "." + getfileFormat(uploadedFile.getOriginalFilename());

		fileName = fileName + format;

		return fileName;

	}

	/**
	 * Method used to Get the File Format
	 * 
	 * @param attachmentFileName
	 * @return
	 */
	public static String getfileFormat(String attachmentFileName) {

		try {

			return attachmentFileName.substring(attachmentFileName.lastIndexOf(".") + 1);

		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Method is used to update the attachment
	 * 
	 * @param phoneBookVo
	 * @param uploadingFiles
	 * @param authDetailsVo 
	 */
	@Transactional
	public void updateAttachment(PhoneBookVO phoneBookVo, MultipartFile[] uploadingFiles, AuthDetailsVo authDetailsVo) {

		PhoneBookEntity phoneBookEntity = new PhoneBookEntity();

		// Get the phone book details from DB using ID
		try {

			phoneBookEntity = phoneBookRepo.findOne(phoneBookVo.getPhoneBookId());

		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noResultFound",authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noRecordFound",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

		phoneBookEntity.setEmployeeId(phoneBookVo.getEmployeeId());
		phoneBookEntity.setEmployeeName(phoneBookVo.getEmployeeName());
		phoneBookEntity.setMobileNumberC(phoneBookVo.getMobileNumberC());
		phoneBookEntity.setMobileNumberP(phoneBookVo.getMobileNumberP());
		phoneBookEntity.setPhoneNumber(phoneBookVo.getPhoneNumber());
		phoneBookEntity.setExtensionNumber(phoneBookVo.getExtensionNumber());
		phoneBookEntity.setEmailId(phoneBookVo.getEmailId());
		phoneBookEntity.setSkypeId(phoneBookVo.getSkypeId());
		phoneBookEntity.setUserLocationId(phoneBookVo.getUserLocationId());
		phoneBookEntity.setSublocationId(phoneBookVo.getSublocationId());
		phoneBookEntity.setUserDepartmentId(phoneBookVo.getUserDepartmentId());
		phoneBookEntity.setPhoneBookIsActive(phoneBookVo.getPhoneBookIsActive());

		if (uploadingFiles != null) {
			phoneBookEntity = saveAttachment(phoneBookEntity, uploadingFiles,authDetailsVo);
		}

		phoneBookEntity.setEntityLicenseId(authDetailsVo.getEntityId());
		// Update the Phone book details
		try {

			phoneBookRepo.save(phoneBookEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}

	/**
	 * Method is used to down load the attachment
	 * 
	 * @param phoneBookVo
	 * @return phoneBookVoList
	 */
	public PhoneBookVO attachmentDownload(PhoneBookVO phoneBookVo,AuthDetailsVo authDetailsVo) {

		PhoneBookVO phoneBookVoList = new PhoneBookVO();

		PhoneBookEntity phoneBookEntity = null;

		// Get the phone book details from DB using ID
		try {
			Integer entityId = authDetailsVo.getEntityId();
			Integer id = phoneBookVo.getPhoneBookId();
			phoneBookEntity = phoneBookRepo.attachmentDownload(entityId, id);

		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noResultFound",authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noRecordFound",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

		// Copy the data from entity to VO
		try {

			BeanUtils.copyProperties(phoneBookEntity, phoneBookVoList);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("copyFailure",authDetailsVo));
		}
		return phoneBookVoList;

	}

	/**
	 * Method is used to get all the search using first letter
	 * 
	 * @param phoneBookVo
	 * @return
	 */
	@Transactional
	public List<PhoneBookVO> getAllFirstSearch(PhoneBookVO phoneBookVo,AuthDetailsVo authDetailsVo) {

		List<Object[]> phoneBookEntity = null;

		List<PhoneBookVO> phoneBookVoList = null;

		// Get the phone book details from DB using the search conditions
		try {

			phoneBookEntity = phoneBookDao.getAllFirstSearch(phoneBookVo,authDetailsVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

		// Set all the phone book details in VO
		try {

			if (phoneBookEntity != null && phoneBookEntity.size() > 0)
				phoneBookVoList = getAllSearchList(phoneBookEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("copyFailure",authDetailsVo));
		}

		return phoneBookVoList;
	}

	
}
