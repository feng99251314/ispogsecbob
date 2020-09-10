package com.ispogsecbob.common.utils;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description 自定义分页拦截工具
 * @date 2019/10/9
 */
public class PagingTool {

    /**
     * 拦截分页信息
     * @param params
     * @return
     */
//    public static <T> Page<?> handlerPage(Map<String, Object> params){
//        Integer pageSize = null;
//        Integer currPage = null;
//        try {
//            pageSize = Integer.valueOf((String) params.get("pageSize"));
//            currPage = Integer.valueOf((String) params.get("currPage"));
//        }catch (Exception e){
//            pageSize = Integer.valueOf(15);
//            currPage = Integer.valueOf(1);
//        }
//        Page objectPage = PageHelper.startPage(currPage, pageSize);
//        return objectPage;
//    }

    public static final String DATA_KEY = "data";

    /**
     * 响应分页数据
     * @param t
     * @param page
     * @return
     */
//    public static PageUtils page(List<?> t, Page page){
//        return new PageUtils(t, (int)page.getTotal(), page.getPageSize(), page.getPageNum());
//    }

}
