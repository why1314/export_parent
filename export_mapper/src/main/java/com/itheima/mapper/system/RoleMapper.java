package com.itheima.mapper.system;

import com.itheima.entity.system.Role;
import com.itheima.entity.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 详情 角色的持久层接口
 *
 * @author wz
 * @date 2019-05-07-10:12
 */
public interface RoleMapper {

    //根据id查询
    Role findById(String id);

    //查询全部用户
    List<Role> findAll(String companyId);

    //根据id删除
    int delete(String id);

    //添加
    int save(Role role);

    //更新
    int update(Role role);

    List<Map> findTreeJson(String roleid);

    //根据id删除角色和模块中间表数据
    void deleteRoleModule(String id);

    //向角色和模块中间表中添加数据
    void saveRoleModule(@Param("id") String id,@Param("moduleId") String moduleId);
    /**
     *所有的角色信息
     * @param companyId
     * @return
     */
    List<Role> findRoleListByCompanyId(String companyId);
}
