package org.land.common.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * 基础service接口类
 * @author: lee.
 * @since: 2018-12-14 11:13
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 根据对象属性作为条件删除对应的数据列表
     * @param entity
     * @return
     */
    Boolean removeByBean(T entity);

    /**
     * 根据对象属性作为条件获取对象信息
     * @param entity
     * @return
     */
    T getByBean(T entity);

    /**
     * 根据对象属性作为条件检索所有的数据列表
     * @param entity
     * @return
     */
    Collection<T> listByBean(T entity);

    /**
     * 根据对象属性作为条件分页检索数据列表
     * @param entity
     * @return
     */
    IPage pageByBean(T entity);
}
