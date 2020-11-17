package com.softech.fpt.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.softech.fpt.dtos.StaffDto;
import com.softech.fpt.models.Depart;
import com.softech.fpt.models.Staff;
import com.softech.fpt.services.StaffService;

@Controller
@RequestMapping("/staffs")
public class StaffController {
	@Autowired
	private StaffService staffService;
	
	@RequestMapping("/list")
	public String list(ModelMap modelMap) {
		modelMap.addAttribute("staffs", staffService.findAll());
	
		return "staffs/list";
	}
	
	@GetMapping("/add")
	public String add(ModelMap modelMap) {
		StaffDto staff = new StaffDto();
	
		modelMap.addAttribute("staffDto", staff);
		
		return "staffs/addOrEdit";
	}
	
	@PostMapping("/saveOrUpdate")
	public String saveOrUpdate(ModelMap modelMap,@Validated StaffDto staffDto, BindingResult result) {
		if(result.hasErrors()) {
			modelMap.addAttribute("message", "Please input all required fields");
			modelMap.addAttribute("staffDto", staffDto);
		
			return "staffs/addOrEdit";
		}
		
		if( staffDto.getId() != null && staffDto.getId() > 0) {
			modelMap.addAttribute("message", "The staff updated!");
		}else {
			modelMap.addAttribute("message", "New staff inserted!");
		}
		
		Path path = Paths.get("images/");
		
		try(InputStream inputStream = staffDto.getImage().getInputStream()) {
			Files.copy(inputStream, path.resolve(staffDto.getImage().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			String filename = staffDto.getImage().getOriginalFilename();
			
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("message", "Error: " + e.getMessage());
		}
		
		Staff staff = new Staff();
		staff.setBirthday(staffDto.getBirthday());
		staff.setName(staffDto.getName());
		staff.setPhoto(staffDto.getImage().getOriginalFilename());
		Depart depart = new Depart();
		depart.setId(staffDto.getDepartId());
		
		staff.setDepart(depart);
		
		staffService.save(staff);
		
		modelMap.addAttribute("staffDto", staffDto);
		
		return "staffs/addOrEdit";
	}
	
	@ModelAttribute(name = "departs")
	public List<Depart> getListDepart() {
		return staffService.findAllDeparts();
	}
	
}
