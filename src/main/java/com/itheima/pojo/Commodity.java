package com.itheima.pojo;

import lombok.Data;

@Data
public class Commodity {
    private Integer id;
    private String username;
    private String context;
    private String time;
    private Integer likes;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl1l;
    private String imageUrl2l;
    private String imageUrl3l;
    private Integer categoryId;//分类id
}
