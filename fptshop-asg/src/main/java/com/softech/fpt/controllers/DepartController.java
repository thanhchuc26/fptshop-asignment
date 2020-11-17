package com.softech.fpt.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.softech.fpt.models.Depart;
import com.softech.fpt.services.DepartService;

@Controller
@RequestMapping("/departs")
public class DepartController {
	@Autowired
	private DepartService departService;
	
	@GetMapping("/add")
	public String add(ModelMap modelMap) {
		modelMap.addAttribute("depart", new Depart());
		return "departs/addOrEdit";
	}
	
	@PostMapping("/saveOrUpdate")
	public String saveOrUpdate(ModelMap modelMap,  Depart depart) {
		String message = "New depart inserted";
		
		if(depart.getId() != null && depart.getId() > 0) {
			message = "The depart updated";
		}
		
		departService.save(depart);
		
		modelMap.addAttribute(depart);
		modelMap.addAttribute("message", message);
		return "departs/addOrEdit";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(ModelMap modelMap ,@PathVariable(name = "id") Integer id) {
		Optional<Depart> optdepart = departService.findById(id);
		
		if(optdepart.isPresent()) {
			modelMap.addAttribute("depart", optdepart.get());
		}else {
			return list(modelMap);
		}
		return "departs/addOrEdit";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(ModelMap modelMap ,@PathVariable(name = "id") Integer id) {
		departService.deleteById(id);
		
		return list(modelMap);
	}
	
	@RequestMapping("/list")
	public String list(ModelMap modelMap) {
		List<Depart> list = (List<Depart>) departService.findAll();
		
		modelMap.addAttribute("departs", list);
		
		return "departs/list";
	}
	
	@RequestMapping("/find")
	public String find(ModelMap modelMap, @RequestParam(defaultValue = "") String name) {
		List<Depart> list =  departService.findByNameLikeOrderByName(name);
		
		modelMap.addAttribute("departs", list);
		
		return "departs/find";
	}
	
}
