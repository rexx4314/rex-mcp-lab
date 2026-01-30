INSERT INTO notes(title, body, created_at)
VALUES ('hello', 'first note body', CURRENT_TIMESTAMP()),
       ('mcp', 'phase2 db paging/search test', CURRENT_TIMESTAMP()),
       ('redis', 'phase2 redis connectivity test', CURRENT_TIMESTAMP()),
       ('spring', 'spring boot + jpa + redis', CURRENT_TIMESTAMP());
