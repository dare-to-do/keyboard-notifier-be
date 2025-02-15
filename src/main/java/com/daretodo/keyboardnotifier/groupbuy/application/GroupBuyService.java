package com.daretodo.keyboardnotifier.groupbuy.application;

import com.daretodo.keyboardnotifier.groupbuy.domain.*;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.user.domain.User;
import com.daretodo.keyboardnotifier.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupBuyService {

    private final GroupBuyRepository groupBuyRepository;
    private final UserRepository userRepository;

    @Transactional
    public void subscribeGroupBuy(Long productId, String email) {
        GroupBuy groupBuy = groupBuyRepository.findByProductId(productId);
        User user = userRepository.findByEmail(email)
            .orElseGet(() -> userRepository.save(User.builder().email(email).build()));

        groupBuy.validAlreadyParticipatedUser(user.getId());

        GroupBuyParticipant groupBuyParticipant = GroupBuyParticipant.builder()
            .groupBuyId(groupBuy.getId())
            .userId(user.getId())
            .joinedAt(LocalDateTime.now())
            .status(GroupBuyParticipantStatus.PARTICIPATED)
            .build();
        groupBuy.addParticipant(groupBuyParticipant);

        groupBuyRepository.save(groupBuy);
    }

    @Transactional
    public void createGroupBuys(List<Product> products) {
        List<GroupBuy> groupBuys = products.stream()
                .map(product -> {
                    LocalDateTime startDateTime = product.getPeriod().orElseThrow().getStartDate();
                    LocalDateTime endDateTime = product.getPeriod().orElseThrow().getEndDate();

                    return GroupBuy.builder()
                        .productId(product.getId())
                        .startDateTime(startDateTime)
                        .endDateTime(endDateTime)
                        .status(GroupBuyStatus.valueOf(product.getStatus().name()))
                        .build();
                }).toList();

        groupBuyRepository.saveAll(groupBuys);
    }
}
