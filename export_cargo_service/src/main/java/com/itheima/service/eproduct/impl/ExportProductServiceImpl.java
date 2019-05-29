package com.itheima.service.eproduct.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.cargo.eproduct.ExportProduct;
import com.itheima.entity.cargo.eproduct.ExportProductExample;
import com.itheima.mapper.cargo.eproduct.ExportProductMapper;
import com.itheima.service.eproduct.ExportProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class ExportProductServiceImpl implements ExportProductService {


    @Autowired
    private ExportProductMapper exportProductMapper;

    @Override
    public ExportProduct findById(String id) {
        return exportProductMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(ExportProduct exportProduct) {

        exportProductMapper.insertSelective(exportProduct);
    }

    @Override
    public void update(ExportProduct exportProduct) {
        exportProductMapper.updateByPrimaryKeySelective(exportProduct);
    }

    @Override
    public void delete(String id) {
        exportProductMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ExportProduct> findAll(ExportProductExample example) {
        return exportProductMapper.selectByExample(example);
    }

}
