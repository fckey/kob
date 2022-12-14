package com.kob.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kob.backend.utils.EncDesUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description user的实体类
 * @date 2022/10/18 09:13:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_user")
public class User extends Model<User> {

    /**
     * 用户的唯一id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 照片
     */
    private String photo;
    @Override
    public Serializable pkVal() {
        return super.pkVal();
    }

    /**
     * 解密获取
     * @return
     */
    public String getDecUserName() {
        return EncDesUtil.dec(username);
    }
    /**
     * 加密写入
     */
    public void setEncUserName(String userName) {
        this.username = EncDesUtil.enc(userName);
    }
}
