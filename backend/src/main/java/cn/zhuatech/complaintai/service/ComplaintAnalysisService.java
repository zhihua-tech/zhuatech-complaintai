/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.complaintai.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 对投诉情绪、时效、复投与安全信号进行可解释分级。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class ComplaintAnalysisService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result analyze(Request request) {
        int score = Math.max(0, 55 - request.sentimentScore());
        score += Math.min(24, request.repeatComplaints() * 8);
        if (request.overdueMinutes() > 240) score += 22;
        else if (request.overdueMinutes() > 60) score += 10;
        if (request.safetyOrComplianceRisk()) score += 35;
        if (request.compensationAmount().compareTo(new BigDecimal("10000")) >= 0) score += 15;
        if (request.socialMediaExposure()) score += 18;
        score = Math.min(100, score);
        String severity = score >= 75 ? "CRITICAL" : score >= 45 ? "HIGH" : score >= 25 ? "MEDIUM" : "LOW";
        List<String> reasons = new ArrayList<>();
        if (request.sentimentScore() < 40) reasons.add("客户情绪显著负向");
        if (request.repeatComplaints() > 1) reasons.add("同一问题重复投诉");
        if (request.overdueMinutes() > 60) reasons.add("投诉处理已超过服务时限");
        if (request.safetyOrComplianceRisk()) reasons.add("涉及安全或合规风险");
        if (request.socialMediaExposure()) reasons.add("存在公开舆情扩散风险");
        if (reasons.isEmpty()) reasons.add("未命中高风险投诉信号");
        String queue = score >= 75 ? "EXECUTIVE_ESCALATION" : score >= 45 ? "SPECIALIST_REVIEW" : "STANDARD_SERVICE";
        int responseWithinMinutes = score >= 75 ? 15 : score >= 45 ? 60 : score >= 25 ? 240 : 480;
        return new Result(request.complaintNo(), score, severity, queue, responseWithinMinutes,
            reasons, request.safetyOrComplianceRisk() || request.compensationAmount().compareTo(new BigDecimal("10000")) >= 0);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank String complaintNo, @NotBlank String channel,
                          @Min(0) @Max(100) int sentimentScore,
                          @Min(0) @Max(20) int repeatComplaints,
                          @Min(0) int overdueMinutes, boolean safetyOrComplianceRisk,
                          @DecimalMin("0") BigDecimal compensationAmount,
                          boolean socialMediaExposure) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(String complaintNo, int riskScore, String severity, String handlingQueue,
                         int responseWithinMinutes, List<String> reasons, boolean managerApprovalRequired) {}
}
