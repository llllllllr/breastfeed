package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.popularization.Audio;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AudioService {

    //添加或修改音频
    ServerResponse<String> addeditAudio(Audio audio);

    //获取音频列表
    ServerResponse<List<Audio>> getAudioList();

    //获取一篇音频
    ServerResponse<Audio> queryById(Integer id);

    //删除一篇音频
    ServerResponse<String>  delAudio(Integer id);
}
