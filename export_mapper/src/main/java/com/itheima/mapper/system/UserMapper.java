package com.itheima.mapper.system;

import com.itheima.entity.system.Module;
import com.itheima.entity.system.Role;
import com.itheima.entity.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 详情 用户的持久层接口
 *
 * @author wz
 * @date 2019-05-07-10:12
 */
public interface UserMapper {


    List<User> findAll(String companyId);

    User findById(String id);

    int update(User user);

    int save(User user);

    int delete(String id);

    //    List<Map> findUserRole(String id);
    List<String> findUserRole(String id);

    void deleteUserRoleById(String userid);

    void saveUserRole(@Param("userid") String userid, @Param("roleId") String roleId);

    User findByEmail(String email);

}
