package com.example.reactivex.laihuola;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginBody {

    private String phoneNumber;
    private String captchas;

}
