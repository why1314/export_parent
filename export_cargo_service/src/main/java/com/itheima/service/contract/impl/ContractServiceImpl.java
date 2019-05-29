package com.itheima.service.contract.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.cargo.contract.Contract;
import com.itheima.entity.cargo.contract.ContractExample;
import com.itheima.mapper.cargo.contract.ContractMapper;
import com.itheima.service.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


/**
 * 详情
 *
 * @author wz
 * @date 2019-05-13-23:18
 */


@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public PageInfo<Contract> findAll(ContractExample example, int page, int size) {

        //设置分页查询信息
        PageHelper.startPage(page, size);

        List<Contract> list = contractMapper.selectByExample(example);

        return new PageInfo<>(list);
    }

    @Override
    public Contract findContractById(String id) {
        Contract contract = contractMapper.selectByPrimaryKey(id);
        return contract;
    }


    @Override
    public void save(Contract contract) {
        //设置id
        String cid = UtilFuns.generatedId();
        contract.setId(cid);

        //设置合同总金额
        contract.setTotalAmount(0d);
        //设置合同状态 0：草稿 1：已上报 2：以报运
        contract.setState(0);
        //设置货物数和附件数
        contract.setProNum(0);
        contract.setExtNum(0);
        //合同创建时间
        contract.setCreateTime(new Date());

        //添加
        contractMapper.insertSelective(contract);
    }

    @Override
    public void update(Contract contract) {
        contractMapper.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        contractMapper.deleteByPrimaryKey(id);

    }
}
