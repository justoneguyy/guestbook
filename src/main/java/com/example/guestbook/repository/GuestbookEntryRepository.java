package com.example.guestbook.repository;

import com.example.guestbook.model.GuestbookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GuestbookEntryRepository extends JpaRepository<GuestbookEntry, Long> {
    List<GuestbookEntry> findAllByOrderByDateDesc();
    List<GuestbookEntry> findAllByDateBetweenOrderByDateDesc(LocalDate first, LocalDate second);
}