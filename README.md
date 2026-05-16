# url-shortener

## Run

```bash
docker compose up -d
./mvnw spring-boot:run

# Or build JAR
./mvnw clean package -DskipTests
java -jar target/url-shortener-0.0.1-SNAPSHOT.jar

curl -X POST http://localhost:8080/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "www.google.com"}'
```

## Decisions

### ID Strategy
We use UUIDv7 for internal IDs and keep `short_code` as a separate unique field. Compared with `BIGSERIAL`, UUIDv7 avoids exposing growth patterns and does not require centralized sequence coordination across distributed writers. Compared with UUIDv4, UUIDv7 keeps the same coordination-free generation model but improves index locality because values are time-ordered.

### Short Code Strategy

The Strategy Design pattern is used to switch between different approaches. We need to keep in mind that each approach however is affects by extra details, like type/existence of a centralized db icrement counter or a UUID identifier, possibility of collisions and hence of retry logic in the application level.

An approach for short codes is CRC32 hash for simplicity. This is acceptable for a small learning project, but CRC32 collisions are possible, so production behavior should include uniqueness checks with retry logic or a move to a stronger random Base62 generator.

We use random base62 generator(not counter based).
#### The birthday problem
The birthday problem asks: in a group of n people, what's the probability that two share a birthday? Intuitively you'd think you need ~365 people, but it's actually just 23 for a 50% chance.  
```math
p(\text{collision}) = 1 - e^{-n^2/(2k)}

```
Where `n` is the number of URLs, and `k` is the keyspace size
- At 1 million URLs → collision chance ≈ 0.00014% (negligible)
- At 10 million URLs → collision chance ≈ 1.4%
- At 100 million URLs → collision chance ≈ 75%

At very large scale we'd either increase code length to 8-9 or switch to a base62 counter-based approach.

Base62 counter-based conversion:
First we need the url's icrement counter. E.g. $11157_{10}$


```math
11157_{10} = 2 \cdot 62^2 + 55 \cdot 62^1 + 59 \cdot 62^0 = [2,55,59] \to [2,T,X]
238327_{10} = 61 \cdot 62^2 + 61 \cdot 62^1 + 61 \cdot 62^0 = [61,61,61] \to [z,z,z]
```


Worth noting that with hashing, same input would always produce same short code. 


### Error Handling

### Database Migrations
Schema changes are managed through versioned Flyway migrations and applied forward-only. We avoid manual schema edits so environments remain reproducible and changes are reviewable in source control.
## References

- Bytebase: Choose Primary Key UUID or Auto Increment
	https://www.bytebase.com/blog/choose-primary-key-uuid-or-auto-increment/
- Birthday Problem: Math behind collision probability
	https://en.wikipedia.org/wiki/Birthday_problem