package com.zelezniak.project.service;


import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zelezniak.project.entity.Course;
import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.entity.Student;
import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.exception.CustomErrors;
import com.zelezniak.project.repository.CourseAuthorRepository;
import com.zelezniak.project.repository.CourseRepository;
import com.zelezniak.project.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseAuthorRepository authorRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public void addCourse(Course course, Long authorId) {

        if (course != null) {
            checkIfCourseExists(course);
            CourseAuthor authorFromDb = getAuthorById(authorId);
            authorFromDb.addAuthorCourse(course);
            log.info("DOCHODZIMY TUTAJ");
            courseRepository.save(course);
            authorRepository.save(authorFromDb);
            course.setCourseAuthor(authorFromDb);
            log.info("Course AUTHOR po ustawieniu" + course.getCourseAuthor().getFullName());
        }
    }

    @Transactional
    public void deleteCourse(Long courseId, CourseAuthor authorFromDb) {
        Course courseFromDb = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CustomErrors.COURSE_NOT_FOUND));
        CourseAuthor courseAuthor = courseFromDb.getCourseAuthor();
        courseAuthor.removeCourseFromCreatedByAuthor(courseId);
        authorRepository.save(authorFromDb);
        courseRepository.deleteById(courseId);
    }

    public Course getById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() ->
                new CourseException(CustomErrors.COURSE_NOT_FOUND));
    }

    public void updateCourse(Long courseId, Course course) {
        Course courseFromDb = this.getById(courseId);
        if (!courseFromDb.getTitle().equals(course.getTitle()) && this.courseRepository.existsByTitle(course.getTitle())) {
            throw new CourseException(CustomErrors.COURSE_ALREADY_EXISTS);
        } else {
            this.setCourse(courseFromDb, course);
            this.courseRepository.save(courseFromDb);
        }
    }

    public List<Course> getAllAvailableCourses(String userEmail) {
        List<Course> coursesFromDb = courseRepository.findAll();
        CourseAuthor courseAuthor = authorRepository.findByEmail(userEmail);
        if (courseAuthor != null) {
            log.info("KURSY W BAZIE " + coursesFromDb.size());
            return coursesAvailableForAuthor(coursesFromDb, courseAuthor);
        } else {
            log.info("KURSY DLA STUDENTA");
            Student student = studentRepository.findByEmail(userEmail);
            return coursesAvailableForStudent(coursesFromDb, student);
        }
    }

    private List<Course> coursesAvailableForStudent(List<Course> coursesFromDb, Student student) {
        Set<Course> boughtCourses = student.getBoughtCourses();
        return deleteCoursesFromList(coursesFromDb, boughtCourses);
    }

    private List<Course> coursesAvailableForAuthor(List<Course> coursesFromDb, CourseAuthor courseAuthor) {
        Set<Course> createdByAuthor = courseAuthor.getCreatedByAuthor();
        Set<Course> boughtCourses = courseAuthor.getBoughtCourses();
        Set<Course> coursesToDelete = Stream.of(createdByAuthor, boughtCourses)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return deleteCoursesFromList(coursesFromDb, coursesToDelete);
    }

    private static List<Course> deleteCoursesFromList(List<Course> coursesFromDb, Set<Course> coursesToDelete) {
        coursesFromDb.removeAll(coursesToDelete);
        coursesFromDb.forEach(course -> log.info("KURS " + course));
        return coursesFromDb;
    }

    private void setCourse(Course courseFromDb, Course course) {
        courseFromDb.setTitle(course.getTitle());
        courseFromDb.setDescription(course.getDescription());
        courseFromDb.setCategory(course.getCategory());
        courseFromDb.setPrice(course.getPrice());
    }

    private void checkIfCourseExists(Course course) {
        if (this.courseRepository.existsByTitle(course.getTitle())) {
            throw new CourseException(CustomErrors.COURSE_ALREADY_EXISTS);
        }
    }

    private CourseAuthor getAuthorById(Long authorId) {
        return authorRepository.findByAuthorId(authorId)
                .orElseThrow(() -> new CourseException(CustomErrors.USER_NOT_FOUND));
    }
}