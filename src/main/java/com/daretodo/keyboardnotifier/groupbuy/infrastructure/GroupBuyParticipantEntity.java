package com.daretodo.keyboardnotifier.groupbuy.infrastructure;

import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyParticipant;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyParticipantStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_buy_participants",
       uniqueConstraints = @UniqueConstraint(columnNames = {"group_buy_id", "user_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupBuyParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_buy_id", nullable = false)
    private Long groupBuyId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupBuyParticipantStatus status;

    public static GroupBuyParticipantEntity from(GroupBuyParticipant participant) {
        GroupBuyParticipantEntity entity = new GroupBuyParticipantEntity();
        entity.id = participant.getId();
        entity.groupBuyId = participant.getGroupBuyId();
        entity.userId = participant.getUserId();
        entity.joinedAt = participant.getJoinedAt();
        entity.status = participant.getStatus();
        return entity;
    }

    public GroupBuyParticipant toDomain() {
        return GroupBuyParticipant.builder()
            .id(id)
            .groupBuyId(groupBuyId)
            .userId(userId)
            .joinedAt(joinedAt)
            .status(status)
            .build();
    }

    public void updateStatus(GroupBuyParticipantStatus groupBuyParticipantStatus) {
        this.status = groupBuyParticipantStatus;
    }
}
