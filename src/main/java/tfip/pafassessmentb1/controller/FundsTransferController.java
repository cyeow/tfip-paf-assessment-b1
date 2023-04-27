package tfip.pafassessmentb1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import tfip.pafassessmentb1.model.Account;
import tfip.pafassessmentb1.model.Transfer;
import tfip.pafassessmentb1.service.AccountsService;
import tfip.pafassessmentb1.service.FundsTransferService;

@Controller
public class FundsTransferController {
    
    @Autowired
    private AccountsService accSvc;

    @Autowired
    private FundsTransferService trfSvc;

    private static final String ATTR_ACCOUNTS = "accounts";
    private static final String ATTR_TRANSFER = "transfer";

    @GetMapping(path="/")
    public String goToIndex(Model model) {
        List<Account> accounts = accSvc.getAccountListWithoutBalance();

        model.addAttribute(ATTR_ACCOUNTS, accounts);
        model.addAttribute(ATTR_TRANSFER, new Transfer());
        return "view0";
    }

    @PostMapping(path="/transfer", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String makeTransfer(Model model, @Valid Transfer t, BindingResult binding) {
        // checks
        // c0
        if(!accSvc.isValidAccount(t.getFrom())) {
            binding.addError(new ObjectError("globalError", "\"From\" account does not exist."));
        } else if(!accSvc.hasSufficientBalance(t.getFrom(), t.getAmount())) {
            // c5 (only checks if account is validated)
            binding.addError(new ObjectError("globalError", "Insufficient balance remaining in \"from\" account."));
        }

        if(!accSvc.isValidAccount(t.getTo())) {
            binding.addError(new ObjectError("globalError", "\"To\" account does not exist."));
        }
        // c2
        if(t.getFrom().getAccountId().equals(t.getTo().getAccountId())) {
            binding.addError(new ObjectError("globalError", "Cannot transfer from and to the same account."));
        }
        // reject transfer if there are errors
        if(binding.hasErrors()) {
            model.addAttribute(ATTR_ACCOUNTS, accSvc.getAccountListWithoutBalance());
            return "view0";
        }

        // all checks passed, proceed with fund transfer
        try {
            trfSvc.transferFunds(t);
        } catch (DataAccessException e) {
            binding.addError(new ObjectError("globalError", "Error encountered performing the transfer. Please try again."));
            model.addAttribute(ATTR_ACCOUNTS, accSvc.getAccountListWithoutBalance());
            return "view0";
        }

        model.addAttribute(ATTR_TRANSFER, t);
        return "view1";
    }

}
