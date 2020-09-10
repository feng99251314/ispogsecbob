package com.ispogsecbob.modules.enterprise.aop;

import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.annotation.ResultNotNull;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description 返回值结果非空处理
 * @date 2019/9/24
 */
@Aspect
@Component
public class ResultNotNullAspect {

    @Pointcut("@annotation(com.ispogsecbob.modules.enterprise.annotation.ResultNotNull)")
    public void logPointCut() {

    }

    @AfterReturning(returning="rvt", pointcut = "logPointCut()")
    public Object rolesBefore(JoinPoint joinPoint, Object rvt){
        if(rvt != null) {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method targetMethod = methodSignature.getMethod();
            ResultNotNull resultAn = targetMethod.getAnnotation(ResultNotNull.class);
            if (resultAn != null) {
                if (rvt instanceof Map) {
                    Map map = (Map) rvt;
                    if(map.get(resultAn.targetKey()) == null) {
                        Class clazz = resultAn.clazz();
                        boolean anInterface = clazz.isInterface();
                        if (!anInterface) {
                            try {
                                Object o = clazz.newInstance();
                                map.put(resultAn.targetKey(), o);
                                map.put(resultAn.codeKey(), resultAn.errorCode());
                                map.put("msg_" + System.currentTimeMillis(), "未查询到数据");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return rvt;
    }

}