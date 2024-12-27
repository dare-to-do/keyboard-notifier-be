package com.daretodo.keyboardnotifier.groupbuy.domain;

public enum GroupBuyStatus {
    PENDING,    // 시작 전
    ACTIVE,     // 진행 중
    COMPLETED,  // 성공적으로 완료
    FAILED,     // 최소 인원 미달로 실패
    CANCELLED   // 취소됨
}
