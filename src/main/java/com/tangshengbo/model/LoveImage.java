package com.tangshengbo.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class LoveImage {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String imgUrl = "http:///";

    /**
     *
     */
    private String remark;
    /**
     *
     */
    private Date createDate;

    private String beanFactoryProcessorDate;

    private String beanProcessorDate;

    private CanvasImage canvasImage = new CanvasImage();

    /**
     * 画板图片对象
     * @return
     */
    public CanvasImage getCanvasImage() {
        return canvasImage;
    }

    public String getBeanFactoryProcessorDate() {
        return beanFactoryProcessorDate;
    }

    public String getBeanProcessorDate() {
        return beanProcessorDate;
    }

    public void setBeanProcessorDate(String beanProcessorDate) {
        this.beanProcessorDate = beanProcessorDate;
    }

    public void setBeanFactoryProcessorDate(String beanFactoryProcessorDate) {
        this.beanFactoryProcessorDate = beanFactoryProcessorDate;
    }

    public void setCanvasImage(CanvasImage canvasImage) {
        this.canvasImage = canvasImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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