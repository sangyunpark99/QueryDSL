package com.example.querydsl;

import static com.example.querydsl.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QueryBasicTest {

  @Autowired
  EntityManager em;

  JPAQueryFactory queryFactory; // field-level로 이동

  @BeforeEach
  public void before() {
    queryFactory = new JPAQueryFactory(em);
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");

    em.persist(teamA);
    em.persist(teamB);

    Member member1 = new Member("member1", 10, teamA);
    Member member2 = new Member("member2", 11, teamA);

    Member member3 = new Member("member3", 12, teamB);
    Member member4 = new Member("member4", 13, teamB);

    em.persist(member1);
    em.persist(member2);
    em.persist(member3);
    em.persist(member4);
  }

  @Test
  public void jpql() {

    Member findMember = em.createQuery("select m from Member m where m.username= :username",Member.class)
        .setParameter("username", "member1")
        .getSingleResult();

    assertThat(findMember.getUsername()).isEqualTo("member1");
  }

  @Test
  public void queryDsl() {
    // QMember를 static import해서 아래와 같은 방법으로 사용하는 것을 권장합니다.

    // alias는 테이블 조인시 이름이 중복되는 것을 방지해주기 위해 존재한다.  
    Member findMember = queryFactory
        .select(member)
        .from(member)
        .where(member.username.eq("member1"))
        .fetchOne();
    // QueryDsl은 sql Injection을 막아줍니다.

    assertThat(findMember.getUsername()).isEqualTo("member1");
  }
}
