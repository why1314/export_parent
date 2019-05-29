package com.itheima.service.stat.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.stat.StatMapper;
import com.itheima.service.stat.StatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-21-18:17
 */

@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatMapper statMapper;

    @Override
    public List findFactoryData(String companyId) {
        return statMapper.findFactoryData(companyId);
    }

    @Override
    public List findSellData(String companyId) {
        return statMapper.findSellData(companyId);
    }

    @Override
    public List findOnlineData(String companyId) {
        return statMapper.findOnlineData(companyId);
    }
}
