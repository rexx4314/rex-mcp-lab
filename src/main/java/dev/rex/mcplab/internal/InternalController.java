package dev.rex.mcplab.internal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InternalController {

    private final InternalService service;

    public InternalController(InternalService service) {
        this.service = service;
    }

    @GetMapping("/internal/status")
    public String status() {
        return service.status();
    }

    @GetMapping("/internal/slow")
    public String slow(@RequestParam(defaultValue = "0") long ms) {
        return service.slow(ms);
    }
}
