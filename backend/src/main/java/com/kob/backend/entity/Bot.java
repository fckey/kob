package com.kob.backend.entity;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kob.backend.utils.EncDesUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description bot实体类
 * @date 2022/10/18 21:08:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_bot")
public class Bot extends Model<Bot> {

    /**
     * 用户的唯一id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户的id
     */
    private Integer userId;

    /**
     * bot的标题
     */
    private String title;

    /**
     * bot的备注
     */
    private String remark;

    /**
     * bot内容
     */
    private String content;

    /**
     * bot所获得的积分
     */
    private Integer rating;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    /**
     * 数据校验位
     */
    private String verify;

    /**
     * 数据状态 0正常 1异常
     */
    private String status = "0";

    public String getDecVerify() {
        return EncDesUtil.dec(verify);
    }

    /**
     * 联合插入bot字段, 对一些字段进行联合加密
     * @param bot
     */
    public void setEncVerify(Bot bot){
        Bot obj = new Bot();
        BeanUtil.copyProperties(bot, obj);
        String[] includeProperties = {"userId", "content", "rating", "status"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter  = filters.addFilter();
        excludefilter.addIncludes(includeProperties);
        String verify = JSON.toJSONString(obj, excludefilter, SerializerFeature.WriteNullStringAsEmpty);
        bot.setVerify(EncDesUtil.enc(verify));
    }

    @Override
    public Serializable pkVal() {
        return super.pkVal();
    }
}
