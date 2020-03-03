package lllr.test.breast.redis;


import org.springframework.stereotype.Component;

@Component
public interface KeyPrefix {
    Integer expireSeconds();

    String getPrefix();
}
