package com.ly.item.service;

import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.item.mapper.SpecGroupMapper;
import com.ly.item.mapper.SpecParamMapper;
import com.ly.pojo.SpecGroup;
import com.ly.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by HXin on 2019/4/3.
 */
@Service
@Transactional
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> querySpecByCid(Long cid) {
        SpecGroup spec = new SpecGroup();
        spec.setCid(cid);
        List<SpecGroup> list = specGroupMapper.select(spec);
        if (CollectionUtils.isEmpty(list)){
            throw new MyException(MyExceptionEnums.SPECIFICATION_GROUP_IS_NOT_FOUND);
        }
        return list;
    }

    public List<SpecParam> queryParamByGid(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        param.setGeneric(generic);
        List<SpecParam> list = specParamMapper.select(param);
        if (CollectionUtils.isEmpty(list)){
            throw new MyException(MyExceptionEnums.SPECIFICATION_PARAM_IS_NOT_FOUND);
        }
        return list;
    }
}
