package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.system.SysLog;
import com.itheima.mapper.system.SysLogMapper;
import com.itheima.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-09-17:48
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;


    public PageInfo findAll(String companyId, int page, int size) {
        //设置分页信息
        PageHelper.startPage(page, size);

        //查询所有
        List<SysLog> list = sysLogMapper.findAll(companyId);
        return new PageInfo(list);
    }

    public void save(SysLog log) {
        //设置id
        log.setId(UtilFuns.generatedId());
        //保存
        sysLogMapper.save(log);
    }
}
