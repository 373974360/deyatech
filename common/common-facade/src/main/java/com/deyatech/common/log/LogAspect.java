package com.deyatech.common.log;

import cn.hutool.core.thread.threadlocal.NamedThreadLocal;
import cn.hutool.core.util.ObjectUtil;
import com.deyatech.common.context.UserContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Slf4j
@Aspect
public class LogAspect {

    public LogAspect() {
    }

    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<>("ThreadLocal beginTime");

    @Autowired(required=false)
    HttpServletRequest request;

    private LogInfoHandler logInfoHandler;

    public void setLogInfoHandler(LogInfoHandler logInfoHandler) {
        this.logInfoHandler = logInfoHandler;
    }


    /**
     * Controller层切点 注解拦截
     */
    @Pointcut("@annotation(com.deyatech.common.log.SysLog)")
    public void controllerAspect(){}

    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        log.info("进入日志切面前置通知!!");
        Date beginTime=new Date();
        beginTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）
    }

    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("进入日志切面后置通知!!");
        String module = getControllerModule(joinPoint);
        String notes = getControllerNotes(joinPoint);
        // 打印JVM信息。
        long beginTime = beginTimeThreadLocal.get().getTime();//得到线程绑定的局部变量（开始时间）  
        long endTime = System.currentTimeMillis();//2、结束时间  
        log.info("设置日志信息存储到表中!!");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method m = methodSignature.getMethod();
        LogsInfo logs = new LogsInfo();
        logs.setNotes("["+module+"]"+notes);
        logs.setMethod(m.getDeclaringClass().getName() + "." + m.getName());
        logs.setRequestUrl(request.getRequestURI());
        logs.setUserId(UserContextHelper.getUserId());
        logs.setParams(ObjectUtil.toString(joinPoint.getArgs()));
        logs.setTime(endTime-beginTime);
        logs.setIp(request.getRemoteAddr());
        logs.setCreateBy(logs.getUserId());
        logInfoHandler.handle(logs);
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return discription
     */
    public static String getControllerModule(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        return sysLog.module();
    }
    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return discription
     */
    public static String getControllerNotes(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        return sysLog.notes();
    }


    @AfterReturning(returning = "res", pointcut = "controllerAspect()")
    public void doAfterReturning(Object res) {
        log.info("==========返回参数日志=========");
        log.info("返回接口响应参数:"+res);
    }

    /**
     *  异常通知 记录操作报错日志
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.info("进入日志切面异常通知!!");
        log.info("异常信息:"+e.getMessage());
    }

}
