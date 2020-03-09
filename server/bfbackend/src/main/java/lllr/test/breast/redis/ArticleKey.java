package lllr.test.breast.redis;

public class ArticleKey extends BasePrefix {


    public  static final Integer expireSnds = 3600 * 24 * 2 ;
    public ArticleKey(String prefix) {
        super(prefix);
    }

    public ArticleKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


    public static ArticleKey articleList  = new ArticleKey(60*60,"al");
}
