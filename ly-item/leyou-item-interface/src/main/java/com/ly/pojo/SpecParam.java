package com.ly.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by HXin on 2019/4/6.
 */
@Table(name="tb_spec_param")
@Data
public class SpecParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    //numeric 为数据库关键字 可能造成错误  所以指定该字段名为numeric
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;
}
