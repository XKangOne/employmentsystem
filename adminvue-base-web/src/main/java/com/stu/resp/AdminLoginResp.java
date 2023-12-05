package com.stu.resp;


import com.stu.system.entity.Admins;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminLoginResp {
    private String utel;
    private String password;
}
