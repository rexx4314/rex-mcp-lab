package dev.rex.mcplab.phase1;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Profile("phase1")
@RestController
@RequestMapping("/internal")
public class InternalApiController {

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("ok", true);
        res.put("profile", "phase1");
        res.put("timeUtc", OffsetDateTime.now().toString());
        res.put("requestId", UUID.randomUUID().toString());
        return res;
    }

    @GetMapping("/sleep")
    public Map<String, Object> sleep(@RequestParam(defaultValue = "1000") long ms) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(Math.max(0, ms));
        long elapsed = System.currentTimeMillis() - start;

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("ok", true);
        res.put("requestedMs", ms);
        res.put("elapsedMs", elapsed);
        res.put("requestId", UUID.randomUUID().toString());
        return res;
    }

    @GetMapping("/fail")
    public ResponseEntity<Map<String, Object>> fail(@RequestParam(defaultValue = "500") int code) {
        HttpStatus status = HttpStatus.resolve(code);
        if (status == null) status = HttpStatus.INTERNAL_SERVER_ERROR;

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("ok", false);
        body.put("code", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("requestId", UUID.randomUUID().toString());

        return ResponseEntity.status(status).body(body);
    }
}
