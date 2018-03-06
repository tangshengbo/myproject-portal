package com.tangshengbo.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class LoveImage {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String imgUrl;

    /**
     *
     */
    private String remark;

    /**
     *
     */
    private Date createDate;

    /**
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return img_url
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     *
     * @param imgUrl
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**
     *
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     *
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public LoveImage(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public LoveImage() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}