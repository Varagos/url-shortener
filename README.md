# url-shortener

## Run

```bash
docker compose up -d
./mvnw spring-boot:run

# Or build JAR
./mvnw clean package -DskipTests
java -jar target/url-shortener-0.0.1-SNAPSHOT.jar

```

## Decisions

### ID Strategy
We use UUIDv7 for internal IDs and keep `short_code` as a separate unique field. Compared with `BIGSERIAL`, UUIDv7 avoids exposing growth patterns and does not require centralized sequence coordination across distributed writers. Compared with UUIDv4, UUIDv7 keeps the same coordination-free generation model but improves index locality because values are time-ordered.

### Short Code Strategy
Short codes are currently generated with CRC32 for simplicity. This is acceptable for a small learning project, but CRC32 collisions are possible, so production behavior should include uniqueness checks with retry logic or a move to a stronger random Base62 generator.

### Error Handling

### Database Migrations
Schema changes are managed through versioned Flyway migrations and applied forward-only. We avoid manual schema edits so environments remain reproducible and changes are reviewable in source control.
## References

- Bytebase: Choose Primary Key UUID or Auto Increment
	https://www.bytebase.com/blog/choose-primary-key-uuid-or-auto-increment/