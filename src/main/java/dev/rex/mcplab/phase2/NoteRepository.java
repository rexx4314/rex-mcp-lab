package dev.rex.mcplab.phase2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findByTitleContainingIgnoreCase(String query, Pageable pageable);
}
