package lllr.test.breast.service.inter;


import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.question.Test;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestService {

     ServerResponse<List<Test>> getTest();
}
