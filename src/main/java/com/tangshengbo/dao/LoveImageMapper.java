package com.tangshengbo.dao;

import com.tangshengbo.model.LoveImage;

import java.util.List;

public interface LoveImageMapper {
    /**
     *
     * @mbggenerated 2018-03-06
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-03-06
     */
    int insert(LoveImage record);

    /**
     *
     * @mbggenerated 2018-03-06
     */
    int insertSelective(LoveImage record);

    /**
     *
     * @mbggenerated 2018-03-06
     */
    LoveImage selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-03-06
     */
    int updateByPrimaryKeySelective(LoveImage record);

    /**
     *
     * @mbggenerated 2018-03-06
     */
    int updateByPrimaryKey(LoveImage record);

    List<LoveImage> listLoveImage();

    void delete(String url);
}