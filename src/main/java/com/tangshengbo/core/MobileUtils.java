package com.tangshengbo.core;

import com.alibaba.fastjson.JSON;

import java.util.regex.Pattern;

/**
 * Created by Tangshengbo on 2018/12/4
 */
public final class MobileUtils {

    //手机号码正则
    private static final String mobileReg = "^1(3[0-9]|4[579]|5[0-35-9]|7[035678]|8[0-9])[0-9]{8}$";
    //中国移动正则
    private static final String cmReg = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])[0-9]{8}$)|(^1705[0-9]{7}$)";
    //中国联通正则
    private static final String cuReg = "(^1(3[0-2]|4[5]|5[56]|7[56]|8[56])[0-9]{8}$)|(^1709[0-9]{7}$)";
    //中国电信正则
    private static final String ctReg = "^1(3[3]|4[9]|53|7[037]|8[019])\\d{8}$";

    public enum MobileType {
        CHINA_MOBILE("中国移动", "10086"), CHINA_UNICOM("中国联通", "10010"), CHINA_TELECOM("中国电信", "10000");
        public String name;
        public String serviceTel;

        MobileType(String name, String serviceTel) {
            this.name = name;
            this.serviceTel = serviceTel;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    /**
     * 校验手机号码是否合法
     *
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile) {
        Pattern p = Pattern.compile(mobileReg);
        return p.matcher(mobile).matches();
    }

    /**
     * 获取运营商类型
     *
     * @param mobile
     * @return
     */
    public static MobileType getMobileType(String mobile) {
        Pattern pCm = Pattern.compile(cmReg);
        Pattern pCu = Pattern.compile(cuReg);
        Pattern pCt = Pattern.compile(ctReg);
        if (pCm.matcher(mobile).matches()) {
            return MobileType.CHINA_MOBILE;
        } else if (pCu.matcher(mobile).matches()) {
            return MobileType.CHINA_UNICOM;
        } else if (pCt.matcher(mobile).matches()) {
            return MobileType.CHINA_TELECOM;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
    }
}
