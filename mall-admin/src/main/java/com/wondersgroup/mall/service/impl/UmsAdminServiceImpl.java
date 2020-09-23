package com.wondersgroup.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.wondersgroup.mall.bo.AdminDetails;
import com.wondersgroup.mall.common.api.ResultCode;
import com.wondersgroup.mall.common.exception.ApiException;
import com.wondersgroup.mall.common.exception.Asserts;
import com.wondersgroup.mall.dao.UmsAdminRoleRelationDao;
import com.wondersgroup.mall.dto.UmsAdminParam;
import com.wondersgroup.mall.mapper.UmsAdminLoginLogMapper;
import com.wondersgroup.mall.mapper.UmsAdminMapper;
import com.wondersgroup.mall.mapper.UmsAdminRoleRelationMapper;
import com.wondersgroup.mall.model.*;
import com.wondersgroup.mall.service.UmsAdminCacheService;
import com.wondersgroup.mall.service.UmsAdminService;
import com.wondersgroup.mall.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lxg
 * @Description: 后台管理员service实现类
 * @date 2020/9/190:33
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER= LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private UmsAdminCacheService umsAdminCacheService;
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;
    @Autowired
    private UmsAdminLoginLogMapper loginLogMapper;
    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UmsAdmin getAdminByUsername(String userName) throws ApiException {
        UmsAdmin umsAdmin=umsAdminCacheService.getAdmin(userName);
        if (umsAdmin!=null){
            return umsAdmin;
        }
        UmsAdminExample example=new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(userName);
        List<UmsAdmin> admins=umsAdminMapper.selectByExample(example);
        if (admins != null && admins.size() > 0) {
            umsAdmin = admins.get(0);
            umsAdminCacheService.setAdmin(umsAdmin);
            return umsAdmin;
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) throws ApiException {
            UmsAdmin umsAdmin=new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam,umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        UmsAdminExample example=new UmsAdminExample();
        //查询是否有相同的用户名
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> list=umsAdminMapper.selectByExample(example);
        if (list.size()>0){
            Asserts.fail(ResultCode.USERNAME_DUPLICATE);
        }
        String password=passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(password);
        umsAdminMapper.insert(umsAdmin);
        return umsAdmin;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws ApiException{
        //获取用户信息
        UmsAdmin umsAdmin=getAdminByUsername(username);
        if (umsAdmin!=null){
            List<UmsResource> resources=getResourceList(umsAdmin.getId());
            return new AdminDetails(umsAdmin, resources);
        }
        throw  new UsernameNotFoundException("用户不存在");
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
    List<UmsResource> resources=umsAdminCacheService.getResourceList(adminId);
    if (CollUtil.isNotEmpty(resources)){
        return resources;
    }
    resources=umsAdminRoleRelationDao.getResourceList(adminId);
    if (CollUtil.isNotEmpty(resources)){
    umsAdminCacheService.setResourceList(adminId, resources);
    }

        return resources;
    }

    @Override
    public String login(String username, String password) {
        String token=null;
        try {
            UserDetails userDetails=loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("账号被禁用");
            }
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
            SecurityContext context= SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);
            token=jwtTokenUtil.generateToken(userDetails);
            insertLoginLog(username);
        }catch (AuthenticationException e){
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }
    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if(admin==null) {
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return umsAdminRoleRelationDao.getRoleList(adminId);
    }

    @Override
    public List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        UmsAdminExample example=new UmsAdminExample();
        UmsAdminExample.Criteria criteria=example.createCriteria();
        if (!StringUtils.isEmpty(keyword)){
                criteria.andUsernameLike("%"+keyword+"%");
                example.or(example.createCriteria().andNickNameLike("%"+keyword+"%"));
        }
        return umsAdminMapper.selectByExample(example);

    }

    @Override
    public int update(Long adminId, UmsAdmin umsAdmin) {
        umsAdminCacheService.delAdmin(adminId);
        umsAdmin.setId(adminId);
        UmsAdmin admin=umsAdminMapper.selectByPrimaryKey(adminId);
        if (admin.getPassword().equals(umsAdmin.getPassword())){
            //TODO 与原加密的密码相同不需要修改
            umsAdmin.setPassword(null);
        }else{
            if(StrUtil.isEmpty(umsAdmin.getPassword())){
                    umsAdmin.setPassword(null);
            }else{
                umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
            }
        }
        int count=umsAdminMapper.updateByPrimaryKeySelective(umsAdmin);
        return count;
    }

    @Override
    public UmsAdmin getItem(Long id) {
        return umsAdminMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        umsAdminCacheService.delResourceList(adminId);
        int count=roleIds==null?0:roleIds.size();
        //先删除原来的关系
        UmsAdminRoleRelationExample example=new UmsAdminRoleRelationExample();
        example.createCriteria().andAdminIdEqualTo(adminId);
        umsAdminRoleRelationMapper.deleteByExample(example);
        //建立新关系
        if (!CollUtil.isEmpty(roleIds)){
            List<UmsAdminRoleRelation>list=new ArrayList<>();
            for (Long roleId:roleIds){
               UmsAdminRoleRelation umsAdminRoleRelation=new UmsAdminRoleRelation();
               umsAdminRoleRelation.setAdminId(adminId);
               umsAdminRoleRelation.setRoleId(roleId);
               list.add(umsAdminRoleRelation);
            }
            umsAdminRoleRelationDao.insertList(list);

        }
        return count;
    }

    @Override
    public int delete(Long adminId) {
        umsAdminCacheService.delAdmin(adminId);
        umsAdminCacheService.delResourceList(adminId);
        int count=umsAdminMapper.deleteByPrimaryKey(adminId);
        return count;
    }
}
