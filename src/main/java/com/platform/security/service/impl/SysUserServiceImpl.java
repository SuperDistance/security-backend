package com.platform.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.platform.security.common.utils.UserUtil;
import com.platform.security.entity.SysUser;
import com.platform.security.dao.SysUserDao;
import com.platform.security.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysUser> queryAllByLimit(int offset, int limit) {
        return this.sysUserDao.queryAllByLimit(offset, limit);
    }

    @Override
    public SysUser selectByName(String userName) {
        return this.sysUserDao.selectByName(userName);
    }

    @Override
    public int registerOrUpdateUser(SysUser sysUser, boolean isUpdate, boolean revisePassword, String newPassword, boolean isAdminister) {
        int verifyResult = judgeUser(sysUser, isUpdate, revisePassword, newPassword, isAdminister);
        if (verifyResult != 0) {
            return verifyResult;
        }
        // if fail to save or update: status code: 1
        if (!saveOrUpdate(sysUser)) {
            verifyResult = 1;
        }
        // if success: status code: 0
        return verifyResult;
    }

    private int judgeUser(SysUser sysUser, boolean isUpdate, boolean revisePassword, String newPassword, boolean isAdminister) {
        // if user not hasn't the right to revise/add others: status code: 7
        String currentUserAccount = UserUtil.getCurrentUserAccount();
        if (!isAdminister && sysUser.getAccount().equals(currentUserAccount)) {
            return 7;
        }

        SysUser temp = getOne(new QueryWrapper<SysUser>().eq("account", sysUser.getAccount()));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // if register, judge whether account exist: status code: 2
        if (!isUpdate && temp != null) {
                return 2;
        }
        // if update, account not exist: status code: 5
        if (isUpdate && temp == null) {
            return 5;
        }
        // if update fetch the original info
        if (isUpdate) {
            // when update if password is error: status code: 4
            if (!isAdminister && !encoder.matches(sysUser.getPassword(), temp.getPassword())) return 4;
            sysUser.setId(temp.getId());
            sysUser.setUpdateUser(getOne(new QueryWrapper<SysUser>().eq("account", UserUtil.getCurrentUserAccount())).getId());
            sysUser.setCreateTime(temp.getCreateTime());
        } else {
            // to encrypt the raw password
            sysUser.setPassword(encoder.encode(sysUser.getPassword()));
        }

        if (revisePassword) {
            sysUser.setPassword(encoder.encode(newPassword));
        }

        // if email exist: status code: 3
        // judge others... status code: 10
        return 0;
    }
}
