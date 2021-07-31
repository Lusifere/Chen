package com.xp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xp.common.AdminUserSession;
import com.xp.common.R;
import com.xp.pojo.Book;
import com.xp.mapper.BookDao;
import com.xp.pojo.User;
import com.xp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Book)表服务实现类
 *
 * @author makejava
 * @since 2021-07-27 20:49:33
 */
@Service("bookService")
public class BookServiceImpl implements BookService {
    @Resource
    private BookDao bookDao;
    @Autowired
    private AdminUserSession adminUserSession;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Book queryById(Integer id) {
        return this.bookDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param book 实例对象
     * @return 实例对象
     */
    @Override
    public Book insert(Book book) {
        this.bookDao.insert(book);
        return book;
    }

    /**
     * 修改数据
     *
     * @param book 实例对象
     * @return 实例对象
     */
    @Override
    public Book update(Book book) {
        this.bookDao.update(book);
        return this.queryById(book.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return bookDao.deleteById(id) > 0;
    }

    @Override
    public PageInfo<Book> getPage(Integer pageNum, Integer pageSize) {
        User user = adminUserSession.getCurrentAdminUser();
        if (user == null) {
            // 用户只显示审核通过的
            Book book = new Book();
            book.setAudit((byte) 2);
            PageHelper.startPage(pageNum, pageSize);
            List<Book> books = this.bookDao.queryAll(book);
            return new PageInfo<>(books);
        }
        if ("管理员".equals(user.getRole())) {
            PageHelper.startPage(pageNum, pageSize);
            List<Book> books = this.bookDao.queryAll(new Book());
            return new PageInfo<>(books);
        } else {
            // 用户只显示审核通过的
            Book book = new Book();
            book.setAudit((byte) 2);
            PageHelper.startPage(pageNum, pageSize);
            List<Book> books = this.bookDao.queryAll(book);
            return new PageInfo<>(books);
        }
    }

    @Override
    public Object save(Book book) {
        bookDao.insert(book);
        return R.ok();
    }
}
