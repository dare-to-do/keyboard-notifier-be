package com.daretodo.keyboardnotifier.groupbuy.application;

import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuy;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyParticipant;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyParticipantStatus;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyRepository;
import com.daretodo.keyboardnotifier.user.domain.User;
import com.daretodo.keyboardnotifier.user.domain.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
