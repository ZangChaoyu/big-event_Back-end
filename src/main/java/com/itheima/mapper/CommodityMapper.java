package com.itheima.mapper;


import com.itheima.pojo.Commodity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommodityMapper {


    List<Commodity> list(Integer categoryId);
}
