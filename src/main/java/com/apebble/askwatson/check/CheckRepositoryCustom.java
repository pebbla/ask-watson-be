package com.apebble.askwatson.check;

import java.util.List;

public interface CheckRepositoryCustom {

    List<Check> findByUserId(Long userId);

}
