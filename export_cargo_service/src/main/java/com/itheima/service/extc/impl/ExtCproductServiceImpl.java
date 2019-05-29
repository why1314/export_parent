package com.itheima.service.extc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.cargo.contract.Contract;
import com.itheima.entity.cargo.extc.ExtCproduct;
import com.itheima.entity.cargo.extc.ExtCproductExample;
import com.itheima.mapper.cargo.contract.ContractMapper;
import com.itheima.mapper.cargo.extc.ExtCproductMapper;
import com.itheima.service.extc.ExtCproductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-15-20:58
 */
@Service
public class ExtCproductServiceImpl implements ExtCproductService {

    @Autowired
    private ExtCproductMapper extCproductMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public void save(ExtCproduct extCproduct) {
        //生成附件id
        extCproduct.setId(UtilFuns.generatedId());

        //获取附件总金额
        double amount = 0;
        if (extCproduct.getPrice() != null && extCproduct.getCnumber() != null) {
            amount = extCproduct.getPrice() * extCproduct.getCnumber();
        }
        //设置附件总金额
        extCproduct.setAmount(amount);
        //添加附件
        extCproductMapper.insertSelective(extCproduct);

        //获取合同该附近所属合同信息
        Contract contract = contractMapper.selectByPrimaryKey(extCproduct.getContractId());

        //附件数+1
        contract.setExtNum(contract.getExtNum() + 1);
        //设置合同总金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);

        contractMapper.updateByPrimaryKeySelective(contract);

    }

    @Override
    public void update(ExtCproduct extCproduct) {

        //取出未修改时附件的总金额
        ExtCproduct cproduct = extCproductMapper.selectByPrimaryKey(extCproduct.getId());
        Double dbAmount = cproduct.getAmount();

        double amount = 0;
        if (extCproduct.getPrice() != null && extCproduct.getCnumber() != null) {
            amount = extCproduct.getPrice() * extCproduct.getCnumber();
        }
        //设置附件总金额
        extCproduct.setAmount(amount);
        //添加附件
        extCproductMapper.updateByPrimaryKeySelective(extCproduct);

        //获取合同该附近所属合同信息
        Contract contract = contractMapper.selectByPrimaryKey(extCproduct.getContractId());

        //设置合同总金额
        contract.setTotalAmount(contract.getTotalAmount() - dbAmount + amount);

        contractMapper.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        //先取出附件信息
        ExtCproduct extCproduct = extCproductMapper.selectByPrimaryKey(id);
        Double dbAmount = extCproduct.getAmount();
        //删除该附件
        extCproductMapper.deleteByPrimaryKey(id);
        //查询该附近所属合同信息
        Contract contract = contractMapper.selectByPrimaryKey(extCproduct.getContractId());

        //设置删除附件后合同的总金额以及附件数量
        contract.setTotalAmount(contract.getTotalAmount()-dbAmount);
        contract.setExtNum(contract.getExtNum()-1);

        contractMapper.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        PageHelper.startPage(page, size);

        List<ExtCproduct> extCproductList = extCproductMapper.selectByExample(example);

        return new PageInfo(extCproductList);
    }
}
