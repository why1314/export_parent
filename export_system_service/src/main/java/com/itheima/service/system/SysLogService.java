package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.system.SysLog;

import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-09-17:47
 */
public interface SysLogService {

    /**
     * 查询系统日志带分页
     * @param
     * @return
     */
    PageInfo findAll(String companyId,int page,int size);

    /**
     * 保存系统日志
     * @param log
     * @return
     */
    void save(SysLog log);
}
