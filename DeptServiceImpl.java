package com.xp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xp.common.R;
import com.xp.common.Result;
import com.xp.pojo.Dept;
import com.xp.mapper.DeptMapper;
import com.xp.pojo.User;
import com.xp.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Demp)表服务实现类
 *
 * @author makejava
 * @since 2021-07-14 09:40:34
 */
@Service("deptService")
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptMapper deptDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Object queryById(Integer id) {
        return this.deptDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Dept> queryAllByLimit(int offset, int limit) {
        return this.deptDao.queryAllByLimit(offset, limit,null);
    }

    /**
     * 新增数据
     *
     * @param dept 实例对象
     * @return 实例对象
     */
    @Override
    public Object insert(Dept dept) {
        if (dept.getId() == null) {
            List<User> users = deptDao.selectByName(dept.getName());
            if (users.size() > 0) {
                return R.fail("该部门已存在");
            }
            deptDao.insert(dept);
        } else {
            deptDao.update(dept);
        }
        return Result.ok();
    }

    /**
     * 修改数据
     *
     * @param demp 实例对象
     * @return 实例对象
     */
    @Override
    public Object update(Dept demp) {
        this.deptDao.update(demp);
        return Result.ok();
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public Object deleteById(Integer id) {
        return this.deptDao.deleteById(id) > 0;
    }

    @Override
    public Object list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dept> depts = this.deptDao.queryAllByLimit(pageNum, pageSize,null);
        return new PageInfo<>(depts);
    }

    @Override
    public Object staffList(Integer pageNum, Integer pageSize, Integer deptId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dept> depts = this.deptDao.queryAllByLimit(pageNum, pageSize,deptId);
        return new PageInfo<>(depts);
    }
}
