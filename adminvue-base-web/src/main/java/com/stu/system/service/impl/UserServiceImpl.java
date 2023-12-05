package com.stu.system.service.impl;


import com.stu.handler.BusinessException;
import com.stu.response.ResultCode;
import com.stu.system.entity.Classes;
import com.stu.system.entity.Colleges;
import com.stu.system.entity.Majors;
import com.stu.system.entity.User;
import com.stu.system.mapper.ClassesMapper;
import com.stu.system.mapper.CollegesMapper;
import com.stu.system.mapper.MajorsMapper;
import com.stu.system.mapper.UserMapper;
import com.stu.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassesMapper classesMapper;
    @Autowired
    private CollegesMapper collegesMapper;
    @Autowired
    private MajorsMapper majorsMapper;


    @Override
    public IPage<User> findUserPage(Page<User> page, QueryWrapper<User> wrapper) {
        return this.baseMapper.findUserPage(page,wrapper);
    }

    @Override
    public void loginUser(String phone_number, String password, Integer usertype) {
      QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("stel",phone_number);
        userWrapper.eq("spwd",password);
        Integer count = this.baseMapper.selectCount(userWrapper);
        System.out.println("count="+count);
        if(count == 0){
            throw new BusinessException(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode(),ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
    }

    @Override
    public User findUserById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public User findUserByTel(String tel) {
        return userMapper.findUserByTel(tel);
    }

    @Override
    public List<User> findUsersByTel(String tel) {
        return userMapper.findUsersByTel(tel);
    }

    @Override
    public void updateUser(User user) {
        User userOld = userMapper.selectById(user.getId());
        if(userOld !=null){
            if(!userOld.getCollegeid().equals(user.getCollegeid())){
                Colleges colleges = collegesMapper.selectById(user.getCollegeid());
                if(colleges != null){
                    //修改后的学院人数加1
                    int totalcount = colleges.getCtotalnums()+1;
                    colleges.setCtotalnums(totalcount);
                    collegesMapper.updateById(colleges);
                }
                Colleges colleges2 = collegesMapper.selectById(userOld.getCollegeid());
                if(colleges2 != null){
                    //原来的学院人数减1
                    int totalcount = colleges.getCtotalnums()-1;
                    colleges.setCtotalnums(totalcount);
                    collegesMapper.updateById(colleges);
                }
            }
            if(!userOld.getMarjorid().equals(user.getMarjorid())){
                Majors majors = majorsMapper.selectById(user.getMarjorid());
                if(majors != null){
                    //修改后的专业人数加1
                    int totalcount = majors.getCtotalnums()+1;
                    majors.setCtotalnums(totalcount);
                    majorsMapper.updateById(majors);
                }

                Majors majors2 = majorsMapper.selectById(userOld.getMarjorid());
                if(majors2 != null){
                    //修改后的专业人数加1
                    int totalcount = majors2.getCtotalnums()+1;
                    majors2.setCtotalnums(totalcount);
                    majorsMapper.updateById(majors2);
                }
            }
            if(!userOld.getClassid().equals(user.getClassid())){
                Classes classes = classesMapper.selectById(user.getClassid());
                if(classes != null){
                    //修改后的班级人数加1
                    int totalcount = classes.getCtotalnums()+1;
                    classes.setCtotalnums(totalcount);
                    classesMapper.updateById(classes);
                }
                Classes classes2 = classesMapper.selectById(userOld.getClassid());
                if(classes2 != null){
                    //原来的班级人数减1
                    int totalcount2 = classes2.getCtotalnums()-1;
                    classes2.setCtotalnums(totalcount2);
                    classesMapper.updateById(classes2);
                }
            }
        }
       this.baseMapper.updateById(user);
    }

    @Override
    public void updateUsersByClassid(User bean) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("classid", bean.getClassid());
        this.baseMapper.update(bean, wrapper);
    }

    @Override
    public void updateBymarjorid(User bean) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("marjorid", bean.getMarjorid());
        this.baseMapper.update(bean, wrapper);
    }

    @Override
    public void updateBycollegeid(User bean) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("collegeid", bean.getCollegeid());
        this.baseMapper.update(bean, wrapper);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userMapper.selectById(id);
        if(user !=null){
            Colleges colleges = collegesMapper.selectById(user.getCollegeid());
            if(colleges != null){
                //修改后的学院人数减1
                int totalcount = colleges.getCtotalnums()-1;
                colleges.setCtotalnums(totalcount);
                collegesMapper.updateById(colleges);
            }
            Majors majors = majorsMapper.selectById(user.getMarjorid());
            if(majors != null){
                //修改后的班级人数减1
                int totalcount = majors.getCtotalnums()-1;
                majors.setCtotalnums(totalcount);
                majorsMapper.updateById(majors);
            }
            Classes classes = classesMapper.selectById(user.getClassid());
            if(classes != null){
                int totalcount = classes.getCtotalnums()-1;
                classes.setCtotalnums(totalcount);
                classesMapper.updateById(classes);
            }
        }
        this.baseMapper.deleteById(id);
    }

    @Override
    public void addUser(User user) {
        String username = user.getSname();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("sname",username);
        Integer count = this.baseMapper.selectCount(wrapper);
        if(count!=0){
            throw new BusinessException(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode(),
                    ResultCode.USER_ACCOUNT_ALREADY_EXIST.getMessage());
        }
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.baseMapper.insert(user);
        Classes classes = classesMapper.selectById(user.getClassid());
        if(classes != null){
            int totalcount = classes.getCtotalnums()+1;
            classes.setCtotalnums(totalcount);
            classesMapper.updateById(classes);
        }
        Colleges colleges = collegesMapper.selectById(user.getCollegeid());
        if(colleges != null){
            //修改后的学院人数加1
            int totalcount = colleges.getCtotalnums()+1;
            colleges.setCtotalnums(totalcount);
            collegesMapper.updateById(colleges);
        }
        Majors majors = majorsMapper.selectById(user.getMarjorid());
        if(majors != null){
            //修改后的专业人数加1
            int totalcount = majors.getCtotalnums()+1;
            majors.setCtotalnums(totalcount);
            majorsMapper.updateById(majors);
        }
    }


}
