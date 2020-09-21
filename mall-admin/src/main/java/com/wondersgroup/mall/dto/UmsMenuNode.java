package com.wondersgroup.mall.dto;

import com.wondersgroup.mall.model.UmsMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lxg
 * @Description: 后台菜单节点的封装
 * @date 2020/9/2015:31
 */
@Getter
@Setter
public class UmsMenuNode extends UmsMenu {
    @ApiModelProperty(value = "子级菜单")
    private List<UmsMenuNode> children;
}
