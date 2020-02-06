package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.VedioMapper;
import lllr.test.breast.dataObject.popularization.Vedio;
import lllr.test.breast.service.inter.VedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VedioServiceImpl implements VedioService {
    @Autowired
    VedioMapper vedioMapper;

    //新增视频，删除待定
    @Override
    public ServerResponse<String> addeditVedio(Vedio vedio) {
        Integer id = vedio.getId();
        int rowCount = 0;
        //新增
        if(id == null)
            rowCount   = vedioMapper.insert(vedio);
        if(rowCount <= 0 )
            return ServerResponse.createByErrorMsg("新增视频失败");
        return ServerResponse.createBysuccessMsg("新增文章成功");

    }

    //获取视频列表
    @Override
    public ServerResponse<List<Vedio>> getVedioList() {
        List<Vedio> vedioList = vedioMapper.getVedioList();
        return ServerResponse.createBysuccessData(vedioList);
    }

    //查询一个视频
    @Override
    public ServerResponse<Vedio> queryById(Integer id) {
        Vedio vedio = vedioMapper.selectByPrimaryKey(id);
        //查询不到结果
        if(vedio == null)
            return ServerResponse.createByErrorMsg("查询错误");
        return ServerResponse.createBysuccessData(vedio);

    }


    //删除一个视频
    @Override
    public ServerResponse<String> delVedio(Integer id) {
        if(id == null)
            return ServerResponse.createByErrorMsg("删除视频中id参数错误");
        int rowCount = 0;
        rowCount = vedioMapper.deleteByPrimaryKey(id);
        if(rowCount <= 0)
            return ServerResponse.createByErrorMsg("删除视错误");
        return ServerResponse.createBysuccessMsg("删除视频成功！");

    }
}
