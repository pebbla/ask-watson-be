package com.apebble.askwatson.notice;

import com.apebble.askwatson.comm.exception.NoticeNotFoundException;
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
public class NoticeService {

    private final NoticeJpaRepository noticeJpaRepository;

    // 공지사항 등록
    public Long createNotice(NoticeDto.Params params) {
        return noticeJpaRepository.save(Notice.create(params)).getId();
    }

    // 공지사항 전체 조회
    @Transactional(readOnly = true)
    public List<NoticeDto.Response> getNotices(String searchWord) {
        List<Notice> notices = (searchWord == null)
                ? noticeJpaRepository.findAll()
                : noticeJpaRepository.findNoticesBySearchWord(searchWord);
        return convertToDtoList(notices);
    }

    // 공지사항 단건 조회
    @Transactional(readOnly = true)
    public NoticeDto.Response getOneNotice(Long noticeId) {
        Notice notice = noticeJpaRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
        return new NoticeDto.Response(notice);
    }

    // 공지사항 수정
    public void modifyNotice(Long noticeId, NoticeDto.Params params) {
        Notice notice = noticeJpaRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
        notice.update(params);
    }

    // 공지사항 삭제
    public void deleteNotice(Long noticeId) {
        Notice notice = noticeJpaRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
        noticeJpaRepository.delete(notice);
    }


    //==DTO 변환 메서드==//
    private List<NoticeDto.Response> convertToDtoList(List<Notice> notices){
        return notices.stream().map(NoticeDto.Response::new).collect(toList());
    }

}
