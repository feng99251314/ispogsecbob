package com.ispogsecbob.common.aspect;

import com.ispogsecbob.common.annotation.LimitPage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description 分页参数拦截AOP
 * @date 2019/10/23
 */
@Order(1)
@Aspect
@Component
public class LimitPageAspect {

    @Pointcut("@annotation(com.ispogsecbob.common.annotation.LimitPage)")
    public void logPointCut() {

    }

    @Around(value = "logPointCut()")
    public Object nullValueAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        // 单值注解
        LimitPage limitPage = targetMethod.getAnnotation(LimitPage.class);
        if(limitPage != null){
            Object[] args = joinPoint.getArgs();
            if(args[limitPage.index()] instanceof java.util.Map){
                java.util.Map map = (java.util.Map)args[limitPage.index()];
                try {
                    Object currPage = map.get(limitPage.pageKey());
                    if(currPage == null){
                        map.put(limitPage.pageKey(), Integer.valueOf(limitPage.currPage()));
                    }else {
                        map.put(limitPage.pageKey(), Integer.valueOf(currPage.toString()));
                    }
                    Object pageSize = map.get(limitPage.pageSizeKey());
                    if(pageSize == null){
                        map.put(limitPage.pageSizeTarKey(), Integer.valueOf(limitPage.pageSize()));
                    }else{
                        map.put(limitPage.pageSizeTarKey(), Integer.valueOf(pageSize.toString()));
                    }
                    map.put(limitPage.currPageKey(), Integer.valueOf(map.get(limitPage.pageKey()).toString()));
                    Object temp = map.get(limitPage.pageKey());
                    map.put(limitPage.pageKey(), 0 + ((Integer) map.get(limitPage.pageSizeTarKey())) * (Integer.valueOf(temp.toString()) - 1));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        //执行方法
        Object result = joinPoint.proceed();
        return result;
    }

}
