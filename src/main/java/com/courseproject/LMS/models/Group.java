package com.courseproject.LMS.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "group_table")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int groupId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToMany
    @JoinTable(
            name = "group_client",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private List<Client> clients = new ArrayList<>();

    @Column(name = "client_count")
    private int clientCount;

    @Column(name = "status")
    private String status;

    public void addClient(Client client) {
        this.clients.add(client);
        this.updateClientCount();
    }

    public void updateClientCount() {
        this.clientCount = this.clients.size();
    }
}