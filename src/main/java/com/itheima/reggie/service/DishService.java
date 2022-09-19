package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    public void saveDishWithFlavor(DishDto dishDto);

    public DishDto getByIDWithFlavor(Long id);

    public void updateDishWithFlavor(DishDto dishDto);
}
