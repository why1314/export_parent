package com.itheima.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.stat.StatService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-21-18:20
 */
@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {


    @Reference
    private StatService statService;

    @RequestMapping("/toCharts")
    public String toCharts(String chartsType) {
        return "stat/stat-" + chartsType;
    }


    @RequestMapping("/getFactoryData")
    @ResponseBody
    public List<Map> getFactoryData() {

        return statService.findFactoryData(companyId);
    }


    @RequestMapping("/getSellData")
    @ResponseBody
    public List<Map> getSellData() {

        return statService.findSellData(companyId);
    }


    @RequestMapping("/getOnlineData")
    @ResponseBody
    public List<Map> getOnlineData() {

        return statService.findOnlineData(companyId);
    }
}
