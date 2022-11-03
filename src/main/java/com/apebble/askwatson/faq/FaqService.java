package com.apebble.askwatson.faq;

import com.apebble.askwatson.comm.exception.FaqNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FaqService {

    private final FaqJpaRepository faqJpaRepository;

    // 방탈출 카페 등록
    public Faq createFaq(FaqParams params) {
        Faq faq = Faq.builder()
                .title(params.getTitle())
                .content(params.getContent())
                .build();
        return faqJpaRepository.save(faq);
    }

    // 방탈출 카페 전체 조회
    @Transactional(readOnly = true)
    public List<Faq> getFaqs(String searchWord) {
        return (searchWord == null)
                ? faqJpaRepository.findAll()
                : faqJpaRepository.findFaqsBySearchWord(searchWord);
    }

    // 방탈출 카페 단건 조회
    public Faq getOneFaq(Long faqId) {
        return faqJpaRepository.findById(faqId).orElseThrow(FaqNotFoundException::new);
    }

    // 방탈출 카페 수정
    public Faq modifyFaq(Long faqId, FaqParams params) {
        Faq faq = faqJpaRepository.findById(faqId).orElseThrow(FaqNotFoundException::new);
        faq.update(params);
        return faq;
    }

    // 방탈출 카페 삭제
    public void deleteFaq(Long faqId) {
        Faq faq = faqJpaRepository.findById(faqId).orElseThrow(FaqNotFoundException::new);
        faqJpaRepository.delete(faq);
    }
}
