package dev.rex.mcplab.internal;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class InternalService {

    public String status() {
        return "UP @" + OffsetDateTime.now();
    }

    public String slow(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        return "SLOW_OK(ms=" + ms + ")";
    }
}
