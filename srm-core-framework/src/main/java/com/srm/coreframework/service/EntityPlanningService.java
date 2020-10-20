package com.srm.coreframework.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.transaction.Transactional;

import org.jfree.util.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.PicturePath;
import com.srm.coreframework.dao.EntityPlanningDao;
import com.srm.coreframework.entity.CurrencyEntity;
import com.srm.coreframework.entity.EntityPlanning;
import com.srm.coreframework.entity.PhoneBookEntity;
import com.srm.coreframework.repository.EntityPlanningRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EntityPlanningVo;


@Service
public class EntityPlanningService extends CommonService{

	@Autowired
	EntityPlanningDao entityPlanningDao;
	
	@Autowired
	EntityPlanningRepository entityPlanningRepository;
	
	@Autowired
	PicturePath picturePath;
	
	@Autowired
	PhoneBookService phoneBookService;
	
	public List<EntityPlanningVo> getEntityPlanningList(){
		
		List<EntityPlanningVo> list = new ArrayList<EntityPlanningVo>();
		EntityPlanningVo entityPlanningVo = null;
		List<EntityPlanning> entityList = entityPlanningRepository.getEntityPlanningList();
		for(EntityPlanning entityPlanning : entityList){
			entityPlanningVo = new EntityPlanningVo();
			BeanUtils.copyProperties(entityPlanning, entityPlanningVo);
						
			if (null != entityPlanning.getOfferAmount()) {
				entityPlanningVo.setOfferAmount(entityPlanning.getOfferAmount());
			}
			
			if (null != entityPlanning.getOfferRemarks()) {
				entityPlanningVo.setOfferRemarks(entityPlanning.getOfferRemarks());
			}
			
			if(null != entityPlanning.getPlanImage() && !entityPlanning.getPlanImage().isEmpty()){
				try {
					entityPlanningVo.setImageLoad(defaultImage(entityPlanning.getPlanImage()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else{
				try {
					entityPlanningVo.setImageLoad(defaultImage(picturePath.getDefaultPlanImagePath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(null != entityPlanning.getCurrencyEntity() && null != entityPlanning.getCurrencyEntity().getSymbol()){
				entityPlanningVo.setSymbol(entityPlanning.getCurrencyEntity().getSymbol());
			}
		
			list.add(entityPlanningVo);
		}
		
		
		return list;
	}

	public EntityPlanningVo getEntityPlanningDetails(Integer planId) {

		EntityPlanningVo entityPlanningVo = new EntityPlanningVo();
		EntityPlanning entityPlanning = entityPlanningRepository.getEntityPlanningDetails(planId);

		if (null != entityPlanning) {
			if (!entityPlanning.getPlanName().equals("Custom")) {
				BeanUtils.copyProperties(entityPlanning, entityPlanningVo);

				if (null != entityPlanningVo.getDuration()) {
					entityPlanningVo.setFromDate(CommonConstant.getCalenderDate());
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MONTH, entityPlanningVo.getDuration());
					entityPlanningVo.setToDate(calendar.getTime());
				}
			}
		}

		return entityPlanningVo;

	}

	@Transactional
	public List<EntityPlanningVo> list(AuthDetailsVo authDetailsVo) {
		List<EntityPlanningVo> entityPlanningList = new ArrayList<EntityPlanningVo>();
		EntityPlanningVo entityPlanningVo = null;
		try {
			List<EntityPlanning> list = entityPlanningRepository.list();
			
			for(EntityPlanning entityPlanning : list){
				entityPlanningVo = new EntityPlanningVo();
				BeanUtils.copyProperties(entityPlanning, entityPlanningVo);
				if(null != entityPlanning.getCurrencyEntity() && null != entityPlanning.getCurrencyEntity().getCurrencyName()){
					entityPlanningVo.setCurrencyName(entityPlanning.getCurrencyEntity().getCurrencyName());
				}
				
				if(entityPlanning.getActiveFlag().equals(CommonConstant.FLAG_ONE)){
					entityPlanningVo.setActiveFlag(true);
				}else{
					entityPlanningVo.setActiveFlag(false);
				}
				
				entityPlanningList.add(entityPlanningVo);
			}
			
		} catch (Exception e) {
			Log.info("Entity Planning Service list Common Exception", e);
			e.printStackTrace();
			throw new CommonException(getMessage("dataFailure", authDetailsVo));
		}
		
		
		return entityPlanningList;
	}
	
	@Transactional
	public void save(EntityPlanningVo entityPlanningVo , AuthDetailsVo authDetailsVo,MultipartFile[] uploadingFiles) {
		
		EntityPlanning  entityPlanning = new EntityPlanning();
		try {
			BeanUtils.copyProperties(entityPlanningVo, entityPlanning);
			if(entityPlanningVo.isActiveFlag()){
				entityPlanning.setActiveFlag(CommonConstant.FLAG_ONE);
			}else{
				entityPlanning.setActiveFlag(CommonConstant.FLAG_ZERO);
			}
			entityPlanning.setDeleteFlag(CommonConstant.FLAG_ZERO);
			entityPlanning.setCreatedBy(authDetailsVo.getUserId());
			entityPlanning.setCreatedDate(CommonConstant.getCalenderDate());
			entityPlanning.setUpdatedBy(authDetailsVo.getUserId());
			entityPlanning.setUpdatedDate(CommonConstant.getCalenderDate());
			
			if(null!= entityPlanningVo.getOfferRemarks()){
			entityPlanning.setOfferRemarks(entityPlanningVo.getOfferRemarks());
			}
			
			if(null != entityPlanningVo.getCurrencyId()){
				CurrencyEntity currencyEntity = new CurrencyEntity();
				currencyEntity.setCurrencyId(entityPlanningVo.getCurrencyId());
				entityPlanning.setCurrencyEntity(currencyEntity);
			}
			
		
			try {
				for (MultipartFile uploadedFile : uploadingFiles) {

					String fileName = phoneBookService.dateAppend(uploadedFile);
					String path = picturePath.getPlanImageFilePath();
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
					entityPlanning.setPlanImage(path);

				}
			} catch (JsonParseException e) {
				throw new CommonException(getMessage("dataFailure",authDetailsVo));
			} catch (JsonMappingException e) {
				throw new CommonException(getMessage("dataFailure",authDetailsVo));
			} catch (IOException e) {
				throw new CommonException(getMessage("dataFailure",authDetailsVo));
			} catch (Exception e) {
				throw new CommonException(getMessage("dataFailure",authDetailsVo));
			}
				
			entityPlanningRepository.save(entityPlanning);
			
		} catch (Exception e) {
			Log.info("Entity Planning Service save  Exception", e);
			e.printStackTrace();
			throw new CommonException(getMessage("dataFailure", authDetailsVo));
		}
	}
	

	@Transactional
	public void update(EntityPlanningVo entityPlanningVo, AuthDetailsVo authDetailsVo,MultipartFile[] uploadingFiles) {

		EntityPlanning entityPlanning = null;
		if (null != entityPlanningVo.getPlanId()) {
			entityPlanning = entityPlanningRepository.getEntityPlanningDetails(entityPlanningVo.getPlanId());
		}

		try {
			if (null != entityPlanning) {
				BeanUtils.copyProperties(entityPlanningVo, entityPlanning);
				if (entityPlanningVo.isActiveFlag()) {
					entityPlanning.setActiveFlag(CommonConstant.FLAG_ONE);
				} else {
					entityPlanning.setActiveFlag(CommonConstant.FLAG_ZERO);
				}
				entityPlanning.setDeleteFlag(CommonConstant.FLAG_ZERO);
				entityPlanning.setUpdatedBy(authDetailsVo.getUserId());
				entityPlanning.setUpdatedDate(CommonConstant.getCalenderDate());
				if(null != entityPlanningVo.getCurrencyId()){
					CurrencyEntity currencyEntity = new CurrencyEntity();
					currencyEntity.setCurrencyId(entityPlanningVo.getCurrencyId());
					entityPlanning.setCurrencyEntity(currencyEntity);
				}
				
				if (null != entityPlanningVo.getOfferRemarks()) {
					entityPlanning.setOfferRemarks(entityPlanningVo.getOfferRemarks());
				}
				
				try {
					for (MultipartFile uploadedFile : uploadingFiles) {

						String fileName = phoneBookService.dateAppend(uploadedFile);
						String path = picturePath.getPlanImageFilePath();
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
						entityPlanning.setPlanImage(path);

					}
				} catch (JsonParseException e) {
					throw new CommonException(getMessage("dataFailure",authDetailsVo));
				} catch (JsonMappingException e) {
					throw new CommonException(getMessage("dataFailure",authDetailsVo));
				} catch (IOException e) {
					throw new CommonException(getMessage("dataFailure",authDetailsVo));
				} catch (Exception e) {
					throw new CommonException(getMessage("dataFailure",authDetailsVo));
				}
				
				entityPlanningRepository.save(entityPlanning);
			}
		} catch (Exception e) {
			Log.info("Entity Planning Service update  Exception", e);
			e.printStackTrace();
			throw new CommonException(getMessage("dataFailure", authDetailsVo));
		}
	}
	
	
	@Transactional
	public EntityPlanningVo view(EntityPlanningVo entityPlanningVo, AuthDetailsVo authDetailsVo) {

		EntityPlanningVo entityPlanning = new EntityPlanningVo();

		if (null != entityPlanningVo.getPlanId()) {
			EntityPlanning entityPlanningenity = entityPlanningRepository
					.getEntityPlanningDetails(entityPlanningVo.getPlanId());
			BeanUtils.copyProperties(entityPlanningenity, entityPlanning);
			if (null != entityPlanningenity && null != entityPlanningenity.getActiveFlag()
					&& null != entityPlanningenity.getActiveFlag()
					&& entityPlanningenity.getActiveFlag().equals(CommonConstant.FLAG_ONE)) {
				entityPlanning.setActiveFlag(true);
			} else {
				entityPlanning.setActiveFlag(false);
			}
			if(null != entityPlanningenity.getCurrencyEntity() && null != entityPlanningenity.getCurrencyEntity().getCurrencyId()){
				entityPlanning.setCurrencyId(entityPlanningenity.getCurrencyEntity().getCurrencyId());
			}
			
			if (null != entityPlanningenity.getOfferRemarks()) {
				entityPlanning.setOfferRemarks(entityPlanningenity.getOfferRemarks());
			}
			
			if(null != entityPlanningenity.getPlanImage() && !entityPlanningenity.getPlanImage().isEmpty()){
				try {
					entityPlanning.setImageLoad(defaultImage(entityPlanningenity.getPlanImage()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else {
				try {
					entityPlanning.setImageLoad(defaultImage(picturePath.getDefaultPlanImagePath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return entityPlanning;
	}
	
	
	@Transactional
	public void delete(EntityPlanningVo entityPlanningVo, AuthDetailsVo authDetailsVo) {

		try {
			Integer[] deleteItems = entityPlanningVo.getDeleteItem();
			for (int i = 0; i < deleteItems.length; i++) {

				EntityPlanning entityPlanning = entityPlanningRepository.findOne(deleteItems[i]);

				entityPlanning.setDeleteFlag(CommonConstant.FLAG_ONE);
				entityPlanning.setUpdatedBy(authDetailsVo.getUserId());
				entityPlanning.setUpdatedDate(CommonConstant.getCalenderDate());
				entityPlanningRepository.save(entityPlanning);
			}
		} catch (NoResultException e) {
			Log.info("Entity Planning Service delete NoResultException", e);
			throw new CommonException(getMessage("noResultFound", authDetailsVo));
		} catch (NonUniqueResultException e) {
			Log.info("Entity Planning Service delete NonUniqueResultException", e);
			throw new CommonException(getMessage("noRecordFound", authDetailsVo));
		} catch (Exception e) {
			Log.info("Entity Planning Service delete Exception", e);
			throw new CommonException(getMessage("dataFailure", authDetailsVo));
		}

	}
	
	@Transactional
	public List<EntityPlanningVo> search(EntityPlanningVo entityPlanningVo,AuthDetailsVo authDetailsVo) {

		List<EntityPlanningVo> list = new ArrayList<EntityPlanningVo>();

		EntityPlanningVo entityPlanningVo2 = null;
		

		try {
			List<EntityPlanning> result =  entityPlanningDao.search(entityPlanningVo,authDetailsVo);

			for (EntityPlanning entityPlanning : result) {

				entityPlanningVo2 = new EntityPlanningVo();
				BeanUtils.copyProperties(entityPlanning, entityPlanningVo2);
				
				if(null != entityPlanning.getCurrencyEntity() && null != entityPlanning.getCurrencyEntity().getCurrencyName()){
					entityPlanningVo2.setCurrencyName(entityPlanning.getCurrencyEntity().getCurrencyName());
				}
				
				if(null != entityPlanning && null != entityPlanning.getActiveFlag()
						&& entityPlanning.getActiveFlag().equals(CommonConstant.FLAG_ONE)){
					entityPlanningVo2.setActiveFlag(true);
				}else{
					entityPlanningVo2.setActiveFlag(false);
				}
				
				list.add(entityPlanningVo2);
			}
		} catch (Exception e) {
			Log.info("Entity Planning Service search Exception", e);
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		
		return list;

	}
	
	public EntityPlanningVo defatultImageLoad() {
		EntityPlanningVo entityPlanningVo = new EntityPlanningVo();

		try {
			entityPlanningVo.setImageLoad(defaultImage(picturePath.getDefaultPlanImagePath()));
		} catch (IOException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entityPlanningVo;
	}
	
	public List<EntityPlanningVo> entityPlanningDropDown() {

		List<EntityPlanningVo> list = new ArrayList<EntityPlanningVo>();
		EntityPlanningVo entityPlanningVo = null;
		List<EntityPlanning> entityList = entityPlanningRepository.getEntityPlanningList();

		for (EntityPlanning entityPlanning : entityList) {
			entityPlanningVo = new EntityPlanningVo();

			if (null != entityPlanning.getPlanId()) {
				entityPlanningVo.setPlanId(entityPlanning.getPlanId());
			}

			if (null != entityPlanning.getPlanName()) {
				entityPlanningVo.setPlanName(entityPlanning.getPlanName());
			}

			list.add(entityPlanningVo);
		}

		return list;
	}
	
	@Transactional
	public void updatePlanExpiry() {

		List<EntityPlanning> entityLicenseList = entityPlanningDao.getPlanList();

		for (EntityPlanning entityPlanning : entityLicenseList) {

			if (null != entityPlanning.getPlanId()) {
				entityPlanningDao.updatePlanStatusForExpiry(entityPlanning.getPlanId());
			}
		}

	}
	
	
}
