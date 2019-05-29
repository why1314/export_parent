package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.system.Module;
import com.itheima.entity.system.Role;
import com.itheima.entity.system.User;

import java.util.List;
import java.util.Map;

/**
 * 详情 用户的业务层接口
 *
 * @author wz
 * @date 2019-05-07-10:43
 */
public interface UserService {
    /**
     * 查询那个公司下的所有用户,带分页，使用PageHelper
     *
     * @param companyId
     * @return
     */
    PageInfo findAll(String companyId, int page, int size);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    User findById(String id);

    /**
     * 对部门进行更新，需要先根据id查询出来才能更新
     *
     * @param user
     */
    void update(User user);

    /**
     * 新建部门
     *
     * @param user
     */
    void save(User user);

    /**
     * 删除指定id的用户
     *
     * @param id
     */
    void delete(String id);

    /**
     * 查询当前公司的所有员工信息
     *
     * @param companyId
     * @return
     */
    List<User> findAll(String companyId);


    /**
     * 查询当前用户所拥有的角色信息
     *
     * @param id
     * @return
     */
//    List<Map> findUserRole(String id);

    List<String> findUserRole(String id);


    /**
     * 1.删除指定用户在用户角色表中的数据
     * 2.向用户角色中间表中添加数据
     *
     * @param userid
     * @param roleIds
     */
    void changeRole(String userid, String roleIds);

    /**
     * 根据邮箱查询用户信息
     * @param email
     * @return
     */
    User findByEmail(String email);


    /**
     *查询该用户所拥有的模块
     * @param id
     * @return
     */
    List<Module> findUserModules(String id);
}
