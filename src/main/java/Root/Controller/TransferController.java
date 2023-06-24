package Root.Controller;

import Root.Model.*;
import Root.Parsing.Pars;
import Root.Repository.CardRepository;
import Root.Repository.HistoryRepository;
import Root.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Id;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TransferController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    HistoryRepository historyRepository;


    @PostMapping("transfer")
    public String number(@RequestParam(name = "number") String number, @RequestParam(name = "id") int id, Model model, @RequestParam(name = "CardFromMoney") String numberFromMoney) {
        List<Card> caard = cardRepository.findAll();
        Card card = cardRepository.findByNumber(number);
        model.addAttribute("Name", card.getClient().getFirstname());
        model.addAttribute("SecondName", card.getClient().getSecondname());
        model.addAttribute("Patronymic", card.getClient().getPatronymic());
        model.addAttribute("Id", id);
        model.addAttribute("CardFromMoney", card.getNumber());
        model.addAttribute("name", "");
        return "Transfer";
    }

    @PostMapping("TransfetMoney")
    public String TransferMoney(@RequestParam(name = "money") String money, @RequestParam(name = "currency") String currency, @RequestParam(name = "id") String id,
                                @RequestParam(name = "CardFromMoney") String card, Model model
    ) throws IOException {

        Card card1 = cardRepository.findById(Integer.parseInt(id)).get();
        Currency currency1 = Currency.valueOf(currency);
        BigDecimal result = new BigDecimal(money);
        if (result.doubleValue() < 0) {
            return "redirect:/personalPage";
        }
        if (currency1 != card1.getCurrency()) {
            result = Pars.change(card1.getCurrency(), currency1, new BigDecimal(money));
        }
        //  BigDecimal result = Pars.change(card1.getCurrency(), currency1, new BigDecimal(money));

        Card card2 = cardRepository.findByNumber(card);
        BigDecimal resultt = result;
        if (currency1 != card2.getCurrency()) {
            resultt = Pars.change(currency1, card2.getCurrency(), result);
        }
        // BigDecimal resultt= Pars.change(currency1,card2.getCurrency(),result);

        card1.setBalance(card1.getBalance().subtract(resultt));
        String error = "insufficient funds";

        if (card1.getBalance().doubleValue() >= 0) {
            cardRepository.save(card1);
            card2.setBalance(card2.getBalance().add(resultt));
            cardRepository.save(card2);
            model.addAttribute("name", "");

        } else {
            model.addAttribute("name", error);
        }
        History history = new History();
        history.setCurrency(currency1);
        history.setSumn(result);
        history.setCardTakeoff(card2);
        history.setDate(new Date());
        history.setCardDeposit(card1);
        historyRepository.save(history);
        return "redirect:/personalPage";
    }



}
