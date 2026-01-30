package dev.rex.mcplab.phase3;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Profile("phase3")
@RestController
@RequestMapping("/internal")
public class Phase3InternalController {

    private final Phase3Service svc;

    public Phase3InternalController(Phase3Service svc) {
        this.svc = svc;
    }

    @GetMapping("/git/status")
    public ResponseEntity<?> gitStatus() {
        try {
            return ResponseEntity.ok(svc.gitStatus());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "git_status_failed", "message", e.getMessage()));
        }
    }

    @PostMapping("/sandbox/write")
    public ResponseEntity<?> write(@RequestParam String approval,
                                   @RequestParam String filename,
                                   @RequestBody(required = false) String content) {
        try {
            return ResponseEntity.ok(svc.writeSandboxFile(approval, filename, content));
        } catch (SecurityException se) {
            return ResponseEntity.status(403).body(Map.of("error", se.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "write_failed", "message", e.getMessage()));
        }
    }

    @GetMapping("/sandbox/read")
    public ResponseEntity<?> read(@RequestParam String filename) {
        try {
            return ResponseEntity.ok(svc.readSandboxFile(filename));
        } catch (SecurityException se) {
            return ResponseEntity.status(403).body(Map.of("error", se.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "read_failed", "message", e.getMessage()));
        }
    }
}
