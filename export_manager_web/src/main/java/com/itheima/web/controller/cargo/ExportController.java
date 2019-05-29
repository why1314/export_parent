package com.itheima.web.controller.cargo;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.BeanMapUtils;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.cargo.contract.ContractExample;
import com.itheima.entity.cargo.eproduct.ExportProduct;
import com.itheima.entity.cargo.eproduct.ExportProductExample;
import com.itheima.entity.cargo.export.Export;
import com.itheima.entity.cargo.export.ExportExample;
import com.itheima.entity.system.User;
import com.itheima.service.contract.ContractService;
import com.itheima.service.eproduct.ExportProductService;
import com.itheima.service.export.ExportService;
import com.itheima.vo.ExportProductVo;
import com.itheima.vo.ExportResult;
import com.itheima.vo.ExportVo;
import com.itheima.web.controller.BaseController;
import com.itheima.web.exceptions.CustomeException;
import com.sun.org.apache.bcel.internal.generic.NEW;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-18-11:41
 */
@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {


    @Reference
    private ContractService contractService;

    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;

    /**
     * 前往合同管理页面
     *
     * @return
     */
    @RequestMapping("/contractList")
    public String contractList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        //设置查询条件
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andCompanyIdEqualTo(companyId);

        //查询数据
        PageInfo pageInfo = contractService.findAll(example, page, size);

        //存入请求域中
        request.setAttribute("page", pageInfo);

        //转发到相应页面
        return "cargo/export/export-contractList";
    }

    /**
     * 前往新增报运页面
     *
     * @return
     */
    @RequestMapping("/toExport")
    public String toExport(String id) {

        request.setAttribute("id", id);

        return "cargo/export/export-toExport";
    }


    /**
     * 更新或添加报运单
     *
     * @param export
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Export export) {
        //设置企业信息
        export.setCompanyId(companyId);
        export.setCompanyName(companyName);

        if (UtilFuns.isEmpty(export.getId())) {

            //保存报运单
            exportService.save(export);
        } else {
            //更新报运单
            exportService.update(export);
        }

        return "redirect:/cargo/export/list.do";
    }

    /**
     * 报运单列表
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        //设置查询条件
        ExportExample example = new ExportExample();
        ExportExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);

        //查询数据
        PageInfo pageInfo = exportService.findAll(example, page, size);

        //存入请求域中
        request.setAttribute("page", pageInfo);

        //转发到相应页面
        return "cargo/export/export-list";
    }


    /**
     * 查看报运单信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/toView")
    public String toView(String id) {

        //查询报运单
        Export export = exportService.findById(id);

        //将信息存入请求域中
        request.setAttribute("export", export);

        //转发到相应页面
        return "cargo/export/export-view";
    }


    /**
     * 前往修改报运单信息
     * 前往携带信息
     * 包括：报运单信息以及报运商品信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        //查询报运单
        Export export = exportService.findById(id);

        //将信息存入请求域中
        request.setAttribute("export", export);

        //设置报运商品条件对象
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        //查询报运商品列表
        List<ExportProduct> exportProductList = exportProductService.findAll(example);

        //存入请求域中
        request.setAttribute("eps", exportProductList);

        //转发到相应页面
        return "cargo/export/export-update";
    }


    /***
     * jasper 填充
     * @param id
     * @throws Exception
     */
//    @RequestMapping("/exportPdf")
//    public void exportPdf(String id) throws Exception {
//
//
//        String path = session.getServletContext().getRealPath("/jasper/export04.jasper");
//        FileInputStream in = new FileInputStream(path);
//
//        ArrayList<User> list = new ArrayList<>();
//
//        for (int i = 0; i < 11; i++) {
//            User user = new User();
//            user.setUserName("why" + i);
//            user.setDeptName("why" + i);
//            user.setEmail("why" + i + "@qq.com");
//            user.setCompanyName("why" + i);
//            list.add(user);
//        }
//
//
//        JRDataSource dataSource = new JRBeanCollectionDataSource(list);
//
//        //创建pdf的输出对象
//        /**
//         * 创建pdf的输出对象
//         * 第一个参数：字节输入流，用于读取jasper文件
//         * 第二个参数：map集合，用于传递map集合的数据(多是表头数据)报运单数据
//         * 第三个参数：一个pdf的DataSource，内部封装了一个list集合，报运单内的商品信息
//         */
//        JasperPrint jasperPrint = JasperFillManager.fillReport(in, new HashMap<>(), dataSource);
//
//        //输出pdf文件
//        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//
//        //关流
//        in.close();
//
//    }


    /**
     *
     * jasper 传递Connection填充数据
     * @param id
     * @throws Exception
     */
//    @RequestMapping("/exportPdf")
//    public void exportPdf(String id) throws Exception {
//
//        String path = session.getServletContext().getRealPath("/jasper/export03.jasper");
//        FileInputStream in = new FileInputStream(path);
//
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/saas-export-ee88", "root", "root");
//
//        //创建pdf的输出对象
//        /**
//         * 创建pdf的输出对象
//         * 第一个参数：字节输入流，用于读取jasper文件
//         * 第二个参数：map集合，用于传递map集合的数据(多是表头数据)报运单数据
//         * 第三个参数：一个pdf的DataSource，内部封装了一个list集合，报运单内的商品信息
//         */
//        JasperPrint jasperPrint = JasperFillManager.fillReport(in, new HashMap<>(), conn);
//
//        //输出pdf文件
//        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//
//        //关流
//        in.close();
//
//    }


    /**
     * jasper入门之自定义map集合
     * @param id
     * @throws Exception
     */
//    @RequestMapping("/exportPdf")
//    public void exportPdf(String id) throws Exception {
//
//        String path = session.getServletContext().getRealPath("/jasper/export02.jasper");
//        FileInputStream in = new FileInputStream(path);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("userName","why1");
//        map.put("companyName","why2");
//        map.put("deptName","why3");
//        map.put("Email","why@qq.com");
//
//
//        //创建pdf的输出对象
//        /**
//         * 创建pdf的输出对象
//         * 第一个参数：字节输入流，用于读取jasper文件
//         * 第二个参数：map集合，用于传递map集合的数据(多是表头数据)报运单数据
//         * 第三个参数：一个pdf的DataSource，内部封装了一个list集合，报运单内的商品信息
//         */
//        JasperPrint jasperPrint = JasperFillManager.fillReport(in, map, new JREmptyDataSource());
//
//        //输出pdf文件
//        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//
//        //关流
//        in.close();
//
//
//    }


    /**
     * jasper的入门案例
     * @param id
     * @throws Exception
     */
//    @RequestMapping("/exportPdf")
//    public void exportPdf(String id) throws Exception {
//
//        String path = session.getServletContext().getRealPath("/jasper/export01.jasper");
//        FileInputStream in = new FileInputStream(path);
//
//
//
//        //创建pdf的输出对象
//        /**
//         * 创建pdf的输出对象
//         * 第一个参数：字节输入流，用于读取jasper文件
//         * 第二个参数：map集合，用于传递map集合的数据(多是表头数据)报运单数据
//         * 第三个参数：一个pdf的DataSource，内部封装了一个list集合，报运单内的商品信息
//         */
//        JasperPrint jasperPrint = JasperFillManager.fillReport(in, new HashMap<>(), new JREmptyDataSource());
//
//        //输出pdf文件
//        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//
//        //关流
//        in.close();
//
//
//    }


    /**
     * 生成报运单的pdf文件
     *
     * @param id 报运单id
     */
    @RequestMapping("/exportPdf")
    public void exportPdf(String id) throws Exception {

        String path = session.getServletContext().getRealPath("/jasper/export.jasper");
        FileInputStream in = new FileInputStream(path);

        //查询报运单
        Export export = exportService.findById(id);

        //将export对象转换为map集合
        Map<String, Object> map = BeanMapUtils.beanToMap(export);

        //使用报运单id查询报运单下的商品
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> list = exportProductService.findAll(example);

        //创建pdf的输出对象
        /**
         * 创建pdf的输出对象
         * 第一个参数：字节输入流，用于读取jasper文件
         * 第二个参数：map集合，用于传递map集合的数据(多是表头数据)报运单数据
         * 第三个参数：一个pdf的DataSource，内部封装了一个list集合，报运单内的商品信息
         */
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, map, new JRBeanCollectionDataSource(list));

        //输出pdf文件
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        //关流
        in.close();


    }


    /**
     * 更新报运单状态为：以上报
     *
     * @param id
     * @return
     */
    @RequestMapping("/submit")
    public String submit(String id) {
        //创建报运单对象
        Export export = new Export();
        //设置报运单id
        export.setId(id);
        //设置报运单状态
        export.setState(1);

        //更新状态
        exportService.update(export);


        return "redirect:/cargo/export/list.do";
    }


    /**
     * 更新报运单状态为：草稿
     *
     * @param id
     * @return
     */
    @RequestMapping("/cancel")
    public String cancel(String id) {
        //创建报运单对象
        Export export = new Export();
        //设置报运单id
        export.setId(id);
        //设置报运单状态
        export.setState(0);

        //更新状态
        exportService.update(export);


        return "redirect:/cargo/export/list.do";
    }


    /**
     * 删除报运单
     * 包括报运单下面的商品、附件
     * 同时更改购销合同的状态   已报运 改为 已上报
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id) {

        exportService.delete(id);

        return "redirect:/cargo/export/list.do";
    }

    /**
     * 电子报运
     *
     * @param id 报运单id
     * @return
     */
    @RequestMapping("/exportE")
    public String exportE(String id) throws CustomeException {
        //根据id查询报运单
        Export export = exportService.findById(id);

        if (export.getState() != 1) {
            throw new CustomeException("请选择符合条件的报运单报运");
        }

        //将报运单数据复制到exportVo中
        ExportVo evo = new ExportVo();
        BeanUtils.copyProperties(export, evo, new String[]{"id"});

        //设置海关平台报运单的id和exportId
        String eid = UtilFuns.generatedId();//后期需要使用
        evo.setId(eid);
        //设置evo中的exportId
        evo.setExportId(export.getId());

        //获取报运单下的索引商品
        //设置查询条件
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(example);
        //遍历eps，取出每个商品，用于构建海关报运单的商品对象
        for (ExportProduct ep : eps) {
            //创建海关报运单的商品对象
            ExportProductVo vo = new ExportProductVo();
            //将数据复制到vo对象中
            BeanUtils.copyProperties(ep, vo, new String[]{"id"});

            //设置海关报运商品对象的id
            vo.setId(UtilFuns.generatedId());
            //海关报运单的id
            vo.setEid(eid);
            //saas平台的报运单id以及报运商品id
            vo.setExportId(export.getId());
            vo.setExportProductId(ep.getId());

            evo.getProducts().add(vo);
        }


        //发送请求
        WebClient client = WebClient.create("http://localhost:8480/ws/export/user");
        client.type(MediaType.APPLICATION_XML).post(evo);

        //再次发送请求，看看数据是否送达没有

        WebClient webClient = WebClient.create("http://localhost:8480/ws/export/user/" + eid);
        ExportResult exportResult = webClient.accept(MediaType.APPLICATION_XML).get(ExportResult.class);

        //把响应结果更新到数据库
        exportService.updateER(exportResult);

        return "redirect:/cargo/export/list.do";
    }

}
