package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.system.Module;
import com.itheima.entity.system.Role;

import java.util.List;

/**
 * 详情 模块的业务层接口
 *
 * @author wz
 * @date 2019-05-07-10:43
 */
public interface ModuleService {
    //根据id查询
    Module findById(String moduleId);

    //根据id删除
    void delete(String moduleId);

    //添加用户
    void save(Module module);

    //更新用户
    void update(Module module);

    //查询全部
    PageInfo findAll(int page, int size);

    List<Module> findAll();


}
