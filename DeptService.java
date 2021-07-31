package com.xp.service;

import com.xp.pojo.Dept;

import java.util.List;


/**
 * (Demp)表服务接口
 *
 * @author makejava
 * @since 2021-07-14 09:40:34
 */
public interface DeptService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Object queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Dept> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param demp 实例对象
     * @return 实例对象
     */
    Object insert(Dept demp);

    /**
     * 修改数据
     *
     * @param demp 实例对象
     * @return 实例对象
     */
    Object update(Dept demp);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    Object deleteById(Integer id);


    Object list(Integer pageNum, Integer pageSize);

    Object staffList(Integer pageNum, Integer pageSize, Integer deptId);
}
