package com.example.querydsl;

import static org.assertj.core.api.Assertions.*;

import com.example.querydsl.entity.Hello;
import com.example.querydsl.entity.QHello;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

  @PersistenceContext
  private EntityManager em;

  @Test
  void contextLoads() {
    Hello hello = new Hello();
    em.persist(hello);

    JPAQueryFactory query = new JPAQueryFactory(em);
    QHello qHello = QHello.hello; // static하게 자기 자신걸 만들어 놓기 때문에 이걸 사용해도 된다.

    Hello result = query
        .selectFrom(qHello)
        .fetchOne();

    assertThat(result).isEqualTo(hello);
    assertThat(result.getId()).isEqualTo(hello.getId());
  }

}
