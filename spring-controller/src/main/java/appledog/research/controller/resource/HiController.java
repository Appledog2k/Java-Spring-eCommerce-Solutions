package appledog.research.controller.resource;

import appledog.research.application.service.event.EventAppService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;

@RestController
@RequestMapping("/hello")
public class HiController {

    @Autowired
    private EventAppService eventAppService;


    @GetMapping("/hi")
    @RateLimiter(name = "backendA", fallbackMethod = "fallbackMethod")
    public String hello() {
        return eventAppService.sayHi("Hi");
    }

    public String fallbackMethod(Throwable throwable) {
        return "Too many request";
    }

    @GetMapping("/hi/v1")
    @RateLimiter(name = "backendB", fallbackMethod = "fallbackMethod")
    public String sayHi() {
        return eventAppService.sayHi("Ho");
    }

    @GetMapping("circuit/breaker")
    @CircuitBreaker(name = "checkRandom", fallbackMethod = "fallbackCircuitBreaker")
    public String circuitBreaker() {
        SecureRandom random = new SecureRandom();
        int productId  = random.nextInt(20) + 1;
        String url = "https://fakestoreapi.com/products/" + productId;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    public String fallbackCircuitBreaker(Throwable throwable) {
        return throwable.getMessage();
    }
}
