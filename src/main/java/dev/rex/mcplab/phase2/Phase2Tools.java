package dev.rex.mcplab.phase2;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Profile("phase2")
@Service
public class Phase2Tools {

    private final NoteQueryService notes;
    private final RedisConnectionFactory redis;

    public Phase2Tools(NoteQueryService notes, RedisConnectionFactory redis) {
        this.notes = notes;
        this.redis = redis;
    }

    @Tool(description = "Phase2(DB, ReadOnly): title에 query가 포함된 Note를 검색합니다. size는 최대 50, page는 0 이상입니다.")
    public Map<String, Object> searchNotes(String query, int page, int size) {
        return notes.search(query, page, size);
    }

    @Tool(description = "Phase2(DB, ReadOnly): Note를 id로 단건 조회합니다. 없으면 not_found를 반환합니다.")
    public Map<String, Object> getNote(long id) {
        try {
            return notes.get(id);
        } catch (NoteQueryService.NotFoundException e) {
            return Map.of("error", "not_found", "id", id);
        }
    }

    @Tool(description = "Phase2(Redis, ReadOnly): Redis 연결 상태를 ping으로 확인합니다.")
    public Map<String, Object> redisPing() {
        String pong = redis.getConnection().ping();
        return Map.of("ok", "PONG".equalsIgnoreCase(pong), "pong", pong);
    }
}
