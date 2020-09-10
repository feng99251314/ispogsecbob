package com.ispogsecbob.modules.enterprise.aop;

import com.ispogsecbob.common.exception.RRException;
import com.ispogsecbob.modules.enterprise.annotation.HasRole;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import com.ispogsecbob.modules.sys.service.SysUserRoleService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description Aop企业入驻权限拦截
 * @date 2019/9/24
 */

@Aspect
@Component
public class HasRoleAspect {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Pointcut("@annotation(com.ispogsecbob.modules.enterprise.annotation.HasRole)")
    public void logPointCut() {

    }

    @Before(value = "logPointCut()")
    public void rolesBefore(JoinPoint joinPoint) throws RRException {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        HasRole hasRole = targetMethod.getAnnotation(HasRole.class);
        SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        if(sysUser != null){
            List<Long> roles = sysUserRoleService.queryRoleIdList(sysUser.getUserId());
            if(roles != null && roles.size() > 0){
                boolean flag = false;
                outer:for(int i=0; i<roles.size(); i++){
                    Long aLong = roles.get(i);
                    String[] roleList = hasRole.roles();
                    for (int j=0; j<roleList.length; j++){
                        if(roleList[j].equals(String.valueOf(aLong))){
                            Object[] args = joinPoint.getArgs();
                            if(args.length == 1 && args[0] instanceof Map){
                                Map param = (Map) args[0];
                                param.put("_queryType", String.valueOf(aLong));
                            }
                            flag = true;
                            break outer;
                        }
                    }
                }
                if(!flag){
                    throw new RRException("您未拥有权限");
                }
            }else{
                throw new RRException("您还未拥有角色");
            }
        }else{
            throw new RRException("您还未登录系统");
        }
    }

}