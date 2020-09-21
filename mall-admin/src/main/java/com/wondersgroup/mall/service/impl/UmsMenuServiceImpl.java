package com.wondersgroup.mall.service.impl;

import com.wondersgroup.mall.dto.UmsMenuNode;
import com.wondersgroup.mall.mapper.UmsMenuMapper;
import com.wondersgroup.mall.model.UmsMenu;
import com.wondersgroup.mall.model.UmsMenuExample;
import com.wondersgroup.mall.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2015:33
 */
@Service
public class UmsMenuServiceImpl implements MenuService {
    @Autowired
    private UmsMenuMapper umsMenuMapper;
    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> menuList=umsMenuMapper.selectByExample(new UmsMenuExample());
        List<UmsMenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> convertMenuNode(menu, menuList)).collect(Collectors.toList());
        return result;
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     * @param umsMenu
     * @param menuList
     * @return
     */
    private UmsMenuNode convertMenuNode(UmsMenu umsMenu,List<UmsMenu> menuList){
        UmsMenuNode node=new UmsMenuNode();
        BeanUtils.copyProperties(umsMenu, node);
        List<UmsMenuNode> children=menuList.stream()
                .filter(subMenu->subMenu.getParentId().equals(umsMenu.getId()))
                .map(subMenu->convertMenuNode(subMenu,menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;

    }
}
