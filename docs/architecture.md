# ComplaintAI 架构

Vue 3 提供投诉管理端与响应式业务工作台；Spring Boot 通过 `ComplaintAnalysisService` 组合情绪、复投、SLA、安全、补偿和舆情信号；JPA、Flyway 与 MySQL 保存任务、人工结论和审计轨迹。

生产接入真实投诉前应完成个人信息授权、字段脱敏、访问审计和数据保留策略，并保留客户申诉、人工改级与重大事项审批通道。

版权所有 © 2026 上海如静知华信息科技有限公司。
