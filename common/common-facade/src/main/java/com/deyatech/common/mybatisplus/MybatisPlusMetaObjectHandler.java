package com.deyatech.common.mybatisplus;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.deyatech.common.enums.EnableEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import com.deyatech.common.context.UserContextHelper;

import java.util.Date;

/**
 * <p>
 * 自动填充实现类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:09
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill clumns createTime,createBy");
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        if (StrUtil.isNotBlank(UserContextHelper.getUserId())) {
            this.setFieldValByName("createBy", UserContextHelper.getUserId(), metaObject);
            this.setFieldValByName("updateBy", UserContextHelper.getUserId(), metaObject);
        } else {
            this.setFieldValByName("createBy", null, metaObject);
            this.setFieldValByName("updateBy", null, metaObject);
        }
        if (this.getFieldValByName("enable", metaObject) == null) {
            this.setFieldValByName("enable", EnableEnum.ENABLE.getCode(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill clumns updateTime,updateBy");
        this.setFieldValByName("updateTime", new Date(), metaObject);
        if (StrUtil.isNotBlank(UserContextHelper.getUserId())) {
            this.setFieldValByName("updateBy", UserContextHelper.getUserId(), metaObject);
        }else{
            this.setFieldValByName("updateBy", null, metaObject);
        }
    }
}
