package lllr.test.breast.service.impl;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dao.mapper.ConsultOrderMapper;
import lllr.test.breast.dataObject.consult.ConsultOrder;
import lllr.test.breast.service.inter.ConsultOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultOrderServiceImpl implements ConsultOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultOrderServiceImpl.class);

    @Autowired
    private ConsultOrderMapper consultOrderMapper;


    @Override
    public ServerResponse<String> AddConsultOrder(ConsultOrder consultOrder) {
        int inNum = consultOrderMapper.insert(consultOrder);
        if( inNum == 1 )
        {
            String oid = consultOrder.getOid();
            return ServerResponse.createBysuccessData(oid);
        }
        return  ServerResponse.createByError();

    }

    @Override
    public ServerResponse<List<ConsultOrder>> selectConsultOrderByUserId(Integer userId) {
        List<ConsultOrder> orderLists = consultOrderMapper.selectConsultOrderAndDoctorByUserId(userId);
        LOGGER.info("selectConsultOrderByUserId： " + orderLists);
        return ServerResponse.createBysuccessData(orderLists);
    }

    @Override
    public ServerResponse<List<ConsultOrder>> selectConsultOrderByDoctorId(Integer doctorId) {
        List<ConsultOrder> orderLists = consultOrderMapper.selectConsultOrderAndUserByDoctorId(doctorId);
        return ServerResponse.createBysuccessData(orderLists);
    }

    @Override
    public ServerResponse<ConsultOrder> selectByOid(String oid) {
        if(oid == null)
            return ServerResponse.createByErrorMsg("订单参数错误");
        return ServerResponse.createBysuccessData(consultOrderMapper.getByOid(oid));
    }

    @Override
    public ServerResponse updateConsultOrderStatusById(Integer id, Integer status) {
        return consultOrderMapper.updateConsultOrderStatusById(id,status) == 1 ? ServerResponse.createBysuccess() : ServerResponse.createByError();
    }


}
