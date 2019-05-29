package com.itheima.mapper.stat;

import java.util.List;
import java.util.Map;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-21-18:19
 */
public interface StatMapper {
    /**
     * 查询工厂的销售数据
     * @param companyId
     * @return
     */
    List<Map> findFactoryData(String companyId);

    /**
     * 查询商品的销售数据
     * @param companyId
     * @return
     */
    List<Map> findSellData(String companyId);

    /**
     * 查询在线人数
     * @param companyId
     * @return
     */
    List<Map> findOnlineData(String companyId);
}
