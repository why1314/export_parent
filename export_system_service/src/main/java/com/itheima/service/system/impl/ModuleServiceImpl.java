package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.system.Module;
import com.itheima.mapper.system.ModuleMapper;
import com.itheima.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-08-17:15
 */
@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;
    public Module findById(String id) {
        return moduleMapper.findById(id);
    }

    public void delete(String id) {
        moduleMapper.delete(id);
    }

    public void save(Module module) {

        //设置id
        module.setId(UtilFuns.generatedId());

        moduleMapper.save(module);

    }

    public void update(Module module) {

        moduleMapper.update(module);
    }

    public PageInfo findAll(int page, int size) {

        //设置分页信息
        PageHelper.startPage(page,size);

        //查询数据
        List<Module> moduleList = moduleMapper.findAll();

        //返回
        return new PageInfo(moduleList);
    }

    public List<Module> findAll() {
        return moduleMapper.findAll();
    }


}
