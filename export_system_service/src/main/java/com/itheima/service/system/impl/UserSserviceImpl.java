package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.Encrypt;
import com.itheima.entity.system.Module;
import com.itheima.entity.system.Role;
import com.itheima.entity.system.User;
import com.itheima.mapper.system.ModuleMapper;
import com.itheima.mapper.system.RoleMapper;
import com.itheima.mapper.system.UserMapper;
import com.itheima.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 详情 用户的业务层实现
 *
 * @author wz
 * @date 2019-05-07-17:08
 */
@Service("userService")
public class UserSserviceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private JmsTemplate jmsQueueTemplate;



    public PageInfo findAll(String companyId, int page, int size) {
        //设置分页信息
        PageHelper.startPage(page, size);
        //查询该公司下所有的用户
        List<User> userList = userMapper.findAll(companyId);
        //返回
        return new PageInfo(userList);
    }

    public List<User> findAll(String companyId) {

        return userMapper.findAll(companyId);
    }

    public User findById(String id) {

        return userMapper.findById(id);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public void save(User user) {
        String userId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String password = user.getPassword();
        String md5Password = Encrypt.md5(password, user.getEmail());
        //需要对密码进行加密
        user.setPassword(md5Password);
        user.setId(userId);
        userMapper.save(user);


        //往消息中间件中写消息
        //生产消费模型
        jmsQueueTemplate.send("export_queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("email", user.getEmail());
                message.setString("userName", user.getUserName());
                message.setString("companyName", user.getCompanyName());
                message.setString("password", password);

                return message;
            }
        });

    }

    public void delete(String id) {
        userMapper.delete(id);
    }


//    @Override
//    public List<Map> findUserRole(String id) {
//
//        return userMapper.findUserRole(id);
//    }

    public List<String> findUserRole(String id) {

        return userMapper.findUserRole(id);
    }

    public void changeRole(String userid, String roleIds) {

        //
        userMapper.deleteUserRoleById(userid);

//        System.out.println(roleIds);
        //把moduleIds转换为String[]
        String[] strings = roleIds.split(",");
        for (String roleId : strings) {
            userMapper.saveUserRole(userid, roleId);

        }

    }

    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }


    public List<Module> findUserModules(String id) {

        //根据id查询用户
        User user = userMapper.findById(id);
        //saas平台管理员
        if (user.getDegree() == 0) {
            return moduleMapper.findModuleByBelong(0);
            //企业系统管理员
        } else if (user.getDegree() == 1) {
            return moduleMapper.findModuleByBelong(1);
        } else {
            return moduleMapper.findUserModules(id);
        }

//        return moduleMapper.findUserModules(id);
    }
}
