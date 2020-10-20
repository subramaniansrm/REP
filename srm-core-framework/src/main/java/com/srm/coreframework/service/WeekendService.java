package com.srm.coreframework.service;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.WeekendDAO;
import com.srm.coreframework.entity.DaysEntity;
import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.entity.WeekendDetailsEntity;
import com.srm.coreframework.entity.WeekendEntity;
import com.srm.coreframework.repository.SubLocationRepository;
import com.srm.coreframework.repository.WeekendDetailsRepository;
import com.srm.coreframework.repository.WeekendRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.DaysVO;
import com.srm.coreframework.vo.UserRoleVO;
import com.srm.coreframework.vo.WeekendVO;

@Component
public class WeekendService extends CommonController<UserRoleVO>{
	
	org.slf4j.Logger logger = LoggerFactory.getLogger(WeekendService.class);
	
	@Autowired 
	WeekendDetailsRepository weekendDetailsRepository;
	
	@Autowired
	SubLocationRepository subLocationRepository;
	
	@Autowired 
	WeekendRepository weekendRepository;
	
	@Autowired 
	WeekendDAO weekendDAO;
	
	@Transactional
	public void saveWeedend(WeekendVO weekendVO, AuthDetailsVo authDetailsVo)
			throws IllegalAccessException, InvocationTargetException , CommonException{
		 
		try {
			if (null != weekendVO) {

				WeekendEntity weekendEntity = saveWeekEndMaster(weekendVO,authDetailsVo);

				saveWeeendMasterDetail(weekendVO,weekendEntity,authDetailsVo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("dataFailure");
		}

	}
	
	public WeekendEntity saveWeekEndMaster(WeekendVO weekendVO, AuthDetailsVo authDetailsVo ) throws CommonException {
		WeekendEntity weekendEntity = null;

		try {
			weekendEntity = new WeekendEntity();
			BeanUtils.copyProperties(weekendVO, weekendEntity);
			
			weekendEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
			weekendEntity.setEntityId(authDetailsVo.getEntityId());
			weekendEntity.setActive(CommonConstant.FLAG_ONE);
						
			UserLocation locEntity = new UserLocation();
			locEntity.setId(weekendVO.getLocationId());
			weekendEntity.setUserLocationEntity(locEntity);
			
			weekendEntity.setCreateBy(authDetailsVo.getUserId());
			weekendEntity.setCreateDate(CommonConstant.getCalenderDate());
			weekendEntity.setUpdateBy(authDetailsVo.getUserId());
			weekendEntity.setUpdateDate(CommonConstant.getCalenderDate());
			
			weekendRepository.save(weekendEntity);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("dataFailure");
		}
		return weekendEntity;
	}
					
	public void saveWeeendMasterDetail(WeekendVO weekendVO, WeekendEntity weekendEntity, 
			AuthDetailsVo authDetailsVo) {
		
		WeekendDetailsEntity weekendDetailEntity = null;

		try {

			if ( null !=  weekendVO.getWeekendDetailArr() && weekendVO.getWeekendDetailArr().length > 0) {

				Integer[] weekendDayArr = weekendVO.getWeekendDetailArr();

				for (int i = 0; i < weekendDayArr.length; i++) {

					weekendDetailEntity = new WeekendDetailsEntity();
					
					UserLocation locEntity = new UserLocation();
					locEntity.setId(weekendVO.getLocationId());
					weekendDetailEntity.setUserLocationEntity(locEntity);
										
					if(null != weekendVO.getSubLocationId()){
						weekendDetailEntity.setSublocationId(weekendVO.getSubLocationId());
					}
					weekendDetailEntity.setWeekendId(weekendEntity.getWeekendId());
					weekendDetailEntity.setCreateBy(authDetailsVo.getUserId());
					weekendDetailEntity.setCreateDate(CommonConstant.getCalenderDate());
					weekendDetailEntity.setUpdateBy(authDetailsVo.getUserId());
					weekendDetailEntity.setUpdateDate(CommonConstant.getCalenderDate());
					weekendDetailEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
					weekendDetailEntity.setActive(CommonConstant.FLAG_ONE);
					weekendDetailEntity.setEntityId(authDetailsVo.getEntityId());
					weekendDetailEntity.setWeekendDay(weekendDayArr[i]);

					weekendDetailsRepository.save(weekendDetailEntity);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("dataFailure");
		}
	}
		
	public List<WeekendVO> listWeekend(AuthDetailsVo authDetailsVo) {

		List<Object[]> weekendList = new ArrayList<Object[]>();
		
		List<WeekendVO> WeekendVoList = new ArrayList<WeekendVO>();

		try{
			weekendList = weekendDAO.getAll(authDetailsVo);

		for (Object[] weekendEntity : weekendList) {
			WeekendVO weekendVO = new WeekendVO();		
			
			if (0 != (int) ((Object[]) weekendEntity)[0]) {
				weekendVO.setWeekendId((int) ((Object[]) weekendEntity)[0]);
			}
			if (null != (String) ((Object[]) weekendEntity)[1]) {
				weekendVO.setLocation((String) ((Object[]) weekendEntity)[1]);
			}
			
			if (null != (String) ((Object[]) weekendEntity)[2]) {
				weekendVO.setSubLocation((String) ((Object[]) weekendEntity)[2]);
			}
			
			if (null != (String) ((Object[]) weekendEntity)[3]) {
				weekendVO.setWeekendDays((String) ((Object[]) weekendEntity)[3]);
			}
			 
				if (null != (Character) ((Object[]) weekendEntity)[4]) {
					Character active = (Character) ((Object[]) weekendEntity)[4];

					if (active == '1') {
						weekendVO.setActiveStatus(CommonConstant.Active);
					} else {
						weekendVO.setActiveStatus(CommonConstant.InActive);
					}
				}

			WeekendVoList.add(weekendVO);
		}

		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("dataFailure");
		}
		return WeekendVoList;

	}	
	
	public void deleteWeekend(WeekendVO weekendVO, AuthDetailsVo authDetailsVo) {
		WeekendEntity weekendEntity = null;

		try {
			weekendEntity = new WeekendEntity();
			// fetch record

			Integer[] deleteItems = weekendVO.getDeleteItem();

			for (int i = 0; i < deleteItems.length; i++) {
				WeekendEntity weekendEn = weekendRepository.findOne(deleteItems[i]);

				weekendEn.setDeleteFlag(CommonConstant.FLAG_ONE);
				weekendEn.setCreateBy(weekendEn.getCreateBy());
				weekendEn.setCreateDate(weekendEn.getCreateDate());
				weekendEn.setUpdateBy(authDetailsVo.getUserId());
				weekendEn.setUpdateDate(CommonConstant.getCalenderDate());

				weekendRepository.save(weekendEntity);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("dataFailure");
		}
	}
		
	public WeekendVO viewWeekend(WeekendVO weekendVO, AuthDetailsVo authDetailsVo) {
		WeekendEntity weekendEntity = null;

		try {
			weekendEntity = new WeekendEntity();
			weekendEntity = weekendDAO.weekendView(authDetailsVo,weekendVO);

			if (null != weekendEntity.getUserLocationEntity().getId()) {
				weekendVO.setLocationId(weekendEntity.getUserLocationEntity().getId());
			}

			if (null != weekendEntity.getUserLocationEntity().getUserLocationName()) {
				weekendVO.setLocation(weekendEntity.getUserLocationEntity().getUserLocationName());
			}

			if (weekendEntity.getActive().equals('1')) {
				weekendVO.setActiveStatus(CommonConstant.Active);
			} else {
				weekendVO.setActiveStatus(CommonConstant.InActive);
			}

			weekendVO.setWeekendId(weekendEntity.getWeekendId());

			weekendVO.setEntityId(weekendEntity.getEntityId());

			List<WeekendDetailsEntity> weekendDetailsEntityList = 
					weekendDAO.getWeekendDetailByWeekendId(weekendVO,authDetailsVo);						 				
						 		
			List<Integer> arrlist = new ArrayList<Integer>();
			
			for (WeekendDetailsEntity weekendDetailsEntity : weekendDetailsEntityList) {
			
				if(null != weekendDetailsEntity.getSublocationId()){
					SubLocation subLocation = subLocationRepository.findOne(weekendDetailsEntity.getSublocationId());
					if(null != subLocation.getSubLocationName()){
						weekendVO.setSubLocation(subLocation.getSubLocationName());
						weekendVO.setSubLocationId(subLocation.getSublocationId());
					}
				}
				arrlist.add(weekendDetailsEntity.getWeekendDay());										 	 											
			}
			
			Integer[] weekendDetailArr = arrlist.toArray(new Integer[arrlist.size()]);  			
			weekendVO.setWeekendDetailArr(weekendDetailArr); 

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("dataFailure");
		}
		return weekendVO;
	}
				
	public List<WeekendVO> search(WeekendVO weekend, AuthDetailsVo authDetailsVo) {
		List<WeekendVO> WeekendVoList = new ArrayList<WeekendVO>();
		List<Object[]> weekendList = new ArrayList<Object[]>();
		try {
			weekendList = weekendDAO.search(weekend, authDetailsVo);

			for (Object[] weekendEntity : weekendList) {
				WeekendVO weekendVO = new WeekendVO();

				if (0 != (int) ((Object[]) weekendEntity)[0]) {
					weekendVO.setWeekendId((int) ((Object[]) weekendEntity)[0]);
				}
				if (null != (String) ((Object[]) weekendEntity)[1]) {
					weekendVO.setLocation((String) ((Object[]) weekendEntity)[1]);
				}

				if (null != (String) ((Object[]) weekendEntity)[2]) {
					weekendVO.setSubLocation((String) ((Object[]) weekendEntity)[2]);
				}

				if (null != (String) ((Object[]) weekendEntity)[3]) {
					weekendVO.setWeekendDays((String) ((Object[]) weekendEntity)[3]);
				}

				if (null != (Character) ((Object[]) weekendEntity)[4]) {
					Character active = (Character) ((Object[]) weekendEntity)[4];

					if (active == '1') {
						weekendVO.setActiveStatus(CommonConstant.Active);
					} else {
						weekendVO.setActiveStatus(CommonConstant.InActive);
					}
				}

				WeekendVoList.add(weekendVO);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("dataFailure");
		}

		return WeekendVoList;
	}
	
	@Transactional
	public void updateWeekend(WeekendVO weekendVO, AuthDetailsVo authDetailsVo) {

		WeekendDetailsEntity weekendDetailEntity = new WeekendDetailsEntity();

		try {

			// inactive all old records
			weekendDAO.updateWeekend(weekendVO, authDetailsVo);

			// add new records
			if (null != weekendVO.getWeekendDetailArr()) {

				Integer[] weekendDayArr = weekendVO.getWeekendDetailArr();

				for (int i = 0; i < weekendDayArr.length; i++) {

					weekendDetailEntity = new WeekendDetailsEntity();

					UserLocation locEntity = new UserLocation();
					locEntity.setId(weekendVO.getLocationId());
					weekendDetailEntity.setUserLocationEntity(locEntity);

					if(null != weekendVO.getSubLocationId()){
						weekendDetailEntity.setSublocationId(weekendVO.getSubLocationId());
					}
					
					weekendDetailEntity.setWeekendId(weekendVO.getWeekendId());
					weekendDetailEntity.setCreateBy(authDetailsVo.getUserId());
					weekendDetailEntity.setCreateDate(CommonConstant.getCalenderDate());
					weekendDetailEntity.setUpdateBy(authDetailsVo.getUserId());
					weekendDetailEntity.setUpdateDate(CommonConstant.getCalenderDate());
					weekendDetailEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
					weekendDetailEntity.setActive(CommonConstant.FLAG_ONE);
					weekendDetailEntity.setEntityId(authDetailsVo.getEntityId());
					weekendDetailEntity.setWeekendDay(weekendDayArr[i]);

					weekendDetailsRepository.save(weekendDetailEntity);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("dataFailure");
		}
	}
	
	public List<DaysVO> listDays(AuthDetailsVo authDetailsVo) {

		List<DaysVO> daysVoList = new ArrayList<DaysVO>();

		DaysVO dayVo = null;

		try {
			@SuppressWarnings("unused")
			List<DaysEntity> daysEntityList = new ArrayList<DaysEntity>();

			daysEntityList = weekendDAO.listDays(authDetailsVo);

			for (DaysEntity dayEntity : daysEntityList) {
				dayVo = new DaysVO();
				if (authDetailsVo.getLangCode().equals(CommonConstant.en)) {

					dayVo.setDayName(dayEntity.getDayNameEn());
					dayVo.setDayNumber(dayEntity.getDayNumber());
				} else {
					dayVo.setDayName(dayEntity.getDayNameJp());
					dayVo.setDayNumber(dayEntity.getDayNumber());
				}
				daysVoList.add(dayVo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("dataFailure");
		}

		return daysVoList;

	}
		
	public void duplicateValidation(AuthDetailsVo authDetailsVo , WeekendVO weekendVO) throws CommonException{
				
		try {

			List<Object[]> weekendList = new ArrayList<Object[]>();
			weekendList = weekendDAO.duplicateValidation(authDetailsVo, weekendVO);

			if (null != weekendList && weekendList.size() > 0) {
				throw new CommonException("weekendDuplicateMessage");
			}

		} catch (CommonException e) {
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage());
		}
	}
	
	
 
}
