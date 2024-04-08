package hr.meske.javaWeb.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import hr.meske.javaWeb.services.CartService;
import hr.meske.javaWeb.services.PayPalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/payment")
public class PayPalController {

    private final PayPalService payPalService;
    private final CartService cartService;

    public PayPalController(PayPalService payPalService, CartService cartService) {
        this.payPalService = payPalService;
        this.cartService = cartService;
    }


    @GetMapping("/create")
    public RedirectView createPayment() {

        try{
            String cancelUrl="http://localhost:8080/payment/cancel";
            String successUrl="http://localhost:8080/payment/success";
            Payment payment = payPalService.createPayment(
                    cartService.getTotalPriceOfItemsInCart(),
                    "USD",
                    "paypal",
                    "sale",
                    cartService.getProductNamesInCsv(),
                    cancelUrl,
                    successUrl);

            for(Links links: payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return new RedirectView(links.getHref());
                }
            }

        } catch (PayPalRESTException e) {

        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/success")
    public String PaymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ){
        try {
            cartService.commitTransaction("paypal");
            Payment payment = payPalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if(payment.getState().equals("approved")){
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/payment/error";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel(){
        return "paymentCancel";
    }


    @GetMapping("/payment/error")
    public String paymentError(){
        return "paymentError";
    }
}
