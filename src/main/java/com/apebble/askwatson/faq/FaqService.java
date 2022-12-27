package com.apebble.askwatson.faq;

import com.apebble.askwatson.comm.exception.FaqNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FaqService {

    private final FaqJpaRepository faqJpaRepository;

    /**
     * 자주묻는질문 등록
     */
    public Long createFaq(FaqDto.Request params) {
        return faqJpaRepository.save(Faq.create(params)).getId();
    }


    /**
     * 자주묻는질문 전체 조회
     */
    @Transactional(readOnly = true)
    public List<Faq> getFaqs(String searchWord) {
        List<Faq> faqs =  (searchWord == null)
                ? faqJpaRepository.findAll()
                : faqJpaRepository.findFaqsBySearchWord(searchWord);

        return faqs;
    }


    /**
     * 자주묻는질문 단건 조회
     */
    @Transactional(readOnly = true)
    public Faq getOneFaq(Long faqId) {
        return faqJpaRepository.findById(faqId).orElseThrow(FaqNotFoundException::new);
    }


    /**
     * 자주묻는질문 수정
     */
    public void modifyFaq(Long faqId, FaqDto.Request params) {
        Faq faq = faqJpaRepository.findById(faqId).orElseThrow(FaqNotFoundException::new);
        faq.update(params);
    }


    /**
     * 자주묻는질문 삭제
     */
    public void deleteFaq(Long faqId) {
        Faq faq = faqJpaRepository.findById(faqId).orElseThrow(FaqNotFoundException::new);
        faqJpaRepository.delete(faq);
    }

}
