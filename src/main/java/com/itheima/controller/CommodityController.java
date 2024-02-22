package com.itheima.controller;


import com.itheima.pojo.Article;
import com.itheima.pojo.Commodity;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;


    @GetMapping
    public Result<PageBean<Commodity>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false)String state
    ){
        PageBean<Commodity> pb = commodityService.list(pageNum,pageSize,categoryId);
        return Result.success(pb);
    }


}
