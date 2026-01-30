# MCP Spring Boot application

| 이 프로젝트는 ChatGPT를 기반으로 작성되었습니다.

Spring Boot 기반의 MCP(Model Context Protocol) 서버 구현 프로젝트입니다.
단계별(Phase)로 기능을 확장하며, 다양한 도구(Tools)를 제공합니다.

## 프로젝트 개요

이 프로젝트는 Spring AI의 MCP Server 기능을 활용하여, LLM(Large Language Model)이 사용할 수 있는 도구들을 제공하는 서버입니다.
각 Phase는 프로파일(`spring.profiles.active`)을 통해 활성화할 수 있습니다.

## 주요 기능 (Phases)

### Phase 0: 기본 도구 (Basic Tools)

- **Echo**: 입력받은 문자열을 그대로 반환합니다.
- **Time**: 현재 UTC 시간을 ISO-8601 형식으로 반환합니다.
- **Health**: 서버 상태를 확인합니다.
- **설정**: `application-phase0.yaml` (STDIO 방식)

### Phase 1: 내부 API 연동 (Internal API)

- **Internal Status**: 내부 REST API (`/internal/status`)를 호출하여 상태를 확인합니다.
- **Internal Slow**: 지연 응답을 테스트하기 위한 API (`/internal/slow`)를 호출합니다.
- **설정**: `application-phase1.yaml` (포트 3001, SSE 방식)

### Phase 2: 데이터베이스 및 캐시 (DB & Redis)

- **Search Notes**: H2 데이터베이스에 저장된 노트를 검색합니다.
- **Get Note**: ID를 기반으로 특정 노트를 조회합니다.
- **Redis Ping**: Redis 연결 상태를 확인합니다.
- **설정**: `application-phase2.yaml` (포트 3002, H2, Redis)
- **초기 데이터**: `data.sql`을 통해 초기 노트 데이터가 로드됩니다.

### Phase 3: 파일 시스템 및 Git (File & Git)

- **Git Status**: 지정된 저장소의 Git 상태를 조회합니다 (JGit 사용).
- **Write Sandbox**: 샌드박스 디렉토리에 파일을 작성합니다 (승인 토큰 필요, 파일명 제한).
- **Read Sandbox**: 샌드박스 디렉토리의 파일을 읽습니다.
- **설정**: `application-phase3.yaml` (포트 3003)
- **보안**:
    - `approval-token`: 파일 쓰기 시 필요한 승인 토큰 (`dev-approve-1234`)
    - `allowed-filenames`: 허용된 파일명 목록 (`note.txt`, `report.json`, `audit.log`)
    - `sandbox-dir`: 파일 작업이 제한된 디렉토리 (`.sandbox`)

## 기술 스택

- **Java**: 17
- **Spring Boot**: 3.5.10
- **Spring AI**: 1.1.2 (MCP Server WebMVC)
- **Database**: H2 (In-memory)
- **Cache**: Redis
- **Git Library**: JGit 7.5.0

## 빌드 및 실행

### 빌드

```bash
./gradlew build
```

### 실행

**Phase 0 (STDIO)**:

```bash
./gradlew bootRun --args='--spring.profiles.active=phase0'
```

**Phase 1 (Internal API)**:

```bash
./gradlew bootRun --args='--spring.profiles.active=phase1'
```

**Phase 2 (DB & Redis)**:

```bash
./gradlew bootRun --args='--spring.profiles.active=phase2'
```

**Phase 3 (Git & File)**:

```bash
./gradlew bootRun --args='--spring.profiles.active=phase3'
```

## 프로젝트 구조

```
src/main/java/dev/rex/mcplab
├── config          # 설정 클래스
├── internal        # 내부 유틸리티 및 클라이언트
├── phase0          # Phase 0 관련 도구 (Echo, Time, Health)
├── phase1          # Phase 1 관련 도구 (Internal API 호출)
├── phase2          # Phase 2 관련 도구 (DB, Redis)
├── phase3          # Phase 3 관련 도구 (Git, File System)
└── RexMcpLabApplication.java  # 메인 애플리케이션 클래스

src/main/resources
├── application.yaml        # 공통 설정
├── application-phase0.yaml # Phase 0 설정
├── application-phase1.yaml # Phase 1 설정
├── application-phase2.yaml # Phase 2 설정
├── application-phase3.yaml # Phase 3 설정
└── data.sql                # Phase 2 초기 데이터
```

## 주의사항

- **Phase 2**: Redis 서버가 로컬(`127.0.0.1:6379`)에 실행 중이어야 합니다.
- **Phase 3**: `application-phase3.yaml`의 `repo-root` 경로가 실제 존재하는 Git 저장소 경로인지 확인하세요. 기본값은 프로젝트 루트입니다.
