package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.cargo.extc.ExtCproduct;
import com.itheima.entity.cargo.extc.ExtCproductExample;
import com.itheima.entity.cargo.factory.Factory;
import com.itheima.entity.cargo.factory.FactoryExample;
import com.itheima.service.extc.ExtCproductService;
import com.itheima.service.factory.FactoryService;
import com.itheima.web.controller.BaseController;
import com.itheima.web.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-15-20:30
 */
@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {

    @Reference
    private ExtCproductService extCproductService;

    @Reference
    private FactoryService factoryService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * 前往附件的列表页面
     *
     * @param contractId
     * @param contractProductId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(String contractId, String contractProductId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        //查询商品下的所有附件信息
        //设置查询条件
        ExtCproductExample example = new ExtCproductExample();
        example.createCriteria().andContractProductIdEqualTo(contractProductId);

        PageInfo pageInfo = extCproductService.findAll(example, page, size);

        //附件信息列表存入请求域中
        request.setAttribute("page", pageInfo);

        //获得生产货物厂家的信息
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");

        //获取生产附件的厂家信息
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        //生产附件的厂家存入请求域中
        request.setAttribute("factoryList", factoryList);

        //合同id与货物id存入请求域
        request.setAttribute("contractProductId", contractProductId);
        //合同id是为了后续对附件进行增删改操作时对合同也进行改变
        request.setAttribute("contractId", contractId);

        //转发到相应页面
        return "cargo/extc/extc-list";
    }


    /**
     * 请问附件更新页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //查询附件信息
        ExtCproduct extCproduct = extCproductService.findById(id);
        //将附件信息存入请求域中
        request.setAttribute("extCproduct", extCproduct);

        //获得生产货物厂家的信息
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");

        //获取生产附件的厂家信息
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        //生产附件的厂家存入请求域中
        request.setAttribute("factoryList", factoryList);

        return "cargo/extc/extc-update";
    }

    /**
     * 保存或更新附件信息
     *
     * @param extCproduct
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ExtCproduct extCproduct, MultipartFile productPhoto) throws Exception {
        //设置附件所属公司信息
        extCproduct.setCompanyId(companyId);
        extCproduct.setCompanyName(companyName);
        //根据附件id是否存在进行更新或添加操作
        if (UtilFuns.isEmpty(extCproduct.getId())) {
            //判断上传文件是否为空
            String imgPath="";
            if (productPhoto != null) {

                imgPath = "http://" + fileUploadUtil.upload(productPhoto);
            }
            //设置上传附件图片的路径
            extCproduct.setProductImage(imgPath);
            extCproductService.save(extCproduct);
        } else {
            extCproductService.update(extCproduct);
        }

        //重定向
        return "redirect:/cargo/extCproduct/list.do?contractId=" + extCproduct.getContractId() + "&contractProductId=" + extCproduct.getContractProductId();
    }


    @RequestMapping("/delete")
    public String delete(String id, String contractId, String contractProductId) {

        //删除指定附件信息
        extCproductService.delete(id);
        //重定向
        return "redirect:/cargo/extCproduct/list.do?contractId=" + contractId + "&contractProductId=" + contractProductId;
    }


}
