package org.litisn.common.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * 基础service接口类
 * @author: litisn
 * @since: 2018-12-14 11:13
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 根据对象属性作为条件检索所有的数据列表
     * @param t
     * @return
     */
    Collection<T> listByBean(T t);

    /**
     * 根据对象属性作为条件分页检索数据列表
     * @param t
     * @return
     */
    IPage pageByBean(T t);
}
