package lllr.test.breast.redis;


import org.springframework.stereotype.Component;

@Component
public abstract class BasePrefix implements KeyPrefix {

    Integer expireSeconds;
    String prefix;

    public BasePrefix(String prefix)
    {
        this.expireSeconds = 0;
        this.prefix=prefix;
    }


    public BasePrefix(Integer expireSeconds,String prefix)
    {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    //默认为0表示永不过期
    @Override
    public Integer expireSeconds() {
        return expireSeconds;

    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
