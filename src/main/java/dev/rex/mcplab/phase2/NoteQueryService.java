package dev.rex.mcplab.phase2;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Profile("phase2")
@Service
public class NoteQueryService {

    private final NoteRepository repo;

    public NoteQueryService(NoteRepository repo) {
        this.repo = repo;
    }

    public Map<String, Object> search(String query, int page, int size) {
        int safePage = Math.max(0, page);
        int safeSize = Math.min(Math.max(1, size), 50); // ✅ 결과 크기 제한(현업형)

        var result = repo.findByTitleContainingIgnoreCase(
                query == null ? "" : query,
                PageRequest.of(safePage, safeSize, Sort.by(Sort.Direction.DESC, "id"))
        );

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("query", query == null ? "" : query);
        out.put("page", result.getNumber());
        out.put("size", result.getSize());
        out.put("totalElements", result.getTotalElements());
        out.put("totalPages", result.getTotalPages());
        out.put("items", result.getContent().stream().map(n -> Map.of(
                "id", n.getId(),
                "title", n.getTitle(),
                "createdAt", n.getCreatedAt().toString()
        )).toList());
        return out;
    }

    public Map<String, Object> get(long id) {
        var n = repo.findById(id).orElseThrow(() -> new NotFoundException("note not found: " + id));
        return Map.of(
                "id", n.getId(),
                "title", n.getTitle(),
                "body", n.getBody(),
                "createdAt", n.getCreatedAt().toString()
        );
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String msg) {
            super(msg);
        }
    }
}
