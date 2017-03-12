package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

    List<Dict> selectByDictGroupCode(@Param("dictGroupCode") String dictGroupCode);
}