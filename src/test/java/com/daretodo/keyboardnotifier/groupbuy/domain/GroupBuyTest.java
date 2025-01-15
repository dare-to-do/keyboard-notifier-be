package com.daretodo.keyboardnotifier.groupbuy.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class GroupBuyTest {

    @Nested
    @DisplayName("공제 참여 가능 여부")
    class canJoin {

        @Test
        void 진행중_상태의_공제에만_참여가_가능하다() {
            // given
            GroupBuy groupBuy = GroupBuy.builder()
                .status(GroupBuyStatus.ACTIVE)
                .endDateTime(LocalDateTime.now().plusDays(1))
                .build();

            // when
            boolean result = groupBuy.canJoin();

            // then
            assertThat(result).isTrue();
        }

        @ParameterizedTest
        @EnumSource(value = GroupBuyStatus.class, mode = EnumSource.Mode.EXCLUDE, names = "ACTIVE")
        void 진행중_상태가_아닌_공제는_참여가_불가능하다(GroupBuyStatus status) {
            // given
            GroupBuy groupBuy = GroupBuy.builder()
                .status(status)
                .endDateTime(LocalDateTime.now().plusDays(1))
                .build();

            // when
            boolean result = groupBuy.canJoin();

            // then
            assertThat(result).isFalse();
        }

        @Test
        void 이미_지난_공제_날짜의_공제는_참여가_불가능하다() {
            // given
            GroupBuy groupBuy = GroupBuy.builder()
                .endDateTime(LocalDateTime.now().minusDays(1))
                .build();

            // when
            boolean result = groupBuy.canJoin();

            // then
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("공제 참여자 추가")
    class addParticipant {

        @Test
        void 공제_참여가_불가능한_경우_예외가_발생한다() {
            // given
            GroupBuy groupBuy = GroupBuy.builder()
                .status(GroupBuyStatus.COMPLETED)
                .build();
            GroupBuyParticipant participant = GroupBuyParticipant.builder().build();

            // when, then
            assertThatIllegalStateException()
                .isThrownBy(() -> groupBuy.addParticipant(participant));
        }

        @Test
        void 이미_참여한_사용자가_공제에_참여하려고_하는_경우_예외가_발생한다() {
            // given
            GroupBuy groupBuy = GroupBuy.builder()
                .status(GroupBuyStatus.ACTIVE)
                .endDateTime(LocalDateTime.now().plusDays(1))
                .participants(List.of(
                    GroupBuyParticipant.builder().userId(1L).status(GroupBuyParticipantStatus.ACTIVE).build()
                ))
                .build();
            GroupBuyParticipant alreadyJoinedParticipant = GroupBuyParticipant.builder().userId(1L).build();

            // when, then
            assertThatIllegalStateException()
                .isThrownBy(() -> groupBuy.addParticipant(alreadyJoinedParticipant));
        }
    }


}
