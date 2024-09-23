package com.trading.model;

import com.trading.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnable=false;
    private VerificationType sendTo;
}
