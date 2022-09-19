package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     *  新增菜品同时保存口味
     * @param dishDto
     */
    @Transactional
    public void saveDishWithFlavor(DishDto dishDto) {
        //保存基本的菜品信息到菜品表
        this.save(dishDto);
        //保存口味信息到菜品口味表
        Long dishID = dishDto.getId();
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        dishFlavors = dishFlavors.stream().map((item)->{
            item.setDishId(dishID);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(dishFlavors);
    }

    /**
     * 菜品及对应口味信息的数据回显
     * @param id
     * @return
     */
    @Override
    public DishDto getByIDWithFlavor(Long id) {
        //查询ID对应的菜品基本信息
        Dish dish = this.getById(id);
        //查询当前菜品对应的口味信息
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getId,id);
        List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper);

        //重新封装查询结果
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(dishFlavors);

        return dishDto;
    }

    /**
     * 菜品及对应口味信息的更新操作
     * @param dishDto
     */
    @Transactional
    public void updateDishWithFlavor(DishDto dishDto) {
        //更新基本的dish表信息
        this.updateById(dishDto);
        //删除dish_flavor表中对应菜品的口味信息
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getId,dishDto.getId());
        dishFlavorService.remove(lambdaQueryWrapper);
        //插入更新过后的口味信息
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        Long dishID = dishDto.getId();
        dishFlavors = dishFlavors.stream().map((item)->{
            item.setDishId(dishID);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(dishFlavors);
    }
}
