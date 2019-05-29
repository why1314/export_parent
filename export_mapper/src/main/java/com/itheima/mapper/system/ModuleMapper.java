package com.itheima.mapper.system;

import com.itheima.entity.system.Module;

import java.util.List;

/**
 * 详情 模块的持久层接口
 *
 * @author wz
 * @date 2019-05-07-10:12
 */
public interface ModuleMapper {

    //根据id查询
    Module findById(String id);

    //根据id删除
    int delete(String id);

    //添加用户
    int save(Module module);

    //更新用户
    int update(Module module);

    //查询全部
    List<Module> findAll();

    List<Module> findUserModules(String id);

    //根据belong查询模块信息
    List<Module> findModuleByBelong(int belong);
}
