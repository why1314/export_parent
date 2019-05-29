package com.itheima.service.contract;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.cargo.contract.Contract;
import com.itheima.entity.cargo.contract.ContractExample;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-13-23:17
 */
public interface ContractService {

    /**
     * 购销合同的查询，只查询合同状态唯一提交的数据
     *
     * @param example
     * @param page
     * @param size
     * @return
     */
    PageInfo<Contract> findAll(ContractExample example, int page, int size);

    /**
     * 根据id查询相关合同信息
     * @param id
     * @return
     */
    Contract findContractById(String id);

    /**
     * 新建合同
     * @param contract
     */
    void save(Contract contract);

    /**
     * 修改合同信息
     * @param contract
     */
    void update(Contract contract);

    /**
     * 将合同状态设为草稿
     * @param id
     */
    void delete(String id);
}
