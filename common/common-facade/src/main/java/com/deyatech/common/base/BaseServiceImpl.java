package com.deyatech.common.base;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deyatech.common.Constants;
import com.deyatech.common.utils.ColumnUtil;

import java.util.Collection;
import java.util.Map;

/**
 * 基础service实现类
 *
 * @author: lee.
 * @since: 2018-12-14 11:14
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public Boolean removeByBean(T entity) {
        return super.remove(getQueryWrapperByBean(entity));
    }

    @Override
    public T getByBean(T entity) {
        return super.getOne(getQueryWrapperByBean(entity));
    }

    @Override
    public Collection<T> listByBean(T entity) {
        return super.list(getQueryWrapperByBean(entity));
    }

    @Override
    public IPage<T> pageByBean(T entity) {
        return super.page(getPageByBean(entity), getQueryWrapperByBean(entity));
    }

    /**
     * 根据基类定义的属性获取page翻页对象
     *
     * @param entity
     * @return
     */
    private Page<T> getPageByBean(T entity) {
        Page<T> page = new Page();
        if (ObjectUtil.isNotNull(entity.getPage())) {
            page.setCurrent(entity.getPage());
        } else {
            page.setCurrent(Constants.DEFAULT_CURRENT_PAGE);
        }
        if (ObjectUtil.isNotNull(entity.getSize())) {
            page.setSize(entity.getSize());
        } else {
            page.setSize(Constants.DEFAULT_PAGE_SIZE);
        }
        return page;
    }

    /**
     * 根据基类定义的属性获取QueryWrapper对象
     *
     * @param entity
     * @return
     */
    private QueryWrapper<T> getQueryWrapperByBean(T entity) {
        Map<String, Object> map = ColumnUtil.objectToColumnMap(entity);
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>().allEq(map, false);
        if (ObjectUtil.isNotNull(entity) && StrUtil.isNotBlank(entity.getSortSql())) {
            String sortSql = Constants.SORT_SQL_KEYWORD.concat(ColumnUtil.replacePropertyToColumn(entity.getSortSql(), entity));
            queryWrapper.last(sortSql);
        } else {
            queryWrapper.last(Constants.DEFAULT_SORT_STR);
        }
        return queryWrapper;
    }
}
