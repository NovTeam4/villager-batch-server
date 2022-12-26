package com.villager.batchserver.event.domain.repository;

import com.villager.batchserver.event.domain.Member;

import java.util.List;

public interface MemberQueryRepository {
    long getTagTotalCount(Long townId, String tag);
    List<Member> getTagAttentionMember(Long townId, String tag, int offset, int size);
}
