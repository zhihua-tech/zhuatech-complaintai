/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.complaintai.workbench;

import static cn.zhuatech.complaintai.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "windowDays", "complaints");
    int window = integer(r, "windowDays", 1, 365);
    var complaints = new ArrayList<>(rows(r, "complaints", 1, 500));
    Set<String> ids = new HashSet<>();
    for (var c : complaints) {
      keys(c, "id", "customer", "issue", "date");
      unique(ids, id(c, "id"));
      id(c, "customer");
      id(c, "issue");
      date(text(c, "date", 10));
    }
    complaints.sort(
        Comparator.<Map<String, Object>, String>comparing(c -> c.get("date").toString())
            .thenComparing(c -> c.get("id").toString()));
    Map<List<String>, Map<String, Object>> active = new HashMap<>();
    List<Map<String, Object>> groups = new ArrayList<>();
    for (var c : complaints) {
      var key = List.of(c.get("customer").toString(), c.get("issue").toString());
      var at = date(c.get("date").toString());
      var g = active.get(key);
      if (g == null
          || java.time.temporal.ChronoUnit.DAYS.between(date(g.get("firstDate").toString()), at)
              > window) {
        g = new LinkedHashMap<>();
        g.put("customer", key.get(0));
        g.put("issue", key.get(1));
        g.put("firstDate", at.toString());
        g.put("ids", new ArrayList<String>());
        groups.add(g);
        active.put(key, g);
      }
      @SuppressWarnings("unchecked")
      var members = (List<String>) g.get("ids");
      members.add(c.get("id").toString());
      g.put("count", members.size());
      g.put("recurrent", members.size() > 1);
      g.put("lastDate", at.toString());
    }
    return Map.of(
        "groups",
        groups,
        "recurrentGroups",
        groups.stream().filter(g -> (Boolean) g.get("recurrent")).count(),
        "automaticEscalation",
        false);
  }
}
