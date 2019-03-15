package com.deyatech.common.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//这个标注应用于类
@Retention(RetentionPolicy.RUNTIME)//标注会一直保留到运行时
public @interface SysLog {


    /**
     * 模块的名称
     *
     * @return
     */
    String module();

    /**
     * 操作描述
     *
     * @return
     */
    String notes();
}
