package com.learnSphere.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learnSphere.entity.Course;
import com.learnSphere.entity.Lesson;
import com.learnSphere.services.TrainerServices;

@Controller
public class TrainerController {
	@Autowired
	TrainerServices tServices;
	
	@PostMapping("/addCourse")
	public String addCourse(@RequestParam("courseId") int courseId, 
			@RequestParam("courseName")String courseName, 
			@RequestParam("coursePrice") int coursePrice) {
		
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName(courseName);
		course.setCoursePrice(coursePrice);
		
		tServices.addCourse(course);
		return "redirect:/trainerHome";
	}
	
	@PostMapping("/addLesson")
	public String addLesson(@RequestParam("courseId") int courseId, 
			@RequestParam("lessonId") int lessonId, 
			@RequestParam("lessonName") String lessonName, 
			@RequestParam("lessonTopics") String topics, 
			@RequestParam("videoLink") String link) {
		
		Course course = tServices.getCourse(courseId);
		
		Lesson lesson = new Lesson(lessonId, lessonName, topics, link, course);
		
		tServices.addLesson(lesson);
		
		course.getLessons().add(lesson);
		tServices.addCourse(course);
		
		return "redirect:/trainerHome";
	}
	
	@GetMapping("/showCourses")
	public String showCourses(Model model) {
		List<Course> courseList = tServices.couseList();
		model.addAttribute("courseList", courseList);
		System.out.println(courseList);
		return "courses";
	}
}
