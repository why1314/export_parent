package com.itheima.test;

import com.itheima.entity.cargo.factory.Factory;
import com.itheima.entity.cargo.factory.FactoryExample;
import com.itheima.mapper.cargo.factory.FactoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-13-19:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class FactoryMapperTest {

    @Autowired
    private FactoryMapper factoryMapper;

    @Test
    public void findOne(){
        Factory factory = factoryMapper.selectByPrimaryKey("1");
        System.out.println(factory);
    }

    @Test
    public void findAll(){
        FactoryExample example = new FactoryExample();
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andCtypeEqualTo("货物");
        criteria.andFactoryNameEqualTo("晶晨");

        List<Factory> factoryList = factoryMapper.selectByExample(example);

        for (Factory factory : factoryList) {
            System.out.println(factory);
        }

    }


}
