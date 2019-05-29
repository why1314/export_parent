package com.itheima.service.export.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.cargo.contract.Contract;
import com.itheima.entity.cargo.cproduct.ContractProduct;
import com.itheima.entity.cargo.cproduct.ContractProductExample;
import com.itheima.entity.cargo.eproduct.ExportProduct;
import com.itheima.entity.cargo.eproduct.ExportProductExample;
import com.itheima.entity.cargo.export.Export;
import com.itheima.entity.cargo.export.ExportExample;
import com.itheima.entity.cargo.extc.ExtCproduct;
import com.itheima.entity.cargo.exte.ExtEpoduct;
import com.itheima.entity.cargo.exte.ExtEpoductExample;
import com.itheima.mapper.cargo.contract.ContractMapper;
import com.itheima.mapper.cargo.eproduct.ExportProductMapper;
import com.itheima.mapper.cargo.export.ExportMapper;
import com.itheima.mapper.cargo.exte.ExtEpoductMapper;
import com.itheima.mapper.cargo.product.ContractProductMapper;
import com.itheima.service.export.ExportService;
import com.itheima.service.product.ContractProductService;
import com.itheima.vo.ExportProductResult;
import com.itheima.vo.ExportResult;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.math3.analysis.function.Exp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportMapper exportMapper;

    @Autowired
    private ContractMapper contractMapper;


    @Autowired
    private ContractProductMapper contractProductMapper;

    @Autowired
    private ExportProductMapper exportProductMapper;

    @Autowired
    private ExtEpoductMapper extEpoductMapper;

    @Override
    public void save(Export export) {

        //补全报运单信息
        //报运单id
        String exportId = UtilFuns.generatedId();
        export.setId(exportId);
        //报运单创建时间
        export.setInputDate(new Date());
        //报运单状态
        export.setState(0);

        //取出报运单下的合同id集合
        String[] ids = export.getContractIds().split(" ");

        //1.查询合同信息
        StringBuilder ss = new StringBuilder();
        for (String id : ids) {
            Contract contract = contractMapper.selectByPrimaryKey(id);
            //拼接购销合同号
            ss.append(contract.getContractNo()).append(" ");

            //报运后合同状态改为
            contract.setState(2);
            contractMapper.updateByPrimaryKeySelective(contract);
        }
        //设置报运单合同号
        ss.delete(ss.toString().length() - 1, ss.toString().length());
        export.setCustomerContract(ss.toString());

        //取出购销合同中的货物信息
        ContractProductExample contractProductExample = new ContractProductExample();
        ContractProductExample.Criteria contractProductExampleCriteria = contractProductExample.createCriteria();

        //把合同id数组转为集合
        List<String> contractIdList = Arrays.asList(ids);
        //拼装查询条件
        contractProductExampleCriteria.andContractIdIn(contractIdList);
        List<ContractProduct> contractProducts = contractProductMapper.selectByExample(contractProductExample);
        int exteNum = 0;
        //遍历合同下货物的集合
        for (ContractProduct product : contractProducts) {
            //创建报运单商品对象
            ExportProduct exportProduct = new ExportProduct();
            BeanUtils.copyProperties(product, exportProduct, new String[]{"id"});
            //设置报运商品id，稍后附件需要
            String exportProductId = UtilFuns.generatedId();
            exportProduct.setId(exportProductId);
            exportProduct.setExportId(exportId);
            //添加报运单商品
            exportProductMapper.insertSelective(exportProduct);

            List<ExtCproduct> extcs = product.getExtCproducts();
            //附件总数
            exteNum += extcs.size();

            for (ExtCproduct extc : extcs) {
                //创建报运单附件对象
                ExtEpoduct extEpoduct = new ExtEpoduct();

                BeanUtils.copyProperties(extc, extEpoduct, new String[]{"id"});
                String exteId = UtilFuns.generatedId();
                //设置报运附件id
                extEpoduct.setId(exportId);
                //设置报运附件所属报运商品id
                extEpoduct.setExportProductId(exportProductId);
                //设置报运附件所属报运单id
                extEpoduct.setExportId(exportId);
                //保存报运附件信息
                extEpoductMapper.insertSelective(extEpoduct);
            }
        }
        export.setProNum(contractProducts.size());
        export.setExtNum(exteNum);

        //保存报运单
        exportMapper.insertSelective(export);


    }

    @Override
    public void delete(String id) {

        //查询报运单信息
        Export export = exportMapper.selectByPrimaryKey(id);

        //删除报运单
        exportMapper.deleteByPrimaryKey(id);

        //设置报运商品查询条件
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);

        //查询报运商品信息
        List<ExportProduct> exportProducts = exportProductMapper.selectByExample(exportProductExample);

        for (ExportProduct exportProduct : exportProducts) {
            //删除商品信息
            exportProductMapper.deleteByPrimaryKey(exportProduct.getId());
        }

        //创建报运附件的查询条件对象
        ExtEpoductExample extEpoductExample = new ExtEpoductExample();
        extEpoductExample.createCriteria().andExportIdEqualTo(id);

        //查询报运附件信息
        List<ExtEpoduct> extEpoducts = extEpoductMapper.selectByExample(extEpoductExample);
        for (ExtEpoduct extEpoduct : extEpoducts) {
            //删除附件信息
            extEpoductMapper.deleteByPrimaryKey(extEpoduct.getId());

        }

        //更新购销合同状态

        //获取该报运单下的购销合同id（利用打断设计，将合同的id放到主表报运单中）
        String contractIds = export.getContractIds();
        List<String> ids = Arrays.asList(contractIds.split(","));
        for (String contractId : ids) {
            Contract contract = new Contract();
            contract.setId(contractId);
            contract.setState(1);
            contractMapper.updateByPrimaryKeySelective(contract);
        }


    }

    @Override
    public void update(Export export) {

        //更新报运单
        exportMapper.updateByPrimaryKeySelective(export);

        //更新报运单商品信息
        List<ExportProduct> list = export.getExportProducts();

        if (list != null) {
            for (ExportProduct exportProduct : list) {
                exportProductMapper.updateByPrimaryKeySelective(exportProduct);
            }

        }
    }


    @Override
    public Export findById(String id) {
        return exportMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        //设置分页信息
        PageHelper.startPage(page, size);

        //查询数据
        List<Export> list = exportMapper.selectByExample(example);

        return new PageInfo(list);
    }


    @Override
    public void updateER(ExportResult er) {

        //查询报运单
        Export export = exportMapper.selectByPrimaryKey(er.getExportId());

        //设置报运单状态和remark
        export.setState(er.getState());
        export.setRemark(er.getRemark());

        //取出商品集合
        Set<ExportProductResult> epr = er.getProducts();
        //遍历商品，取出每个商品的id
        for (ExportProductResult exportProductResult : epr) {
            ExportProduct exportProduct = exportProductMapper.selectByPrimaryKey(exportProductResult.getExportProductId());

            //设置每个商品的税率
            exportProduct.setTax(exportProductResult.getTax());

            //更新报运商品的税率
            exportProductMapper.updateByPrimaryKeySelective(exportProduct);

        }

        //更新报运单
        exportMapper.updateByPrimaryKeySelective(export);
    }
}
