package com.apebble.askwatson.cafe;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;


import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Api(tags = {"카페"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class CafeController {

    private final CafeService cafeService;
    private final ResponseService responseService;

    // 방탈출 카페 등록
    @PostMapping(value="/admin/cafes", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public SingleResponse<CafeDto.Response> createCafe(@RequestPart CafeDto.Request params,
                                                       @RequestPart(value = "file", required = false) MultipartFile file) throws ParseException  {
        Long cafeId = cafeService.createCafe(params, file);
        return responseService.getSingleResponse(toDto(cafeService.findOne(cafeId)));
    }

    // 방탈출 카페 전체 조회
    @GetMapping(value="/cafes")
    public PageResponse<CafeDto.Response> getCafes(CafeSearchOptions searchOptions,
                                                   @PageableDefault(size=20) Pageable pageable) {
        return responseService.getPageResponse(toDtoPage(cafeService.getCafes(searchOptions, pageable)));
    }

    // 방탈출 카페 전체 조회(리스트 - 관리자웹 개발용)
    @GetMapping(value="/admin/cafes")
    public ListResponse<CafeDto.Response> getCafeList(@RequestParam(required = false) String searchWord,
                                                      @RequestParam(required = false) Boolean sortByUpdateYn) {
        return responseService.getListResponse(toDtoList(cafeService.getCafeList(searchWord, sortByUpdateYn)));
    }

    // 방탈출 카페 단건 조회
    @GetMapping(value = "/cafes/{cafeId}")
    public SingleResponse<CafeDto.Response> getCafe(@PathVariable Long cafeId) {
        return responseService.getSingleResponse(toDto(cafeService.findOne(cafeId)));
    }

    // 방탈출 카페 수정
    @PutMapping(value = "/admin/cafes/{cafeId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public SingleResponse<CafeDto.Response> modifyCafe(@PathVariable Long cafeId,
                                                       @RequestPart CafeDto.Request params,
                                                       @RequestPart(value = "file", required = false) MultipartFile file) throws ParseException {
        cafeService.modifyCafe(cafeId, params, file);
        return responseService.getSingleResponse(toDto(cafeService.findOne(cafeId)));
    }

    // 방탈출 카페 삭제
    @PatchMapping(value = "/admin/cafes/{cafeId}/unavailable") 
    public CommonResponse deleteUselessCafeInfo(@PathVariable Long cafeId) {
        cafeService.deleteUselessCafeInfo(cafeId);
        return responseService.getSuccessResponse();
    }

    //==DTO 변환 메서드==//
    private Page<CafeDto.Response> toDtoPage(Page<Cafe> cafeList){
        return cafeList.map(CafeDto.Response::new);
    }

    private List<CafeDto.Response> toDtoList(List<Cafe> cafeList){
        return cafeList.stream().map(CafeDto.Response::new).collect(toList());
    }

    private CafeDto.Response toDto(Cafe cafe){
        return new CafeDto.Response(cafe);
    }
}
