package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.AudioMapper;
import lllr.test.breast.dataObject.popularization.Audio;
import lllr.test.breast.service.inter.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudioServiceImpl implements AudioService {

    @Autowired
    AudioMapper auduioMapper;

    @Override
    public ServerResponse<String> addeditAudio(Audio audio) {
        if(audio==null)
            return ServerResponse.createByErrorMsg("audio参数错误");
        int rowCount;
        Integer id = audio.getId();
        //修改
        if(id!=null) {
            rowCount = auduioMapper.updateByPrimaryKey(audio);
            //新增
        } else {
            rowCount = auduioMapper.insert(audio);
        }
        if(rowCount == 0 )
            return  ServerResponse.createByErrorMsg("更新或添加失败，未知错误");
        else
            return ServerResponse.createBysuccessMsg("新增或删除成功");

    }

    @Override
    public ServerResponse<List<Audio>> getAudioList() {
        List<Audio> audioList = auduioMapper.selectAudioList();
        if(audioList.size() >0)
            return ServerResponse.createBysuccessData(audioList);
        System.out.println(audioList.size());
        return null;
    }

    @Override
    public ServerResponse<Audio> queryById(Integer id) {
        Audio audio = auduioMapper.selectByPrimaryKey(id);
        if(audio == null)
            return ServerResponse.createByErrorMsg("查询错误！");
        return ServerResponse.createBysuccessData(audio);

    }

    //删除
    @Override
    public ServerResponse<String> delAudio(Integer id) {
        int rowCount = auduioMapper.deleteByPrimaryKey(id);
        if(rowCount <= 0)
            return ServerResponse.createByErrorMsg("删除失败");

        return ServerResponse.createBysuccessMsg("删除成功");
    }

}
