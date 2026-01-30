package dev.rex.mcplab.config;

import dev.rex.mcplab.phase0.Phase0Tools;
import dev.rex.mcplab.phase1.Phase1Tools;
import dev.rex.mcplab.phase2.Phase2Tools;
import dev.rex.mcplab.phase3.Phase3Tools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class McpToolConfig {

    @Bean
    @Profile("phase0")
    public ToolCallbackProvider phase0Provider(Phase0Tools tools) {
        return MethodToolCallbackProvider.builder().toolObjects(tools).build();
    }

    @Bean
    @Profile("phase1")
    public ToolCallbackProvider phase1Provider(Phase1Tools tools) {
        return MethodToolCallbackProvider.builder().toolObjects(tools).build();
    }

    @Bean
    @Profile("phase2")
    public ToolCallbackProvider phase2Provider(Phase2Tools tools) {
        return MethodToolCallbackProvider.builder().toolObjects(tools).build();
    }

    @Bean
    @Profile("phase3")
    public ToolCallbackProvider phase3Provider(Phase3Tools tools) {
        return MethodToolCallbackProvider.builder().toolObjects(tools).build();
    }
}
