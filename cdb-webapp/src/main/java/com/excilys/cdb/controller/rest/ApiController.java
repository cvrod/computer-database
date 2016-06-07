package com.excilys.cdb.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.model.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.ValidatorException;

@RestController
@RequestMapping("/rest")
public class ApiController {

    @Autowired
    @Qualifier("computerService")
    private ComputerService computerService;

    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;
    
    static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

	@RequestMapping(value = { "/computer/", "/computer" }, method = RequestMethod.GET)
    public ResponseEntity<List<ComputerDTO>> listAllComputers() {
		List<Computer> res = new ArrayList<>();
		List<ComputerDTO> resDTO = new ArrayList<>();
		res = computerService.listAll();
		for(Computer c : res) {
			resDTO.add(new ComputerDTO(c));
		}
		return new ResponseEntity<>(resDTO, HttpStatus.OK);
	}

	@RequestMapping(value = { "/company/", "/company" }, method = RequestMethod.GET)
    public ResponseEntity<List<Company>> listAllCompany() {
		List<Company> res = new ArrayList<>();
		res = companyService.listAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/computer/{id}", method = RequestMethod.GET)
	public ResponseEntity<Computer> getComputer(@PathVariable("id") int id){
		Computer res = computerService.get(id);
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/computer/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Computer> deleteComputer(@PathVariable("id") int id){
		computerService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = { "/computer/", "/computer" }, method = RequestMethod.POST)
	public ResponseEntity<Computer> addComputer(@RequestBody Computer c){
		try {
			ComputerValidator.validate(c);
		} catch (ValidatorException e) {
			LOGGER.debug("Computer is invalid !");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		computerService.add(c);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/computer/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Computer> updateComputer(@PathVariable("id") int id, @RequestBody Computer c){
		try {
			ComputerValidator.validate(c);
		} catch (ValidatorException e) {
			LOGGER.debug("Computer is invalid !");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		computerService.update(id, c);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
