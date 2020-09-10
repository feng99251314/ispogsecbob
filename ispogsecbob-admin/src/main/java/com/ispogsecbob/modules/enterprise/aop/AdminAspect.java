package com.ispogsecbob.modules.enterprise.aop;

import com.ispogsecbob.common.exception.RRException;
import com.ispogsecbob.modules.enterprise.annotation.HasAdminRole;
import com.ispogsecbob.modules.enterprise.service.EntEnterpriseInfoService;
import com.ispogsecbob.modules.enterprise.service.UserPerInfoService;
import com.ispogsecbob.modules.enterprise.service.UserTeacherInfoService;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import com.ispogsecbob.modules.sys.service.SysUserRoleService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description Aop管理员参数拦截
 * @date 2019/10/22
 */
@Order(1)
@Aspect
@Component
public class AdminAspect {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private UserPerInfoService userPerInfoService;

    @Autowired
    private UserTeacherInfoService userTeacherInfoService;

    @Autowired
    private EntEnterpriseInfoService entEnterpriseInfoService;

    @Pointcut("@annotation(com.ispogsecbob.modules.enterprise.annotation.HasAdminRole)")
    public void logPointCut() {

    }

    @Around(value = "logPointCut()")
    public Object rolesBefore(ProceedingJoinPoint joinPoint)throws Throwable{
        SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        if(sysUser == null)throw new RRException("当前没有登录，或者已过有效期");
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        HasAdminRole admin = targetMethod.getAnnotation(HasAdminRole.class);
        if(admin != null){
            List<Long> roles = sysUserRoleService.queryRoleIdList(sysUser.getUserId());
            if(roles != null && roles.size() > 0){
                String[] roleIds = admin.roleIds();
                out: for(int i = 0; i < roleIds.length; i++){
                    inner: for(int j = 0; j < roles.size(); j++){
                        // 管理员权限
                        if(Long.valueOf(roleIds[i]) == roles.get(j)){
                            break out;
                        }
                        Object[] args = joinPoint.getArgs();
                        if(args[admin.index()] instanceof java.util.Map){
                            java.util.Map map = (java.util.Map)args[admin.index()];
                            // 学生
                            if(roles.get(j) == Long.valueOf(admin.perRoleId())){
                                Long perId = userPerInfoService.queryUserPerIdByUserId(sysUser.getUserId());
                                map.put(admin.perRoleKey(), perId);
                                break out;
                            }
                            // 教师
                            if(roles.get(j) == Long.valueOf(admin.teacherRoleId())){
                                Long teacherId = userTeacherInfoService.queryUserTeacherIdByUserId(sysUser.getUserId());
                                map.put(admin.teacherRoleKey(), teacherId);
                                break out;
                            }
                            // 企业
                            if(roles.get(j) == Long.valueOf(admin.entRoleId())){
                                Long entId = entEnterpriseInfoService.queryEntInfoIdByUserId(sysUser.getUserId());
                                map.put(admin.entRoleKey(), entId);
                                break out;
                            }
                        }
                    }
                }
            }else{
                throw new RRException("当前没有权限，或者已过有效期");
            }
        }
        //执行方法
        Object result = joinPoint.proceed();
        return result;
    }

}