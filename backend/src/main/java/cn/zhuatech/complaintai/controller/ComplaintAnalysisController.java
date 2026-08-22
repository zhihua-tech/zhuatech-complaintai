/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.complaintai.controller;

import cn.zhuatech.complaintai.common.ApiResponse;
import cn.zhuatech.complaintai.service.ComplaintAnalysisService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/complaint")
@PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')")
public class ComplaintAnalysisController {
    private final ComplaintAnalysisService service;
    public ComplaintAnalysisController(ComplaintAnalysisService service) { this.service = service; }
    @PostMapping("/analyze")
    public ApiResponse<ComplaintAnalysisService.Result> analyze(@Valid @RequestBody ComplaintAnalysisService.Request request) {
        return ApiResponse.ok("投诉风险分级完成", service.analyze(request));
    }
}
