package com.villager.batchserver.event.infra;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.villager.batchserver.event.domain.Member;
import com.villager.batchserver.event.domain.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.villager.batchserver.event.domain.QMember.member;
import static com.villager.batchserver.event.domain.QMemberTown.memberTown;
import static com.villager.batchserver.event.domain.QTown.town;


@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public long getTagTotalCount(Long townId, String tag) {

        Long totalCount = queryFactory
                .select(member.count())
                .from(memberTown)
                .join(memberTown.town, town)
                .join(memberTown.member, member)
                .where(
                        memberTown.town.id.eq(townId),
                        isActiveMember(),
                        isSameTag(tag)
                )
                .fetchOne();

        return (totalCount != null) ? totalCount : 0;
    }

    @Override
    public List<Member> getTagAttentionMember(Long townId, String tag, int offset, int size) {
        return queryFactory
                .select(member)
                .from(memberTown)
                .join(memberTown.town, town)
                .join(memberTown.member, member)
                .where(
                        memberTown.town.id.eq(townId),
                        isActiveMember(),
                        isSameTag(tag)
                )
                .offset(offset)
                .limit(size)
                .fetch();
    }

    private BooleanExpression isSameTag(String tag) {
        return member.tags.contains(tag);
    }

    private BooleanExpression isActiveMember() {
        return member.isDeleted.eq(false);
    }

}
