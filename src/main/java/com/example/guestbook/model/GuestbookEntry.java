package com.example.guestbook.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Entry")

public class GuestbookEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String title;
    private String comment;
    private String commenter;

    @Column(name="date_of_entry", nullable = false, updatable = false)
    private LocalDate date = LocalDate.now();
}

