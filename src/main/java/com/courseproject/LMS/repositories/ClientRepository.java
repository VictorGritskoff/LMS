package com.courseproject.LMS.repositories;

import com.courseproject.LMS.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
