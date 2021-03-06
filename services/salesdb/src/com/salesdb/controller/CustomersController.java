/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the source code license agreement you entered into with wavemaker-com*/
package com.salesdb.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/
import com.salesdb.service.CustomersService;
import com.salesdb.service.LeadsService;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.TypeMismatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.util.WMMultipartUtils;
import com.wavemaker.runtime.util.WMRuntimeUtils;
import com.wavemaker.runtime.file.model.DownloadResponse;
import com.wordnik.swagger.annotations.*;
import com.salesdb.*;
import com.salesdb.service.*;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;

/**
 * Controller object for domain model class Customers.
 * @see com.salesdb.Customers
 */
@RestController(value = "Salesdb.CustomersController")
@RequestMapping("/salesdb/Customers")
@Api(description = "Exposes APIs to work with Customers resource.", value = "CustomersController")
public class CustomersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomersController.class);

    @Autowired
    @Qualifier("salesdb.CustomersService")
    private CustomersService customersService;

    @Autowired
    @Qualifier("salesdb.LeadsService")
    private LeadsService leadsService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Returns the list of Customers instances matching the search criteria.")
    public Page<Customers> findCustomerss(Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Customerss list");
        return customersService.findAll(queryFilters, pageable);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the list of Customers instances.")
    public Page<Customers> getCustomerss(Pageable pageable) {
        LOGGER.debug("Rendering Customerss list");
        return customersService.findAll(pageable);
    }

    @RequestMapping(value = "/{id:.+}/leadses", method = RequestMethod.GET)
    @ApiOperation(value = "Gets the leadses instance associated with the given id.")
    public Page<Leads> findAssociatedleadses(Pageable pageable, @PathVariable("id") Integer id) {
        LOGGER.debug("Fetching all associated leadses");
        return leadsService.findAssociatedValues(id, "customers", "id", pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 * 
	 * @param service
	 */
    protected void setCustomersService(CustomersService service) {
        this.customersService = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Creates a new Customers instance.")
    public Customers createCustomers(@RequestBody Customers instance) {
        LOGGER.debug("Create Customers with information: {}", instance);
        instance = customersService.create(instance);
        LOGGER.debug("Created Customers with information: {}", instance);
        return instance;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Returns the total count of Customers instances.")
    public Long countAllCustomerss() {
        LOGGER.debug("counting Customerss");
        Long count = customersService.countAll();
        return count;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Returns the Customers instance associated with the given id.")
    public Customers getCustomers(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Customers with id: {}", id);
        Customers instance = customersService.findById(id);
        LOGGER.debug("Customers details with id: {}", instance);
        return instance;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Updates the Customers instance associated with the given id.")
    public Customers editCustomers(@PathVariable(value = "id") Integer id, @RequestBody Customers instance) throws EntityNotFoundException {
        LOGGER.debug("Editing Customers with id: {}", instance.getId());
        instance.setId(id);
        instance = customersService.update(instance);
        LOGGER.debug("Customers details with id: {}", instance);
        return instance;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Deletes the Customers instance associated with the given id.")
    public boolean deleteCustomers(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Customers with id: {}", id);
        Customers deleted = customersService.delete(id);
        return deleted != null;
    }
}
