package com.courseproject.LMS.repositories;

import com.courseproject.LMS.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findTop6ByOrderByCreationDateDesc();
}
