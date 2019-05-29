package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.system.Role;
import com.itheima.entity.system.User;

import java.util.List;
import java.util.Map;

/**
 * 详情 角色的业务层接口
 *
 * @author wz
 * @date 2019-05-07-10:43
 */
public interface RoleService {
    //根据id查询
    Role findById(String id);

    //查询全部用户
    PageInfo findAll(String companyId,int page,int size);

    //根据id删除
    void delete(String id);

    //添加
    void save(Role role);

    //更新
    void update(Role role);

    List<Map> findTreeJson(String roleid);

    void updateRoleModule(String id, String moduleIds);

    /**
     * 查询当前公司的所有角色信息
     * @param companyId
     * @return
     */
    List<Role> findRoleListByCompanyId(String companyId);
}
