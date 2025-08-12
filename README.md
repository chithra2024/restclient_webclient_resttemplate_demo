#Key Points

ApplicationRunner runs immediately after the app starts.

@RestController lets you expose endpoints in the same class (/invoke endpoint here).

RestTemplate → simple & synchronous (old but still supported).

WebClient → reactive & async (recommended for non-blocking calls).

RestClient → new in Spring 6+, combines simplicity of RestTemplate with fluent API.

#You can run this and check:

mvn spring-boot:run

#Startup logs will print API responses, and hitting:

http://localhost:8080/invoke

will return all three call results in JSON.
