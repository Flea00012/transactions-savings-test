package com.qapital.savings.rule;

import com.qapital.savings.event.SavingsEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        log.info("get request to activeRulesForUser");
        return savingsRulesService.activeRulesForUser(userId);
    }

    @PostMapping("/events")
    public List<SavingsEvent> retrieveEvents(@RequestBody SavingsRule savingsRule){
        log.info("retrieving all savings events from endpoint '/events'");
        return savingsRulesService.executeRule(savingsRule);
    }

}
