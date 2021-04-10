package com.qapital.savings.rule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/savings/rule")
public class SavingsRulesController {

    private final SavingsRulesService savingsRulesService;

    public SavingsRulesController(SavingsRulesService savingsRulesService) {
        this.savingsRulesService = savingsRulesService;
    }

    @GetMapping("/active/{userId}")
    public List<SavingsRule> activeRulesForUser(@PathVariable Long userId) {
        log.debug("Inside activeRulesForUser");
        return savingsRulesService.activeRulesForUser(userId);
    }


}
