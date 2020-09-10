package com.ispogsecbob.modules.enterprise.aop;

import com.ispogsecbob.modules.enterprise.annotation.DefaultArrayValue;
import com.ispogsecbob.modules.enterprise.annotation.DefaultValue;
import com.ispogsecbob.common.enums.DefValueEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description AOP处理请求数据中指定值为空是赋值初始值
 * @date 2019/10/9
 */
@Aspect
@Component
public class DefaultValueAspect {

    @Pointcut("@annotation(com.ispogsecbob.modules.enterprise.annotation.DefaultValue) || @annotation(com.ispogsecbob.modules.enterprise.annotation.DefaultArrayValue)")
    public void logPointCut() {

    }

    @Around(value = "logPointCut()")
    public Object nullValueAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        // 单值注解
        DefaultValue defaultValue = targetMethod.getAnnotation(DefaultValue.class);
        if(defaultValue != null){
            Class<?> targetType = defaultValue.targetType();
            int index = defaultValue.index();
            Object[] args = joinPoint.getArgs();
            if(args != null && args.length > 0){
                for (int i=0; i<args.length; i++){
                    if (invokeMethod(targetType, defaultValue, index == i, args[i])) break;
                }
            }
        }

        // 多值注解
        DefaultArrayValue annotation = targetMethod.getAnnotation(DefaultArrayValue.class);
        if(annotation != null){
            Class<?> targetType = annotation.targetType();
            int index = annotation.index();
            Object[] args = joinPoint.getArgs();
            if(args != null && args.length > 0){
                for (int i=0; i<args.length; i++){
                    if (invokeArrayMethod(annotation, index == i, args[i])) break;
                }
            }
        }
        //执行方法
        Object result = joinPoint.proceed();
        return result;
    }

    private boolean invokeArrayMethod(DefaultArrayValue annotation, boolean b, Object arg) {
        if(b){
            if(arg instanceof java.util.Map){
                java.util.Map map = (java.util.Map) arg;
                String[] keys = annotation.key();
                for(int i=0; i<keys.length; i++){
                    Object object = map.get(keys[i]);
                    if (null == object) {
                        DefValueEnum[] defValueEnum = annotation.defValueEnum();
                        enumArrayType(defValueEnum[i],keys[i], annotation.defValue()[i], map);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void enumArrayType(DefValueEnum defValueEnum, String key, String defaultValue, Map map) {
        switch (defValueEnum){
            case BYTE:
                map.put(key,Byte.valueOf(defaultValue));
                break;
            case SHORT:
                map.put(key,Short.valueOf(defaultValue));
                break;
            case INTEGER:
                map.put(key,Integer.valueOf(defaultValue));
                break;
            case LONG:
                map.put(key,Long.valueOf(defaultValue));
                break;
            case FLOAT:
                map.put(key,Float.valueOf(defaultValue));
                break;
            case DOUBLE:
                map.put(key,Double.valueOf(defaultValue));
                break;
            case BOOLEAN:
                map.put(key,Boolean.valueOf(defaultValue));
                break;
            case STRING:
                map.put(key,defaultValue);
                break;
        }
    }

    private boolean invokeMethod(Class<?> targetType, DefaultValue defaultValue, boolean b, Object arg) {
        if(b){
            if(arg instanceof java.util.Map){
                java.util.Map map = (java.util.Map) arg;
                Object object = map.get(defaultValue.key());
                if (null == object) {
                    DefValueEnum defValueEnum = defaultValue.defValueEnum();
                    enumType(defValueEnum, defaultValue, map);
                }
            }
            if(arg instanceof java.lang.String){
                arg = defaultValue.defValue();
            }
            return true;
        }
        return false;
    }

    private void enumType(DefValueEnum defValueEnum, DefaultValue defaultValue, Map map) {
        switch (defValueEnum){
            case BYTE:
                map.put(defaultValue.key(),Byte.valueOf(defaultValue.defValue()));
                break;
            case SHORT:
                map.put(defaultValue.key(),Short.valueOf(defaultValue.defValue()));
                break;
            case INTEGER:
                map.put(defaultValue.key(),Integer.valueOf(defaultValue.defValue()));
                break;
            case LONG:
                map.put(defaultValue.key(),Long.valueOf(defaultValue.defValue()));
                break;
            case FLOAT:
                map.put(defaultValue.key(),Float.valueOf(defaultValue.defValue()));
                break;
            case DOUBLE:
                map.put(defaultValue.key(),Double.valueOf(defaultValue.defValue()));
                break;
            case BOOLEAN:
                map.put(defaultValue.key(),Boolean.valueOf(defaultValue.defValue()));
                break;
            case STRING:
                map.put(defaultValue.key(),defaultValue.defValue());
                break;
        }
    }
}
