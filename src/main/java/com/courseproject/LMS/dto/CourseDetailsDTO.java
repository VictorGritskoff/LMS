package com.courseproject.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseDetailsDTO {
    private Long courseId;
    private String courseName;
    private double coursePrice;
    private String teacherFirstName;
    private String teacherLastName;
    private String teacherSurname;
}