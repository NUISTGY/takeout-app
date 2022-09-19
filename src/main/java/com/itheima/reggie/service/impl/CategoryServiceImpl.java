package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;


    /**
     * 通过自定义方式完善删除功能
     * @param id
     */
    @Override
    public void remove(Long id) {

        //添加查询条件，根据分类ID进行查询
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int dishCount = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了相关菜品
        if(dishCount > 0){
            throw new CustomException("当前分类下关联了其他菜品，无法删除！");
        }
        //添加查询条件，根据分类ID进行查询
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int setCount = setmealService.count(setmealLambdaQueryWrapper);
        //查询当前分类是否关联了相关套餐
        if(setCount > 0){
            throw new CustomException("当前分类下关联了其他套餐，无法删除！");
        }
        //正常删除该分类
        super.removeById(id);
    }
}
