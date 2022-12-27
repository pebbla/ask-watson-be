package com.apebble.askwatson.notice;

import com.apebble.askwatson.comm.exception.NoticeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeQueryRepository noticeQueryRepository;


    /**
     * 공지사항 등록
     */
    public Long createNotice(NoticeDto.Request request) {
        return noticeRepository.save(Notice.create(request)).getId();
    }


    /**
     * 공지사항 전체 조회
     */
    @Transactional(readOnly = true)
    public List<Notice> getNotices(String searchWord) {
        return noticeQueryRepository.findNoticesBySearchWord(searchWord);
    }


    /**
     * 공지사항 단건 조회
     */
    @Transactional(readOnly = true)
    public NoticeDto.Response getOneNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
        return new NoticeDto.Response(notice);
    }


    /**
     * 공지사항 수정
     */
    public void modifyNotice(Long noticeId, NoticeDto.Request params) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
        notice.update(params);
    }


    /**
     * 공지사항 삭재
     */
    public void deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
        noticeRepository.delete(notice);
    }

}
