package lllr.test.breast.util.ikanalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class IKAnalyzerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IKAnalyzerUtil.class);

    /**
     * IK分词
     * @param msg
     * @return
     */
    public static List<String> iKSegmenterToList(String msg){

        StringReader sr=new StringReader(msg);
        IKSegmenter ik=new IKSegmenter(sr, true);
        Lexeme lex=null;
        List<String> list=new ArrayList<>();
        while(true){
            try {
                if ((lex = ik.next()) == null) break;
            } catch (IOException e) {
                LOGGER.error("=== IKAnalyzerUtil->iKSegmenterToList:" + msg + " 错误消息:" + e.getMessage() + " ===");
            }
            list.add(lex.getLexemeText());
        }
        LOGGER.info("=== IKAnalyzerUtil->iKSegmenterToList:" + msg + " - > " + list.toString());
        return list;
    }
}
