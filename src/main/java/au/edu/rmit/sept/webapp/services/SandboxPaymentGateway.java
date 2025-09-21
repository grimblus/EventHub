package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.PaymentRequest;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

// NOT using payment provider api, this is for simple UI testing

@Service
public class SandboxPaymentGateway {
    // 4242 4242 4242 4242 = success
    // 4000 0000 0000 0002 = decline
    // anything else with 16 digits = success

    public SandboxPaymentGateway() {

    }

    public Result charge(PaymentRequest req) {
        String digits = req.getCardNumber().replaceAll("\\D", "");
        if (digits.length() < 12 || digits.length() > 19) {
            return Result.fail("Invalid card number length");
        }
        if (!isExpiryValid(req.getExpMonth(), req.getExpYear())) {
            return Result.fail("Card expired");
        }
        if ("4000000000000002".equals(digits)) {
            return Result.fail("Card was declined");
        }
        if (digits.length() == 16/* || "4242424242424242".equals(digits)*/) {
            String ref = "REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
            return Result.ok(ref);
        }

        // REMOVE LATER: for easy testing
        return Result.fail("Unsupported test number (try 4242 4242 4242 4242)");
    }

    private boolean isExpiryValid(int month, int year) {
        java.time.YearMonth now = java.time.YearMonth.now();
        java.time.YearMonth exp = java.time.YearMonth.of(year, Math.max(1, Math.min(12, month)));
        return !exp.isBefore(now);
    }

    public record Result(boolean success, String reference, String message) {
        public static Result ok(String ref) { return new Result(true, ref, null); }
        public static Result fail(String msg) { return new Result(false, null, msg); }
    }
}
