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
    public ServerResponse<ConsultOrder> AddConsultOrder(ConsultOrder consultOrder) {
        int inNum = consultOrderMapper.insert(consultOrder);
        return inNum == 1 ? ServerResponse.createBysuccessData(consultOrder) : ServerResponse.createByError();
    }

    @Override
    public ServerResponse<List<ConsultOrder>> selectConsultOrderByUserId(Integer userId) {
        List<ConsultOrder> orderLists = consultOrderMapper.selectConsultOrderAndUserByUserId(userId);
        return ServerResponse.createBysuccessData(orderLists);
    }

    @Override
    public ServerResponse<List<ConsultOrder>> selectConsultOrderByDoctorId(Integer doctorId) {
        List<ConsultOrder> orderLists = consultOrderMapper.selectConsultOrderAndDoctorByDoctorId(doctorId);
        return ServerResponse.createBysuccessData(orderLists);
    }
}
