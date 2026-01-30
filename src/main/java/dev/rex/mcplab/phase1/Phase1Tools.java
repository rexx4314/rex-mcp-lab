package dev.rex.mcplab.phase1;

import dev.rex.mcplab.internal.InternalClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("phase1")
@Service
public class Phase1Tools {

    private final InternalClient internalClient;

    @Value("${server.port:8080}")
    private int port;

    public Phase1Tools(InternalClient internalClient) {
        this.internalClient = internalClient;
    }

    @Tool(description = "Phase1: 내부 REST /internal/status 를 호출해 결과를 반환합니다(읽기 전용).")
    public String callInternalStatus() {
        try {
            return internalClient.getStatus(port);
        } catch (Exception e) {
            return "ERROR(callInternalStatus): " + e.getClass().getSimpleName() + " - " + e.getMessage();
        }
    }

    @Tool(description = "Phase1: 내부 REST /internal/slow?ms=... 를 호출합니다(타임아웃/실패 테스트용).")
    public String callInternalSlow(long ms) {
        try {
            return internalClient.getSlow(port, ms);
        } catch (Exception e) {
            return "ERROR(callInternalSlow): " + e.getClass().getSimpleName() + " - " + e.getMessage();
        }
    }
}
