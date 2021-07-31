package com.xp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xp.common.AdminUserSession;
import com.xp.common.R;
import com.xp.mapper.UserMapper;
import com.xp.pojo.User;
import com.xp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: liangrenren
 * @Date: 9:45 2021/4/3
 * @Description:
 * @Version v1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminUserSession adminUserSession;
    @Autowired
    private HttpSession session;

    @Override
    public Object getById(Integer id) {
        return userMapper.queryById(id);
    }

    @Override
    public Object delete(Integer id) {
        userMapper.deleteById(id);
        return R.ok();
    }
//
    @Override
    public Object save(User user) {
        if (user.getId() == null) {
            List<User> users = userMapper.selectByName(user.getName());
            if (users.size() > 0) {
                return R.fail("该账号已存在");
            }
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
        return R.ok(user);
    }

    @Override
    public Object login(User user) {
        String code = session.getAttribute("code").toString();
        if (!user.getCode().equalsIgnoreCase(code)) {
            return R.fail("验证码错误");
        }

        List<User> users = userMapper.login(user.getName(),user.getPassword());
        if (users.isEmpty())
            return R.fail("用户名账号错误");
        user = users.get(0);
        adminUserSession.saveCurrentAdminUser(user);
        return R.ok(user);
    }

    @Override
    public Object updateById(User user) {
        userMapper.update(user);
        final User userResult = userMapper.queryById(user.getId());
        userResult.setPassword(null);
        return userResult;
    }

    @Override
    public Object updatePassword(String newPass, String oldPass) {
        User user = adminUserSession.getCurrentAdminUser();
        if (!oldPass.equals(user.getPassword())) {
            return R.fail();
        } else {
            user.setPassword(newPass);
            updateById(user);
            return R.ok();
        }
    }

    @Override
    public Object getPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.queryAllByLimit(pageNum,pageSize);
        return new PageInfo<>(users);
    }

}
