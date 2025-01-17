package com.daretodo.keyboardnotifier.product.controller.dto;

import jakarta.validation.constraints.Email;

public record GroupBuySubscribeRequest(
    @Email
    String email
) {

}
