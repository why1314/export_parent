package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.system.Role;
import com.itheima.mapper.system.RoleMapper;
import com.itheima.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-08-15:47
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public Role findById(String id) {
        return roleMapper.findById(id);
    }

    public PageInfo findAll(String companyId, int page, int size) {

        //设置分页信息
        PageHelper.startPage(page, size);

        //查询该公司下所有角色
        List<Role> roleList = roleMapper.findAll(companyId);
        //返回结果
        return new PageInfo(roleList);
    }

    public void delete(String id) {

        roleMapper.delete(id);

    }

    public void save(Role role) {

        //设置id
        role.setId(UtilFuns.generatedId());

        roleMapper.save(role);
    }

    public void update(Role role) {

        roleMapper.update(role);
    }


    public List<Map> findTreeJson(String roleid) {
        return roleMapper.findTreeJson(roleid);
    }


    public void updateRoleModule(String id, String moduleIds) {
        //先使用id删除中间表数据
        roleMapper.deleteRoleModule(id);

        //把moduleIds转换为String[]
        String[] strings = moduleIds.split(",");
        for (String moduleId : strings) {
            roleMapper.saveRoleModule(id, moduleId);

        }
    }


    public List<Role> findRoleListByCompanyId(String companyId) {
        return roleMapper.findRoleListByCompanyId(companyId);
    }
}
