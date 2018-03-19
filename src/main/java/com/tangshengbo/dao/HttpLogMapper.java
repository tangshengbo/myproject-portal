package com.tangshengbo.dao;

import com.tangshengbo.model.HttpLog;

import java.util.List;

public interface HttpLogMapper {
    /**
     *
     * @mbggenerated 2018-03-05
     */
    int deleteByPrimaryKey(Integer logId);

    /**
     *
     * @mbggenerated 2018-03-05
     */
    int insert(HttpLog record);

    /**
     *
     * @mbggenerated 2018-03-05
     */
    int insertSelective(HttpLog record);

    /**
     *
     * @mbggenerated 2018-03-05
     */
    HttpLog selectByPrimaryKey(Integer logId);

    /**
     *
     * @mbggenerated 2018-03-05
     */
    int updateByPrimaryKeySelective(HttpLog record);

    /**
     *
     * @mbggenerated 2018-03-05
     */
    int updateByPrimaryKey(HttpLog record);

    List<HttpLog> listHttpLog();

    List<HttpLog> listByNullAddress();

    void updateBatch(List<HttpLog> list);
}