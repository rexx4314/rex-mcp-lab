package dev.rex.mcplab.phase3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("phase3")
@ConfigurationProperties(prefix = "mcplab.phase3")
public class Phase3Properties {
    private String repoRoot;
    private String sandboxDir;
    private String approvalToken;
    private int maxBytes = 4096;
    private List<String> allowedFilenames = List.of();

    public String getRepoRoot() {
        return repoRoot;
    }

    public void setRepoRoot(String repoRoot) {
        this.repoRoot = repoRoot;
    }

    public String getSandboxDir() {
        return sandboxDir;
    }

    public void setSandboxDir(String sandboxDir) {
        this.sandboxDir = sandboxDir;
    }

    public String getApprovalToken() {
        return approvalToken;
    }

    public void setApprovalToken(String approvalToken) {
        this.approvalToken = approvalToken;
    }

    public int getMaxBytes() {
        return maxBytes;
    }

    public void setMaxBytes(int maxBytes) {
        this.maxBytes = maxBytes;
    }

    public List<String> getAllowedFilenames() {
        return allowedFilenames;
    }

    public void setAllowedFilenames(List<String> allowedFilenames) {
        this.allowedFilenames = allowedFilenames;
    }
}
