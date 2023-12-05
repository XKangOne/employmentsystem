package com.stu.resp;


import com.stu.system.entity.Instructors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstructorsLoginResp {
    private Instructors instructors;
    private String token;
}
