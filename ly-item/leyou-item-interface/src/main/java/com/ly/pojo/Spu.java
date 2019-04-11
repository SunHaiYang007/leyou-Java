package com.ly.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "tb_spu")
@Data
public class Spu {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    @Column(name = "brand_id")
    private Long brandId;
    private Long cid1;// 1级类目
    private Long cid2;// 2级类目
    private Long cid3;// 3级类目
    private String title;// 标题
    @Column(name = "sub_title")
    private String subTitle;// 子标题
    private Boolean saleable;// 是否上架
    private Boolean valid;// 是否有效，逻辑删除用
    private Date create_time;// 创建时间
    @JsonIgnore  //不返回
    private Date last_update_time;// 最后修改时间

    @Transient //不是数据库字段加上该注解
    private String  bname;//品牌名称
    @Transient
    private String cname;//分类名称
    @Transient
    private List<Sku> skus;
    @Transient
    private SpuDetail spuDetail;


}