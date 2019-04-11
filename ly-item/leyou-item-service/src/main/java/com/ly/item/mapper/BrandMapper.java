package com.ly.item.mapper;

import com.ly.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by HXin on 2019/4/1.
 */
@Repository
public interface BrandMapper extends Mapper<Brand> {

    @Insert("insert into tb_category_brand values(#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid")Long cid,@Param("bid")Long bid);

    @Select("select b.* from tb_brand b,tb_category_brand cb where b.id = cb.brand_id and cb.category_id = #{cid}" )
    List<Brand> queryBrandByCid(@Param("cid") Long cid);
}
