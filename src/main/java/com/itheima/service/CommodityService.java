package com.itheima.service;

import com.itheima.pojo.Commodity;
import com.itheima.pojo.PageBean;

public interface CommodityService {
    PageBean<Commodity> list(Integer pageNum, Integer pageSize, Integer categoryId);

}
