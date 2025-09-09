package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.PaymentRequest;
import au.edu.rmit.sept.webapp.services.SandboxPaymentGateway;
import au.edu.rmit.sept.webapp.services.SandboxPaymentGateway.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Year;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class PaymentController {

    private final SandboxPaymentGateway gateway;

    public PaymentController(SandboxPaymentGateway gateway) {
        this.gateway = gateway;
    }

    // create empty payment model and next 12 years when you visit page
    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("payment", new PaymentRequest());

        // see nextYears helper below
        model.addAttribute("years", nextYears(12));
        return "payment/checkout";
    }

    // handles form submission validation check
    @PostMapping("/pay")
    public String pay(@Valid @ModelAttribute("payment") PaymentRequest req,
                      BindingResult errors,
                      Model model) {
        model.addAttribute("years", nextYears(12));

        if (errors.hasErrors()) {
            return "payment/checkout";
        }

        Result r = gateway.charge(req);
        if (r.success()) {
            return "redirect:/checkout?success=1&ref=" + r.reference();
        } else {
            return "redirect:/checkout?failed=1&reason=" + r.message();
        }
    }

    // helper that gets the next 12 years (e.g. 2025, 2026, 2027...2036) for the initial empty page
    private List<Integer> nextYears(int count) {
        int start = Year.now().getValue();
        return IntStream.range(0, count).map(i -> start + i).boxed().toList();
    }
}
