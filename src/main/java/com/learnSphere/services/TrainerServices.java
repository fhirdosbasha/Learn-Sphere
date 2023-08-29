package com.learnSphere.services;

import java.util.List;

import com.learnSphere.entity.Course;
import com.learnSphere.entity.Lesson;

public interface TrainerServices {

	String addCourse(Course course);
	
	Course getCourse(int courseId);
	
	String addLesson(Lesson lesson);
	
	List<Course> couseList();
	
	String saveCourse(Course course);
}
