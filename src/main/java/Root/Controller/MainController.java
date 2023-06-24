package Root.Controller;


import Root.Model.*;
import Root.Repository.CardRepository;
import Root.Repository.HistoryRepository;
import Root.Repository.UserRepository;
import Root.Util.Code;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller


public class MainController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    HistoryRepository historyRepository;

    @GetMapping("/registration")
    public String Reg(Model model) {
        String error = "error";

        return "singup";
    }

    @PostMapping("/registration")
    public String take(@RequestParam(name = "firstname") String firstname, @RequestParam(name = "secondname")
            String secondname, @RequestParam(name = "patronymic") String patronymic, @RequestParam(name = "username")
                               String username, @RequestParam(name = "password") String password, @RequestParam(name = "passwordAgain") String passwordAgain,
                       Model model) {
        Client client = new Client();

        if (userRepository.existsByUsername(username)) {
            model.addAttribute("errorMessage", "choose an other username");
            return "singup";
        }
        if (password.equals(passwordAgain)) {
            client.setPassword(password);
            client.setFirstname(firstname);

            client.setPatronymic(patronymic);
            client.setSecondname(secondname);
            client.setUsername(username);
            client.setActive(true);
            List<Roles> roles = new ArrayList<>();
            roles.add(Roles.USER);
            client.setRoles(roles);
            userRepository.save(client);
            model.addAttribute("errorMessage", "your registration is successful");
            return "singup";
        } else {
            model.addAttribute("errorMessage", "passwords are not same");
            return "singup";
        }


    }

    @GetMapping("addCard")
    public String cart(@AuthenticationPrincipal Client client, Model model) {
        model.addAttribute("Client", client.getFirstname() + " " + client.getSecondname());
        model.addAttribute("Number", Code.function());
        model.addAttribute("Date", Code.date());

        return "card";

    }

    @PostMapping("/saveCard")

    //@AuthenticationPrincipal
    public String save(@RequestParam(name = "numberCard") String number, @AuthenticationPrincipal Client client, @RequestParam(name = "currency") String currency) {
        Card card = new Card();
        card.setCVV(Code.generationCVV());
        card.setNumber(number);
        card.setDate(Code.datas());
        card.setClient(client);
        Currency currency1 = Currency.valueOf(currency);
        card.setCurrency(currency1);
        card.setBalance(new BigDecimal(0));
        cardRepository.save(card);
        // cardRepository.save();


        return "card";

    }


    @GetMapping("personalPage")
    public String cards(@AuthenticationPrincipal Client client, Model model) {
        List<Card> cards = cardRepository.findByClient(client);
        model.addAttribute("cards", cards);
        return "allCards";
    }

    @GetMapping("/dashboard/myEntries/{id}")
    public String topUp(@PathVariable("id") String id, @AuthenticationPrincipal Client client, Model model) {
        Integer a = Integer.parseInt(id);

        Card card = cardRepository.findById(a).get();
        model.addAttribute("balance", card.getBalance());
        model.addAttribute("Number", card.getNumber());
        model.addAttribute("currency", card.getCurrency());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        String f = sdf.format((card.getDate()));
        model.addAttribute("date", f);
        model.addAttribute("CVV", card.getCVV());
        model.addAttribute("Client", client.getFirstname() + " " + client.getSecondname());
        model.addAttribute("Currency", card.getCurrency());
        model.addAttribute("Balance", card.getBalance());
        model.addAttribute("idCard", card.getId());

        List<History> historyOfCards = readyCards(card);
        List<HistoryDto> history = convert(historyOfCards);
        HashSet<HistoryDto> hiistory = new HashSet<>(history);

        model.addAttribute("histories", hiistory);
        return "topUp";
    }

    public List<HistoryDto> convert(List<History> cards) {
        List<HistoryDto> historyDto = new ArrayList<>();
        for (int i = 0; i < cards.size(); i = i + 1) {
            HistoryDto historyDto1 = new HistoryDto();
            historyDto1.setId(cards.get(i).getId());
            historyDto1.setSumn(cards.get(i).getSumn());
            historyDto1.setCardDeposit(cards.get(i).getCardDeposit().getNumber());
            historyDto1.setGetCardTakeOff(cards.get(i).getCardTakeoff().getNumber());
            historyDto1.setCurrency(cards.get(i).getCurrency());
            historyDto1.setDate(cards.get(i).getDate());
            historyDto.add(historyDto1);

        }
        return historyDto;
    }

    @GetMapping("/time")
    public String string() {
        return "onTime";

    }

    @PostMapping("/putMoney")
    public String number(@RequestParam(name = "sumn") String number,
                         @RequestParam(name = "id") String id
    ) {

        int h = Integer.parseInt(id);
        Card card = cardRepository.findById(h).get();
        BigDecimal bigDecimal = card.getBalance();
        BigDecimal n = new BigDecimal(number);
        if (n.doubleValue() < 0) {
            return "redirect:/personalPage";
        }
        card.setBalance(bigDecimal.add(n));
        cardRepository.save(card);
        System.out.println(number);
        return "redirect:/dashboard/myEntries/" + id;

    }

    @GetMapping("")
    public String balancce() {

        return "topUp";
    }

    @GetMapping("transfer")
    public static String transfer(Model model) {
        model.addAttribute("name", "");
        return "Transfer";
    }

    public List<History> readyCards(Card card) {
        List<History> historyRepositoryByCardTakeoff = historyRepository.findByCardTakeoff(card);
        List<History> historyRepositoryByCardDeposit = historyRepository.findByCardDeposit(card);
        List<History> result = new ArrayList<>();
        result.addAll(historyRepositoryByCardDeposit);
        result.addAll(historyRepositoryByCardTakeoff);
        return result;
    }

    @GetMapping("new")
    public String nngw() {
        return "New";
    }

    @PostMapping("filter")
    public String filters(@RequestParam(name = "AmountFrom", required = false) String amoutFrom, @RequestParam(name = "AmountUpTo", required = false) String amountUpTo,
                          @RequestParam(name = "Currency", required = false) String currency, Model model, @RequestParam(name = "cardNumber") String numberCard) {
        System.out.println(amountUpTo);
        System.out.println(amoutFrom);
        System.out.println(currency);
        System.out.println(numberCard);
        Card card = cardRepository.findByNumber(numberCard);

        List<History> historyOfCards = readyCards(card);

        historyOfCards = Amoumt(amoutFrom, amountUpTo, historyOfCards, currency);
        List<HistoryDto> history = convert(historyOfCards);
        HashSet<HistoryDto> hiistory = new HashSet<>(history);
        model.addAttribute("balance", card.getBalance());
        model.addAttribute("Number", card.getNumber());
        model.addAttribute("currency", card.getCurrency());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        String f = sdf.format((card.getDate()));
        model.addAttribute("date", f);
        model.addAttribute("CVV", card.getCVV());
        model.addAttribute("Client", card.getClient().getFirstname() + " " + card.getClient().getSecondname());
        model.addAttribute("Currency", card.getCurrency());
        model.addAttribute("Balance", card.getBalance());
        model.addAttribute("idCard", card.getId());
        model.addAttribute("histories", hiistory);
        return "topUp";

    }

    public List<History> Amoumt(String AmountFrom, String AmountUpTo, List<History> histories, String currency) {
        List<History> finall = new ArrayList<>();

        if (!AmountFrom.equals("") && !AmountUpTo.equals("") && currency != null) {

            Currency currency1 = Currency.valueOf(currency);
            BigDecimal bigDecimal1 = new BigDecimal(AmountFrom);
            BigDecimal bigDecimal2 = new BigDecimal(AmountUpTo);


            for (int i = 0; i < histories.size(); i = i + 1) {
                if (histories.get(i).getSumn().doubleValue() >= bigDecimal1.doubleValue() && histories.get(i).getSumn().doubleValue() <= bigDecimal2.doubleValue() &&
                        histories.get(i).getCurrency() == currency1) {
                    finall.add(histories.get(i));
                }

            }

        } else if (currency != null && AmountFrom.equals("") && !AmountUpTo.equals("")) {
            Currency currency1 = Currency.valueOf(currency);
            BigDecimal bigDecimal2 = new BigDecimal(AmountUpTo);
            for (int i = 0; i < histories.size(); i = i + 1) {
                if (histories.get(i).getSumn().doubleValue() <= bigDecimal2.doubleValue() && histories.get(i).getCurrency() == currency1) {
                    finall.add(histories.get(i));
                }

            }

        } else if (AmountUpTo.equals("") && currency != null && !AmountFrom.equals("")) {
            Currency currency1 = Currency.valueOf(currency);
            BigDecimal bigDecimal = new BigDecimal(AmountFrom);
            for (int i = 0; i < histories.size(); i = i + 1) {
                if (histories.get(i).getSumn().doubleValue() >= bigDecimal.doubleValue() && histories.get(i).getCurrency() == currency1) {
                    finall.add(histories.get(i));
                }
            }

        } else if (currency == null && !AmountFrom.equals("") && !AmountUpTo.equals("")) {

            BigDecimal bigDecimal = new BigDecimal(AmountFrom);
            BigDecimal bigDecimal1 = new BigDecimal(AmountUpTo);
            for (int i = 0; i < histories.size(); i = i + 1) {
                if (histories.get(i).getSumn().doubleValue() >= bigDecimal.doubleValue() && histories.get(i).getSumn().doubleValue() <= bigDecimal1.doubleValue()) {
                    finall.add(histories.get(i));
                }
            }
        }
       else if (currency!=null&&AmountFrom.equals("")&& AmountUpTo.equals("")){
            Currency currency1 = Currency.valueOf(currency);
       for (int i=0;i<histories.size();i=i+1){
           if (histories.get(i).getCurrency()==currency1){
               finall.add(histories.get(i));
           }
       }

       }
       else if (currency==null&&!AmountFrom.equals("")&&AmountUpTo.equals("")){
            BigDecimal bigDecimal = new BigDecimal(AmountFrom);

            for (int i=0;i<histories.size();i=i+1){
                if (histories.get(i).getSumn().doubleValue()>=bigDecimal.doubleValue()){
                finall.add(histories.get(i));}
            }
        }
else if (currency==null&&AmountFrom.equals("")&&!AmountUpTo.equals("")){
    BigDecimal bigDecimal=new BigDecimal(AmountUpTo);
    for (int i=0;i<histories.size();i=i+1){
        if (histories.get(i).getSumn().doubleValue()<=bigDecimal.doubleValue()){
            finall.add(histories.get(i));
        }
    }
        }
else if (currency==null&&AmountFrom.equals("")&&AmountUpTo.equals("")){
for (int i=0;i<histories.size();i=i+1){
    finall.add(histories.get(i));
}
        }
return finall;
    }
}
