package com.ly.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by HXin on 2019/4/11.
 */
@Table(name = "tb_sku")
@Data
public class Sku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long spuId;
    private String title;
    private String images;
    private Long price;
    private String ownSpec;// 商品特殊规格的键值对
    private String indexes;// 商品特殊规格的下标
    private Boolean enable;// 是否有效，逻辑删除用
    private Date create_time;// 创建时间
    private Date last_update_time;// 最后修改时间
    @Transient
    private Integer stock;// 库存
}
