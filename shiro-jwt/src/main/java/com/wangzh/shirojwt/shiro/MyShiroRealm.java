package com.wangzh.shirojwt.shiro;

import com.wangzh.shirojwt.model.entity.SysPermissionEntity;
import com.wangzh.shirojwt.model.entity.SysRoleEntity;
import com.wangzh.shirojwt.model.entity.SysUserEntity;
import com.wangzh.shirojwt.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/30 10:34
 */

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 身份验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (null == authenticationToken) return null;
        String token = (String) authenticationToken.getCredentials();
        // 获取userName与DB(cache)对比
        String userName = JWTUtils.getUserName(token);
        if (StringUtils.isBlank(userName)) {
            throw new AuthenticationException("token invalid");
        }

        //read DB(cache)
        SysUserEntity userEntity = userService.getUserByName(userName);
        if (null == userEntity) {
            throw new AuthenticationException("用户不存在!");
        }

        if (!JWTUtils.verify(token, userName, userEntity.getPassword())) {
            throw new AuthenticationException("Token认证失败");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (null == principalCollection) return null;

        String userName = JWTUtils.getUserName(principalCollection.toString());
        if (StringUtils.isBlank(userName)) {
            throw new AuthenticationException("token invalid");
        }
        //read DB(cache)
        SysUserEntity userEntity = userService.getUserByName(userName);
        if (null == userEntity) {
            throw new AuthenticationException("用户不存在!");
        }

        SimpleAuthorizationInfo simpleInfo = new SimpleAuthorizationInfo();
        //设置用户角色权限
        for (SysRoleEntity roleEntity : userEntity.getRoles()) {
            simpleInfo.addRole(roleEntity.getRoleName());
            for (SysPermissionEntity permission : roleEntity.getPermissions()) {
                simpleInfo.addStringPermission(permission.getPermName());
            }
        }

        return simpleInfo;

    }
}
