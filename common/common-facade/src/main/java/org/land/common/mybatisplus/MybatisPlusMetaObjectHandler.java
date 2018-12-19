package org.land.common.mybatisplus;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.land.common.context.UserContextHelper;
import org.springframework.stereotype.Component;

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
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill clumns createTime,createBy");
        this.setFieldValByName("createTime", new Date(), metaObject);
        if (StrUtil.isNotBlank(UserContextHelper.getUserId())) {
            this.setFieldValByName("createBy", UserContextHelper.getUserId(), metaObject);
        }else{
            this.setFieldValByName("createBy", null, metaObject);
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
