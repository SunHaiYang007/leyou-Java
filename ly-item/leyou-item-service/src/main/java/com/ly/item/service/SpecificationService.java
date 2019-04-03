package com.ly.item.service;

import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.item.mapper.SpecificationMapper;
import com.ly.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by HXin on 2019/4/3.
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecificationMapper specificationGroupMapper;
    public List<Specification> querySpecByCid(Long cid) {
        Specification spec = new Specification();
        spec.setCategoryId(cid);
        List<Specification> list = specificationGroupMapper.select(spec);
        if (CollectionUtils.isEmpty(list)){
            throw new MyException(MyExceptionEnums.PRICE_CAN_NOT_BE_NULL);
        }
        return list;
    }
}
