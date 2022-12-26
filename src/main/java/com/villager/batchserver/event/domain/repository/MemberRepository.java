package com.villager.batchserver.event.domain.repository;

import com.villager.batchserver.event.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
