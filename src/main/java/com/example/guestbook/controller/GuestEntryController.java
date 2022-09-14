package com.example.guestbook.controller;

import com.example.guestbook.model.GuestbookEntry;
import com.example.guestbook.repository.GuestbookEntryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/guestbook")
public class GuestEntryController {

    private GuestbookEntryRepository repository;

    public GuestEntryController(GuestbookEntryRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<GuestbookEntry> createGuestbookEntry(@RequestBody GuestbookEntry entry) {
        entry = repository.save(entry);
        return new ResponseEntity(entry, HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<GuestbookEntry> findGuestbookEntryById(@PathVariable Long id){
        Optional<GuestbookEntry> entry = repository.findById(id);
        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @GetMapping
    public ResponseEntity<List<GuestbookEntry>> findAllGuestBookEntriesByYear(@RequestParam(required = false) Integer year) {
        if (year == null) {
            return ResponseEntity.ok(repository.findAllByOrderByDateDesc());
        } else {
            return ResponseEntity.ok(this.repository.findAllByDateBetweenOrderByDateDesc(LocalDate.of(year, 1, 1),
                    LocalDate.of(year, 12, 31)));
        }
    }

    @PutMapping
    public ResponseEntity<GuestbookEntry> updateGuestbookEntry(@RequestBody GuestbookEntry entryToUpdate){
        Long id = entryToUpdate.getId();
        Optional<GuestbookEntry> response = repository.findById(id)
                .map(entry -> {
                    entry.setTitle(entryToUpdate.getTitle());
                    entry.setComment(entryToUpdate.getComment());
                    entry.setCommenter(entryToUpdate.getCommenter());
                    return repository.save(entry);
                });
        if(response.isPresent()){
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteGuestbookEntryById(@PathVariable Long id){
        repository.deleteById(id);
    }
}

