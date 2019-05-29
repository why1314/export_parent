package com.itheima.mapper.system;

import com.itheima.entity.system.SysLog;

import java.util.List;

public interface SysLogMapper {
    //查询全部
    List<SysLog> findAll(String companyId);

    //添加
    int save(SysLog log);
}