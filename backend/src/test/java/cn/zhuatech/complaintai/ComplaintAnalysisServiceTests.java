/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.complaintai;

import cn.zhuatech.complaintai.service.ComplaintAnalysisService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class ComplaintAnalysisServiceTests {
    private final ComplaintAnalysisService service = new ComplaintAnalysisService();
    @Test void escalatesSafetyComplaintImmediately() {
        var result = service.analyze(new ComplaintAnalysisService.Request("CP-1001", "热线", 22, 3, 280, true, new BigDecimal("18000"), true));
        assertThat(result.severity()).isEqualTo("CRITICAL");
        assertThat(result.managerApprovalRequired()).isTrue();
        assertThat(result.responseWithinMinutes()).isEqualTo(15);
    }
    @Test void keepsNormalCaseInStandardService() {
        var result = service.analyze(new ComplaintAnalysisService.Request("CP-1002", "在线", 86, 0, 15, false, BigDecimal.ZERO, false));
        assertThat(result.handlingQueue()).isEqualTo("STANDARD_SERVICE");
    }
}
