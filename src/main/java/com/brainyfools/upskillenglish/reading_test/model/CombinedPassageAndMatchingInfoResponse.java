package com.brainyfools.upskillenglish.reading_test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinedPassageAndMatchingInfoResponse {

    private MultiplePassageForm passages;
    private MatchingInfoForm matchingInfo;

}
