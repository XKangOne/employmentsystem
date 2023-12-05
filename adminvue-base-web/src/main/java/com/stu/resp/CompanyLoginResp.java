package com.stu.resp;


import com.stu.system.entity.Companys;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyLoginResp {
    private Companys companys;
    private String token;
}
