package com.tangshengbo.core;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by TangShengBo on 2018/12/15
 */
public class MybatisUtil {

    private final static SqlSessionFactory SQL_SESSION_FACTORY;

    static {
        String resource = "mybatis-config.xml";
        InputStream resourceStream = null;
        try {
            resourceStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(resourceStream);
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return SQL_SESSION_FACTORY;
    }

}
