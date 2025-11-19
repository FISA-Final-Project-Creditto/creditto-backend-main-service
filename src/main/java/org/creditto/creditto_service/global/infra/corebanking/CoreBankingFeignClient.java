package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceRecordDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "core-banking",
        url = "${CORE_BANKING_SERVER_URL}"
)
public interface CoreBankingFeignClient {

    // 등록된 정기 해외 송금 설정 조회
    @GetMapping("/api/core/remittance/schedule")
    List<RegularRemittanceDto> getScheduledRemittancesByUserId(@RequestParam("userId") String userId);

    // 한 건의 정기 해외 송금 기록 상세 조회
    @GetMapping("/api/core/remittance/schedule/{recurId}")
    List<RemittanceRecordDto> getRemittanceRecordsByRecurId(@PathVariable("recurId") String recurId, @RequestParam("userId") String userId);

    // 정기 해외 송금 내역 수정
    @PutMapping("/api/core/remittance/schedule/{recurId}")
    void updateScheduledRemittance(
            @PathVariable("recurId") String recurId,
            @RequestParam("userId") String userId,
            @RequestBody RegularRemittanceDto regularRemittanceDto
    );

    // 정기 해외 송금 설정 삭제
    @DeleteMapping("/api/core/remittance/schedule/{recurId}")
    void cancelScheduledRemittance(
            @PathVariable("recurId") String recurId,
            @RequestParam("userId") String userId
    );
}
