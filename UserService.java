package com.xp.service;

import com.xp.pojo.User;


public interface UserService {

    Object getById(Integer id);
    Object delete(Integer id);
    Object save(User user);
    Object getPage(Integer pageNum, Integer pageSize);
//    Object list();
    Object login(User user);

    /**
     * 修改
     * @param user
     * @return
     */
    Object updateById(User user);

    /**
     * 修改密码
     * @param newPass
     * @param oldPass
     * @return
     */
    Object updatePassword(String newPass, String oldPass);

//}
}
