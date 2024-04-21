package com.zelezniak.project.author;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseAuthorRepository extends JpaRepository<CourseAuthor, Long> {

    boolean existsByUserCredentialsEmail(String email);

    CourseAuthor findByUserCredentialsEmail(String email);

    Optional<CourseAuthor> findByAuthorId(Long id);

    @Query("SELECT c FROM CourseAuthor c WHERE NOT EXISTS " +
            "(SELECT rol FROM c.roles rol WHERE rol.name =:roleAdmin)")
    List<CourseAuthor> getAllAuthorsWithoutAdmin(@Param("roleAdmin") String roleAdmin);
}
