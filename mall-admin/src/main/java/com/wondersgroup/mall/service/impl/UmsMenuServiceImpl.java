package com.wondersgroup.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.wondersgroup.mall.dto.UmsMenuNode;
import com.wondersgroup.mall.mapper.UmsMenuMapper;
import com.wondersgroup.mall.model.UmsMenu;
import com.wondersgroup.mall.model.UmsMenuExample;
import com.wondersgroup.mall.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsMenuExample example=new UmsMenuExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        example.setOrderByClause("sort desc");
        return umsMenuMapper.selectByExample(example);

    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        UmsMenu umsMenu=new UmsMenu();
        umsMenu.setId(id);
        umsMenu.setHidden(hidden);
        return umsMenuMapper.updateByPrimaryKeySelective(umsMenu);
    }

    @Override
    public UmsMenu getIten(Long id) {
        return umsMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id,UmsMenu umsMenu) {
        umsMenu.setId(id);
        updateMenuLevel(umsMenu);
        return umsMenuMapper.updateByPrimaryKeySelective(umsMenu);
    }

    @Override
    public int create(UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        updateMenuLevel(umsMenu);
        return umsMenuMapper.insert(umsMenu);
    }

    @Override
    public int delete(Long id) {
        return umsMenuMapper.deleteByPrimaryKey(id);
    }

    private void updateMenuLevel(UmsMenu umsMenu){
        if (umsMenu.getParentId()==0){
            //没有父菜单 设为一级菜单
        umsMenu.setLevel(0);
        }else{
            //有父级菜单时，根据父级菜单设置
            UmsMenu umsMenu1=umsMenuMapper.selectByPrimaryKey(umsMenu.getParentId());
            if (umsMenu1 != null) {
                umsMenu.setLevel(umsMenu1.getLevel() + 1);
            } else {
                umsMenu.setLevel(0);
            }
        }


    }
}
