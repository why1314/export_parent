package com.itheima.service.product.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.cargo.contract.Contract;
import com.itheima.entity.cargo.extc.ExtCproduct;
import com.itheima.entity.cargo.extc.ExtCproductExample;
import com.itheima.entity.cargo.cproduct.ContractProduct;
import com.itheima.entity.cargo.cproduct.ContractProductExample;
import com.itheima.mapper.cargo.contract.ContractMapper;
import com.itheima.mapper.cargo.extc.ExtCproductMapper;
import com.itheima.mapper.cargo.product.ContractProductMapper;
import com.itheima.service.product.ContractProductService;
import com.itheima.vo.ContractProductVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-15-14:48
 */
@Service
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductMapper contractProductMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ExtCproductMapper extCproductMapper;

    @Override
    public void save(ContractProduct contractProduct) {
        //设置货物的id
        contractProduct.setId(UtilFuns.generatedId());
        double amount = 0d;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
            contractProduct.setAmount(amount);//当前货物的总金额
        }

        //保存货物
        contractProductMapper.insertSelective(contractProduct);

        //修改合同总金额
        //先查询货物所属合同
        Contract contract = contractMapper.selectByPrimaryKey(contractProduct.getContractId());
        //设置合同总金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);

        //设置合同中货物类数量
        contract.setProNum(contract.getProNum() + 1);
        //更新合同信息
        contractMapper.updateByPrimaryKeySelective(contract);

    }

    @Override
    public void update(ContractProduct contractProduct) {

        double amount = 0d;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
            contractProduct.setAmount(amount);//当前货物的总金额
        }
        ContractProduct product = contractProductMapper.selectByPrimaryKey(contractProduct.getId());
        Double oldAmount = product.getAmount();

        //保存货物
        contractProductMapper.updateByPrimaryKeySelective(contractProduct);

        //修改合同总金额
        //先查询货物所属合同
        Contract contract = contractMapper.selectByPrimaryKey(contractProduct.getContractId());
        //更新合同总金额         合同中原来金额-货物原来金额+货物现在金额
        contract.setTotalAmount(contract.getTotalAmount() - oldAmount + amount);

        //更新合同信息
        contractMapper.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {

        //查询该商品xinx
        ContractProduct contractProduct = contractProductMapper.selectByPrimaryKey(id);
        Double ProAmount = contractProduct.getAmount();
        //先查询该商品下的所有附件
        ExtCproductExample example = new ExtCproductExample();
        example.createCriteria().andContractProductIdEqualTo(id);

        List<ExtCproduct> extCproducts = extCproductMapper.selectByExample(example);
        //获得所有附件金额的综合，同时便利删除该商品下所有附件信息
        double dbExtcAmount = 0;
        for (ExtCproduct extCproduct : extCproducts) {
            dbExtcAmount += extCproduct.getAmount();
            //删除附件
            extCproductMapper.deleteByPrimaryKey(extCproduct.getId());
        }

        //删除商品
        contractProductMapper.deleteByPrimaryKey(id);

        //获得该商品所属合同的信息
        Contract contract = contractMapper.selectByPrimaryKey(contractProduct.getContractId());
        //设置合同总金额
        contract.setTotalAmount(contract.getTotalAmount() - ProAmount - dbExtcAmount);

        //设置该合同下的商品以及附件数
        contract.setProNum(contract.getProNum() - 1);
        contract.setExtNum(contract.getExtNum() - extCproducts.size());
        //更新合同信息
        contractMapper.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(String contractId, int page, int size) {

        //设置分页信息
        PageHelper.startPage(page, size);

        //设置查询条件
        ContractProductExample example = new ContractProductExample();
        ContractProductExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(contractId);

        //查询相关数据
        List<ContractProduct> products = contractProductMapper.selectByExample(example);

        return new PageInfo(products);
    }


    @Override
    public List<ContractProductVo> findCproductByShipTime(String companyId, String inputDate) {
        return contractProductMapper.findCproductByShipTime(companyId,inputDate);
    }

    @Override
    public void batchSave(List<ContractProduct> productList) {
        for (ContractProduct product : productList) {
            this.save(product);
        }
    }
}
