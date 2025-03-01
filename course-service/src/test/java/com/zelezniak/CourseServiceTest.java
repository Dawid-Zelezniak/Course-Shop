package com.zelezniak;

import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.course.Course;
import com.zelezniak.project.course.CourseService;
import com.zelezniak.project.course.CourseServiceImpl;
import com.zelezniak.project.valueobjects.UserCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CoursesApplication.class)
class CourseServiceTest {

    Course course;

    CourseAuthor courseAuthor;

    @MockBean
    CourseServiceImpl courseServiceImpl;

    @Autowired
    CourseService courseService;

    @BeforeEach
    void initialize() {
        initializeAuthor();
        initializeCourse();
    }

    void initializeCourse() {
        course = new Course();
        course.setCourseAuthor(courseAuthor);
        course.setCourseId(1L);
        course.setDescription("Programming in Java");
        course.setCategory("IT");
    }

    void initializeAuthor() {
        courseAuthor = new CourseAuthor();
        courseAuthor.setAuthorId(1L);
        UserCredentials userCredentials = new UserCredentials("marian@gmail.com", "password");
        courseAuthor.setUserCredentials(userCredentials);
    }

    @Test
    void find_Course_By_Id() {
        when(courseServiceImpl.findById(1L)
        ).thenReturn(course);

        assertEquals(course,
                courseService.findById(1L));

        verify(courseServiceImpl,
                times(1)
        ).findById(1L);
    }
}
