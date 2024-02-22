package com.itheima.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.CommodityMapper;

import com.itheima.pojo.Commodity;
import com.itheima.pojo.PageBean;
import com.itheima.service.CommodityService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public PageBean<Commodity> list(Integer pageNum, Integer pageSize, Integer categoryId) {
        PageBean<Commodity> pb = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //调用mapper 查询时只能查询到当前用户的文章
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer)map.get("id");
        List<Commodity> as = commodityMapper.list(categoryId);
        //Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页数据
        Page<Commodity> p = (Page<Commodity>) as;
        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
