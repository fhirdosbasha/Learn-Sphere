package com.learnSphere.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learnSphere.entity.Comments;
import com.learnSphere.entity.Course;
import com.learnSphere.entity.Lesson;
import com.learnSphere.entity.Users;
import com.learnSphere.services.CommentService;
import com.learnSphere.services.StudentServices;
import com.learnSphere.services.TrainerServices;
import com.learnSphere.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {

	@Autowired
	StudentServices sService;
	
	@Autowired
	TrainerServices tService;
	
	@Autowired
	UserService uService;
	
	@Autowired
	CommentService cService;
	
	@GetMapping("/purchaseCourses")
	public String showCourses(Model model, HttpSession session) {
		Users user = (Users) session.getAttribute("loggedInUser");
		
		List<Course> courseList = tService.couseList();
		model.addAttribute("courseList", courseList);
		model.addAttribute("loggedInUser", user);
		return "purchase";
	}
	
	@GetMapping("/fetchCourses")
	public String fetchCourses(Model model, HttpSession session) {
		
		Users loggedUser = (Users) session.getAttribute("loggedInUser");
		
		String email = loggedUser.getEmail();
		
		Users user = uService.getUser(email);
		
		List<Course> courseList = user.getCourses();
		model.addAttribute("courseList", courseList);
		
		return "myCourses";
	}
	
	@GetMapping("/viewLesson")
	public String viewLesson(@RequestParam("lessonId") int lessonId, 
			Model model, HttpSession session) {
		
		Lesson lesson = sService.getLesson(lessonId);
		
		String youtubeUrl = lesson.getLink();
		
		String videoId = youtubeUrl.substring(youtubeUrl.indexOf("=") + 1);
	    lesson.setLink(videoId);
	    
	    model.addAttribute("lesson", lesson);
	    
	    List<Comments> commentsList=cService.commentsList();
		model.addAttribute("comments",commentsList);
		
		return "myLesson";
	}
}
