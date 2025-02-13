package com.daretodo.keyboardnotifier.groupbuy.application;

import org.springframework.stereotype.Service;

@Service
public class GroupBuyEmailContentGenerator {
    public String generateStartTitle(String productName) {
        return String.format("[Sokey]%s 공제 시작", productName);
    }

    public String generateStartContent(String productName, String endDate, String productLink, String feedbackFormLink) {
        return String.format(
                "<html>" +
                        "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                        "<p>안녕하세요, <b>Sokey</b>에서 공제 일정 소식 알려드립니다.</p>" +
                        "<p>관심상품으로 등록하신 <b>%s</b> 공제가 오늘부터 <b>%s</b>까지 진행 될 예정입니다.</p>" +
                        "<p>관련 상세 정보는 아래 링크를 통해 확인하세요.</p>" +
                        "<p><a href='%s' style='color: #0066cc; text-decoration: none;'><b>%s</b></a></p>" +
                        "<p>Sokey 서비스를 이용하시면서 불편하셨던 점이나 개선 사항이 있다면 문의 남겨주세요.</p>" +
                        "<p><a href='%s' style='color: #0066cc; text-decoration: none;'><b>%s</b></a></p>" +
                        "</body>" +
                        "</html>",
                productName, endDate, productLink, productLink, feedbackFormLink, feedbackFormLink
        );
    }
}
