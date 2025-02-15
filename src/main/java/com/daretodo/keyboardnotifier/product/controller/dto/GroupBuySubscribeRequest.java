package com.daretodo.keyboardnotifier.product.controller.dto;

import jakarta.validation.constraints.Email;

public record GroupBuySubscribeRequest(
    String email
) {

}
