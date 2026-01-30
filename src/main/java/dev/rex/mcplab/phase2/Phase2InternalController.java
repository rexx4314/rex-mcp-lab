package dev.rex.mcplab.phase2;

import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Profile("phase2")
@RestController
@RequestMapping("/internal")
public class Phase2InternalController {

    private final NoteQueryService notes;
    private final RedisConnectionFactory redis;

    public Phase2InternalController(NoteQueryService notes, RedisConnectionFactory redis) {
        this.notes = notes;
        this.redis = redis;
    }

    @GetMapping("/notes")
    public Map<String, Object> searchNotes(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return notes.search(q, page, size);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<?> getNote(@PathVariable long id) {
        try {
            return ResponseEntity.ok(notes.get(id));
        } catch (NoteQueryService.NotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", "not_found", "id", id));
        }
    }

    /**
     * ✅ 권장: Redis 연결 확인은 "쓰기 없이" connection ping으로 끝냄
     */
    @GetMapping("/redis/ping")
    public Map<String, Object> redisPing() {
        String pong = redis.getConnection().ping();
        return Map.of("ok", "PONG".equalsIgnoreCase(pong), "pong", pong);
    }
}
