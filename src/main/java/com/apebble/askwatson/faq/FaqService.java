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

    private final FaqRepository faqRepository;
    private final FaqQueryRepository faqQueryRepository;

    /**
     * 자주묻는질문 등록
     */
    public Long createFaq(FaqDto.Request params) {
        return faqRepository.save(Faq.create(params)).getId();
    }


    /**
     * 자주묻는질문 전체 조회
     */
    @Transactional(readOnly = true)
    public List<Faq> getFaqs(String searchWord) {
        return faqQueryRepository.findFaqsBySearchWord(searchWord);
    }


    /**
     * 자주묻는질문 단건 조회
     */
    @Transactional(readOnly = true)
    public Faq getOneFaq(Long faqId) {
        return faqRepository.findById(faqId).orElseThrow(FaqNotFoundException::new);
    }


    /**
     * 자주묻는질문 수정
     */
    public void modifyFaq(Long faqId, FaqDto.Request params) {
        Faq faq = faqRepository.findById(faqId).orElseThrow(FaqNotFoundException::new);
        faq.update(params);
    }


    /**
     * 자주묻는질문 삭제
     */
    public void deleteFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId).orElseThrow(FaqNotFoundException::new);
        faqRepository.delete(faq);
    }

}
