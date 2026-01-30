package dev.rex.mcplab.phase0;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Profile("phase0")
@Service
public class Phase0Tools {

    @Tool(description = "Echo: 입력 문자열을 그대로 반환합니다.")
    public String echo(String text) {
        return text == null ? "" : text;
    }

    @Tool(description = "Time: 현재 UTC 시간을 ISO-8601 문자열로 반환합니다.")
    public String getTimeUtc() {
        return OffsetDateTime.now(ZoneOffset.UTC).toString();
    }

    @Tool(description = "Health: 서버 상태를 ok로 반환합니다.")
    public String health() {
        return "ok";
    }
}
