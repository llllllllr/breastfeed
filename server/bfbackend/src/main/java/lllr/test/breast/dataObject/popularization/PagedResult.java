package lllr.test.breast.dataObject.popularization;

import lombok.Data;

import java.util.List;

@Data
public class PagedResult {


    private  int page;//当前页数
    private  int totalPage;//总页数
    private  long records;//总记录数
    private List<?> rows;

}
