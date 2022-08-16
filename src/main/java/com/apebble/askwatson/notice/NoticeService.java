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

    private final NoticeJpaRepository noticeJpaRepository;

    // 공지사항 등록
    public Notice createNotice(NoticeParams params) {
        Notice notice = Notice.builder()
                .title(params.getTitle())
                .content(params.getContent())
                .build();
        return noticeJpaRepository.save(notice);
    }

    // 공지사항 전체 조회
    public List<Notice> getNotices() {
        return noticeJpaRepository.findAll();
    }

    // 공지사항 단건 조회
    public Notice getOneNotice(Long noticeId) {
        return noticeJpaRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
    }

    // 공지사항 수정
    public Notice modifyNotice(Long noticeId, NoticeParams params) {
        Notice notice = noticeJpaRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
        notice.update(params);
        return notice;
    }

    // 공지사항 삭제
    public void deleteNotice(Long noticeId) {
        Notice notice = noticeJpaRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
        noticeJpaRepository.delete(notice);
    }
}
