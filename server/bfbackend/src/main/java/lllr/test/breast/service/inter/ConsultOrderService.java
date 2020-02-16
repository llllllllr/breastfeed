package lllr.test.breast.service.inter;

import lllr.test.breast.common.ServerResponse;
import lllr.test.breast.dataObject.consult.ConsultOrder;

import java.util.List;

public interface ConsultOrderService {
    ServerResponse<ConsultOrder> AddConsultOrder(ConsultOrder consultOrder);

    ServerResponse<List<ConsultOrder>> selectConsultOrderByUserId(Integer userId);

    ServerResponse<List<ConsultOrder>> selectConsultOrderByDoctorId(Integer doctorId);
}