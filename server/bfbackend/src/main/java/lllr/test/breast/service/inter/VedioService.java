package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Vedio;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface VedioService {
    //添加或修改视频
    ServerResponse<String> addeditVedio(Vedio vedio);

    //获取视频列表
    ServerResponse<List<Vedio>> getVedioList();

    //获取一段视频
    ServerResponse<Vedio> queryById(Integer id);

    //删除一个视频
    ServerResponse<String>  delVedio(Integer id);
}
