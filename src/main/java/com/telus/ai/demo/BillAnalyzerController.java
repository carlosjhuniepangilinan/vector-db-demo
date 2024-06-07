package com.telus.ai.demo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.telus.ai.demo.adapter.LiteLLMProxyAdapter;

@RestController
public class BillAnalyzerController {
    
    private final LiteLLMProxyAdapter litellm;
    private final Gson gson;

    public BillAnalyzerController(LiteLLMProxyAdapter litellm
            , Gson gson
            ) {
        this.litellm = litellm;
        this.gson = gson;
    }
    
    @GetMapping("/isBillHigherInd/{accountNum}")
    Map<String, String> isBillHigher(@PathVariable String accountNum) {
    //String isBillHigher() {
        String bills = """
                Account Number: 600101213
                Mar 10, 2024
                Account summary Go to telus.com/mytelus for a detailed breakdown of

your monthly charges

Balance forward from your last bill .............................................. $0.00

TELUS Rewards

New charges
view your current balance and redeem, visit Home Phone $0.00 telus.com/rewards
Internet $139.68
TELUS TV $118.10
Other charges and credits $0.00
GST / HST $12.88
Total new charges .................................................................. $270.66
Total due.......................................................$270.66

                Account Number: 600101213
                Apr 10, 2024
                Account summary
Balance forward from your last bill .............................................. $0.00
New charges
Home Phone $0.00
Internet $120.00
GST / HST $6.00
Total new charges .................................................................. $126.00
Total due.......................................................$126.00
                """;

        List<String> billDates = new ArrayList<>();
        billDates.add("Mar 2024");
        billDates.add("Apr 2024");

        String aiResponse = litellm.isBillHigher(accountNum, bills, billDates);
        //return aiResponse;

        Type type = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> response = gson.fromJson(aiResponse, type);
        return response;
    }
}
