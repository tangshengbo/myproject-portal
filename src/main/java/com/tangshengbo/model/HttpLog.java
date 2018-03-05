package com.tangshengbo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tangshengbo.core.CustomDateDeserializer;
import com.tangshengbo.core.CustomDateSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class HttpLog {
    /**
     *
     */
    private Integer logId;

    /**
     *
     */
    private String requestUrl;

    /**
     *
     */
    private String httpMethod;

    /**
     *
     */
    private String clientIp;

    /**
     *
     */
    private String clientAddress;

    /**
     *
     */
    private String clientProxy;

    /**
     *
     */
    private String remark;

    /**
     *
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createDate;

    /**
     *
     * @return log_id
     */
    public Integer getLogId() {
        return logId;
    }

    /**
     *
     * @param logId
     */
    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    /**
     *
     * @return request_url
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     *
     * @param requestUrl
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl == null ? null : requestUrl.trim();
    }

    /**
     *
     * @return http_method
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     *
     * @param httpMethod
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod == null ? null : httpMethod.trim();
    }

    /**
     *
     * @return client_ip
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     *
     * @param clientIp
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    /**
     *
     * @return client_address
     */
    public String getClientAddress() {
        return clientAddress;
    }

    /**
     *
     * @param clientAddress
     */
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress == null ? null : clientAddress.trim();
    }

    /**
     *
     * @return client_proxy
     */
    public String getClientProxy() {
        return clientProxy;
    }

    /**
     *
     * @param clientProxy
     */
    public void setClientProxy(String clientProxy) {
        this.clientProxy = clientProxy == null ? null : clientProxy.trim();
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}