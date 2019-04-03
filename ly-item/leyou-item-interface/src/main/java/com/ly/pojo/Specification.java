package com.ly.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by HXin on 2019/4/3.
 */
@Table(name="tb_specification")
@Data
public class Specification {
    @Id
    private Long categoryId;
    private String specifications;
}
