package dev.rex.mcplab.phase3;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Profile("phase3")
@Service
public class Phase3Tools {

    private final Phase3Service svc;

    public Phase3Tools(Phase3Service svc) {
        this.svc = svc;
    }

    @Tool(description = "Phase3(Git, ReadOnly): repoRoot에서 git status를 조회합니다.")
    public Map<String, Object> gitStatus() throws Exception {
        return svc.gitStatus();
    }

    @Tool(description = "Phase3(File, Restricted): sandbox-dir에 허용된 파일명만 쓰기. approvalToken 필요. 크기 제한 있음.")
    public Map<String, Object> writeSandbox(String approvalToken, String filename, String content) throws Exception {
        return svc.writeSandboxFile(approvalToken, filename, content);
    }

    @Tool(description = "Phase3(File, Restricted): sandbox-dir에서 허용된 파일명만 읽기.")
    public Map<String, Object> readSandbox(String filename) throws Exception {
        return svc.readSandboxFile(filename);
    }
}
