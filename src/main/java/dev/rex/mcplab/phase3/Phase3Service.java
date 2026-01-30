package dev.rex.mcplab.phase3;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;

@Profile("phase3")
@Service
public class Phase3Service {

    private final Phase3Properties props;

    public Phase3Service(Phase3Properties props) {
        this.props = props;
    }

    public Map<String, Object> gitStatus() throws Exception {
        try (Git git = Git.open(Paths.get(props.getRepoRoot()).toFile())) {
            Status s = git.status().call();

            Map<String, Object> out = new LinkedHashMap<>();
            out.put("repoRoot", props.getRepoRoot());
            out.put("added", s.getAdded());
            out.put("changed", s.getChanged());
            out.put("modified", s.getModified());
            out.put("missing", s.getMissing());
            out.put("removed", s.getRemoved());
            out.put("untracked", s.getUntracked());
            out.put("conflicting", s.getConflicting());
            out.put("clean", s.isClean());
            return out;
        }
    }

    public Map<String, Object> writeSandboxFile(String approvalToken, String filename, String content) throws IOException {
        requireApproval(approvalToken);
        requireAllowedFilename(filename);

        byte[] bytes = (content == null ? "" : content).getBytes(StandardCharsets.UTF_8);
        if (bytes.length > props.getMaxBytes()) {
            return Map.of("error", "too_large", "maxBytes", props.getMaxBytes(), "actualBytes", bytes.length);
        }

        Path dir = Paths.get(props.getSandboxDir());
        Files.createDirectories(dir);

        Path target = dir.resolve(filename).normalize();

        // ✅ 디렉터리 탈출 방지
        if (!target.startsWith(dir)) {
            return Map.of("error", "path_traversal_blocked");
        }

        Files.write(target, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        return Map.of("ok", true, "path", target.toString(), "bytes", bytes.length);
    }

    public Map<String, Object> readSandboxFile(String filename) throws IOException {
        requireAllowedFilename(filename);

        Path dir = Paths.get(props.getSandboxDir());
        Path target = dir.resolve(filename).normalize();
        if (!target.startsWith(dir)) {
            return Map.of("error", "path_traversal_blocked");
        }
        if (!Files.exists(target)) {
            return Map.of("error", "not_found", "path", target.toString());
        }

        byte[] bytes = Files.readAllBytes(target);
        String text = new String(bytes, StandardCharsets.UTF_8);

        return Map.of("ok", true, "path", target.toString(), "bytes", bytes.length, "content", text);
    }

    private void requireApproval(String token) {
        if (token == null || !token.equals(props.getApprovalToken())) {
            throw new SecurityException("approval_required");
        }
    }

    private void requireAllowedFilename(String filename) {
        if (filename == null || filename.isBlank()) throw new IllegalArgumentException("filename_required");
        if (!props.getAllowedFilenames().contains(filename)) {
            throw new SecurityException("filename_not_allowed");
        }
    }
}
