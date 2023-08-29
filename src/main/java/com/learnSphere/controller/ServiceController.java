package com.learnSphere.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learnSphere.entity.Comments;
import com.learnSphere.entity.Users;
import com.learnSphere.services.CommentService;
import com.learnSphere.services.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class ServiceController {
	
	@Autowired
	UserService uService;
	
	@Autowired
	CommentService cService;

	@PostMapping("/addUser")
	public String addUser(@RequestParam("fname") String fname,
			@RequestParam("lname") String lname, 
			@RequestParam("email") String email, 
			@RequestParam("password") String password, 
			@RequestParam("role") String role, 
			@RequestParam("mobile") String mobile) {
		
		boolean emailExist = uService.emailExist(email);
		
		if(emailExist == false) {
			Users user = new Users();
			user.setFname(fname);
			user.setLname(lname);
			user.setEmail(email);
			user.setPassword(password);
			user.setRole(role);
			user.setMobile(mobile);
		
			uService.addUser(user);
		
			return "redirect:login?registered";
			
		}
		else {
			return "redirect:/register?alreadyExist";
		}
	}
	
	@PostMapping("/userLogin")
	public String validateUser(@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpSession session) {
		
		boolean valid = uService.validate(email, password);
		
		if (valid == true) {
			Users user = uService.getUser(email);
			session.setAttribute("loggedInUser", user);
			if(uService.getUserRole(email).equals("trainer")) {
				return "redirect:trainerHome";
			}
			else {
				return "redirect:studentHome";
			}
		}
		else if(valid == false && uService.emailExist(email) == false) {
			return "redirect:register?notfound";
		}
		else if(valid == false && uService.emailExist(email) == true) {
			return "redirect:register?alreadyExist";
		}
		else {
			return "redirect:login?error";
		}
	}
	
	@PostMapping("/addComment")
	public String comments(@RequestParam("comment") String comment, Model model) {
		Comments c = new Comments();
		c.setComment(comment);
		cService.addComment(c);
		
		List<Comments> commentsList = cService.commentsList();
		model.addAttribute("comments", commentsList);
		return "myLesson";
	}
}
