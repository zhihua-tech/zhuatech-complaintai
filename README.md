<div align="center">

# ZhuaTech ComplaintAI

[简体中文](README.md) | [English](README.en.md)

### 知华客户投诉智能运营平台 · 社区源码版

**投诉分级、SLA 路由、跨部门处置与回访闭环**

[知华科技官网](https://www.zhuatech.cn/) · 上海如静知华信息科技有限公司

</div>

## 本次升级：客诉退款余额与补偿权限

核对累计现金退款不超原收款，同时把本次退款和优惠补偿纳入输入的审批额度；不调用退款接口或签发优惠券。

新增已认证的结构化录入工作台（`/operations.html`），支持批量核算、逐条结果依据、分级复核和CSV/完整JSON导出。结果由真实后端算法计算，不是预置演示数字。所有数值精确传递，不调用外部模型。

这是**只读核算**：不写库、不审批、不自动执行财务、签署、销毁或设备控制。输入条件满足不是执行授权。[算法、输入边界与接口说明](docs/OPERATIONS-REVIEW.md)。

## 本次功能更新：重复客诉关联与间隔

按客户业务编号及问题类型关联工单，计算窗口内重复次数、首末投诉日期和涉及工单，不依赖情绪分值猜测重复投诉。 登录后从「业务计算工具」使用，支持校验与完整结果导出。详见[算法口径、接口和测试说明](docs/AI_OPERATIONS_UPGRADE_20261023.md)。

> [!IMPORTANT]
> 本工程仅限个人非商业学习、研究和技术交流，不得商用。企业内部使用、生产部署、SaaS、实施交付、收费服务、品牌替换或商业再发行，须事先取得上海如静知华信息科技有限公司书面授权，详见 [LICENSE](LICENSE)。

ComplaintAI 面向客户体验、售后服务和投诉管理团队。它将热线、在线和公开渠道的投诉放入统一队列，用客户情绪、重复投诉、SLA 超时、安全合规、补偿金额和舆情暴露生成可解释的风险分级；重大事项始终由人工确认。

![投诉运营管理端](docs/images/complaintai-admin.png)

## 已实现的处置闭环

- `LOW / MEDIUM / HIGH / CRITICAL` 四级投诉风险
- 标准服务、专家复核、管理层升级三类责任队列
- 15—480 分钟动态响应时限与超时预警
- 投诉任务、客户旅程、SLA 日历、根因分析和管理复核
- 管理驾驶舱与投诉专员响应式 H5 工作台
- JWT、MySQL、Flyway、Docker Compose、JUnit 与 MockMvc

核心接口：`POST /api/ai/complaint/analyze`。社区版采用本地确定性策略，不需要外部模型密钥。

![投诉专员 H5 工作台](docs/images/complaintai-h5.png)

## 本地体验

```bash
cd frontend
npm install
npm run dev:demo
```

访问 `http://localhost:5173`，管理端账号 `planner / Demo@2026`，业务端账号 `operator / Demo@2026`。演示客户、投诉、金额和人员均为虚构数据。

后端包名：`cn.zhuatech.complaintai`。更多资料：[API](docs/api.md) · [架构](docs/architecture.md) · [数据库](docs/database.md) · [部署](deploy/README.md)

## 项目合作

需要投诉管理、客户体验、智能客服、CRM/工单集成、AI 私有化、软件外包或深度开发定制，请联系[知华科技](https://www.zhuatech.cn/)：

| 方案咨询 | 商业授权与定制 |
| --- | --- |
| ![微信咨询一](docs/images/zhuatech-wechat-consulting.png) | ![微信咨询二](docs/images/zhuatech-wechat-consulting-2.png) |

SEO：投诉管理系统、AI 投诉分级、客户体验管理、投诉 SLA、Java Vue 源码、知华科技。
