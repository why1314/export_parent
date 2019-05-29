package com.itheima.service.stat;

import java.util.List;
import java.util.Map;

/**
 * 详情:统计图表的业务层接口
 *
 * @author wz
 * @date 2019-05-21-18:06
 */
public interface StatService {

    List findFactoryData(String companyId);

    List findSellData(String companyId);

    List findOnlineData(String companyId);
}
