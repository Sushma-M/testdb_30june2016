/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the source code license agreement you entered into with wavemaker-com*/

package com.salesdb.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wavemaker.runtime.data.dao.*;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;

import com.salesdb.*;


/**
 * ServiceImpl object for domain model class FollowUps.
 * @see com.salesdb.FollowUps
 */
@Service("salesdb.FollowUpsService")
public class FollowUpsServiceImpl implements FollowUpsService {


    private static final Logger LOGGER = LoggerFactory.getLogger(FollowUpsServiceImpl.class);

    @Autowired
    @Qualifier("salesdb.FollowUpsDao")
    private WMGenericDao<FollowUps, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<FollowUps, Integer> wmGenericDao){
        this.wmGenericDao = wmGenericDao;
    }
     @Transactional(readOnly = true, value = "salesdbTransactionManager")
     public Page<FollowUps> findAssociatedValues(Object value, String entityName, String key,  Pageable pageable){
          LOGGER.debug("Fetching all associated");
          return this.wmGenericDao.getAssociatedObjects(value, entityName, key, pageable);
     }

    @Transactional(value = "salesdbTransactionManager")
    @Override
    public FollowUps create(FollowUps followups) {
        LOGGER.debug("Creating a new followups with information: {}" , followups);
        return this.wmGenericDao.create(followups);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "salesdbTransactionManager")
    @Override
    public FollowUps delete(Integer followupsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting followups with id: {}" , followupsId);
        FollowUps deleted = this.wmGenericDao.findById(followupsId);
        if (deleted == null) {
            LOGGER.debug("No followups found with id: {}" , followupsId);
            throw new EntityNotFoundException(String.valueOf(followupsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public Page<FollowUps> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all followupss");
        return this.wmGenericDao.search(queryFilters, pageable);
    }
    
    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public Page<FollowUps> findAll(Pageable pageable) {
        LOGGER.debug("Finding all followupss");
        return this.wmGenericDao.search(null, pageable);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public FollowUps findById(Integer id) throws EntityNotFoundException {
        LOGGER.debug("Finding followups by id: {}" , id);
        FollowUps followups=this.wmGenericDao.findById(id);
        if(followups==null){
            LOGGER.debug("No followups found with id: {}" , id);
            throw new EntityNotFoundException(String.valueOf(id));
        }
        return followups;
    }
    @Transactional(rollbackFor = EntityNotFoundException.class, value = "salesdbTransactionManager")
    @Override
    public FollowUps update(FollowUps updated) throws EntityNotFoundException {
        LOGGER.debug("Updating followups with information: {}" , updated);
        this.wmGenericDao.update(updated);

        Integer id = (Integer)updated.getId();

        return this.wmGenericDao.findById(id);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public long countAll() {
        return this.wmGenericDao.count();
    }
}


