package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐同时保存套餐和菜品关联关系
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐同时删除套餐和菜品对应关系表条目
     */
    public void removeWithDish(List<Long> ids);

    public SetmealDto getByIdWithDishs(Long id);

    public void updateSetmealWithDish(SetmealDto setmealDto);
}
