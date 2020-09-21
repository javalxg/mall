package com.wondersgroup.mall.common.api;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author lxg
 * @Description: 分页数据分装类
 * @date 2020/9/1823:22
 */
@Data
public class CommonPage<T> {
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 页大小
     */
    private Integer pageSize;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 总页数
     */
    private Integer pageTotal;
    /**
     * 分页数据
     */
    private List<T> list;

    /**
     *PageHelper分页后的数据转换为CommonPage
     * @param list
     * @param <T>
     * @return
     */
    public static <T> CommonPage<T> restPate(List<T> list){
    CommonPage<T> commonPage=new CommonPage<T>();
    PageInfo<T> pageInfo=new PageInfo<>(list);
    commonPage.setTotal(pageInfo.getTotal());
    commonPage.setPageSize(pageInfo.getPageSize());
    commonPage.setPageTotal(pageInfo.getPages());
    commonPage.setList(pageInfo.getList());
    return commonPage;
    }

    /**
     * 将spring data分页后的信息转换成commonpage
     * @param pageInfo
     * @param <T>
     * @return
     */
    public static <T> CommonPage<T> restPate(Page<T> pageInfo){
        CommonPage<T> commonPage=new CommonPage<T>();
        commonPage.setPageTotal(pageInfo.getTotalPages());
        commonPage.setPageNum(pageInfo.getNumber());
        commonPage.setPageSize(pageInfo.getSize());
        commonPage.setTotal(pageInfo.getTotalElements());
        commonPage.setList(pageInfo.getContent());
        return commonPage;
    }
}
