package lllr.test.breast.redis;

public class UserKey extends BasePrefix {


    public  static final Integer expireSnds = 3600 * 24 * 2 ;
    public UserKey(String prefix) {
        super(prefix);
    }

    public UserKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


    public static UserKey token  = new UserKey(expireSnds,"tk");
}
