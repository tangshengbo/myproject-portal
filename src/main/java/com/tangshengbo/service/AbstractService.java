package com.tangshengbo.service;


import com.tangshengbo.core.MyMapper;
import com.tangshengbo.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected MyMapper<T> myMapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public int save(T model) {
        return myMapper.insertSelective(model);
    }

    public void save(List<T> models) {
        myMapper.insertList(models);
    }

    public int deleteById(Integer id) {
        return myMapper.deleteByPrimaryKey(id);
    }

    public void deleteByIds(String ids) {
        myMapper.deleteByIds(ids);
    }

    public int update(T model) {
        return myMapper.updateByPrimaryKeySelective(model);
    }

    public T findById(Integer id) {
        return myMapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return myMapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<T> findByIds(String ids) {
        return myMapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return myMapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return myMapper.selectAll();
    }

    @Override
    public void saveBatch(List<T> list) {
        int size = list.size();
        int unitNum = 1000;
        int startIndex = 0;
        int endIndex = 0;
        while (size > 0) {
            if (size > unitNum) {
                endIndex = startIndex + unitNum;
            } else {
                endIndex = startIndex + size;
            }
            List<T> insertBatch = list.subList(startIndex, endIndex);
            myMapper.insertBatch(insertBatch);
            size = size - unitNum;
            startIndex = endIndex;
        }
    }
}
