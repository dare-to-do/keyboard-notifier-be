package com.daretodo.keyboardnotifier.groupbuy.controller;

import jakarta.validation.constraints.Email;

public record GroupBuySubscribeRequest(
    @Email
    String email
) {

}
