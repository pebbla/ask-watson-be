package com.apebble.askwatson.faq;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.apebble.askwatson.faq.QFaq.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class FaqQueryRepositoryTest {
    @PersistenceContext EntityManager em;
    JPAQueryFactory queryFactory;


    @BeforeEach // 각 개별 테스트 실행 전 한번씩 실행됨
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        FaqDto.Request params1 = new FaqDto.Request();
        params1.setTitle("자묻질1"); params1.setContent("자주묻는질문1 내용입니당");
        Faq faq1 = Faq.create(params1);

        FaqDto.Request params2 = new FaqDto.Request();
        params1.setTitle("자묻질2"); params1.setContent("자주묻는질문2 내용입니당");
        Faq faq2 = Faq.create(params1);

        FaqDto.Request params3 = new FaqDto.Request();
        params1.setTitle("자묻질3"); params1.setContent("자주묻는질문3 내용입니당");
        Faq faq3 = Faq.create(params1);

        em.persist(faq1); em.persist(faq2); em.persist(faq3);
    }

    @Test
    public void 자주묻는질문_단건조회_제목() {
        Faq findFaq = queryFactory
                .selectFrom(faq)
                .where(faq.title.eq("자묻질2"))
                .fetchOne();

        assertThat(findFaq.getTitle()).isEqualTo("자묻질2");
    }

    @Test
    public void 자주묻는질문_목록조회_검색어() {
        List<Faq> faqs = queryFactory
                .selectFrom(faq)
                .where(faq.title.contains(" ")
                        .or(faq.content.contains("2")))
                .fetch();

        for (Faq faq : faqs) {
            System.out.println(faq.getId() + ": " + faq.getTitle());
        }
    }
}
