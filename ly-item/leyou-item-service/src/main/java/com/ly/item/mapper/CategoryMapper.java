package com.ly.item.mapper;

import com.ly.pojo.Category;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by HXin on 2019/1/18.
 */
@Repository
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category,Long> {
}
