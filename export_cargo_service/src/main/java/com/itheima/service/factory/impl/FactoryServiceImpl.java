package com.itheima.service.factory.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.entity.cargo.factory.Factory;
import com.itheima.entity.cargo.factory.FactoryExample;
import com.itheima.mapper.cargo.factory.FactoryMapper;
import com.itheima.service.factory.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-15-14:44
 */
@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private FactoryMapper factoryMapper;

    @Override
    public void save(Factory factory) {
        factoryMapper.insertSelective(factory);
    }

    @Override
    public void update(Factory factory) {
        factoryMapper.updateByPrimaryKeySelective(factory);
    }

    @Override
    public void delete(String id) {
factoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Factory findById(String id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        return factory;
    }

    @Override
    public List<Factory> findAll(FactoryExample example) {

        List<Factory> factoryList = factoryMapper.selectByExample(example);
        return factoryList;
    }
}
