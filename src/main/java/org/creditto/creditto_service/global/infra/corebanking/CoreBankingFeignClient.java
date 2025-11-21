package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceCreateRequestDto;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceResponseDto;
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
    List<RegularRemittanceResponseDto> getScheduledRemittancesByUserId(@RequestParam("userId") String userId);

    // 한 건의 정기 해외 송금 설정 내역 조회
    @GetMapping("/api/core/remittance/schedule/{recurId}")
    List<RemittanceRecordDto> getRemittanceRecordsByRecurId(@PathVariable("recurId") Long recurId, @RequestParam("userId") String userId);

    // TODO: 정기 해외 송금 내역 신규 등록
    @PostMapping("/api/core/remittance/schedule")
    void createScheduledRemittance(
            @RequestParam("userId") String userId,
            @RequestBody RegularRemittanceCreateRequestDto regularRemittanceCreateRequestDto
    );

    // 정기 해외 송금 내역 수정
    @PutMapping("/api/core/remittance/schedule/{recurId}")
    void updateScheduledRemittance(
            @PathVariable("recurId") Long recurId,
            @RequestParam("userId") String userId,
            @RequestBody RegularRemittanceResponseDto regularRemittanceResponseDto
    );

    // 정기 해외 송금 설정 삭제
    @DeleteMapping("/api/core/remittance/schedule/{recurId}")
    void cancelScheduledRemittance(
            @PathVariable("recurId") Long recurId,
            @RequestParam("userId") String userId
    );
}
