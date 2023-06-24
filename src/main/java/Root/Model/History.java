package Root.Model;

import javax.persistence.*;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Card cardDeposit;
    private BigDecimal sumn;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private Date date;
    @ManyToOne
    private Card cardTakeoff;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getSumn() {
        return sumn;
    }

    public void setSumn(BigDecimal sumn) {
        this.sumn = sumn;
    }

    public Card getCardTakeoff() {
        return cardTakeoff;
    }

    public void setCardTakeoff(Card card) {
        this.cardTakeoff = card;
    }

    public Currency getCurrency() {
        return currency;


    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Card getCardDeposit(){
    return cardDeposit;
    }
    public void setCardDeposit(Card cardDeposit){
        this.cardDeposit=cardDeposit;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public History(Card cardDeposit, BigDecimal sumn, Currency currency, Date date, Card cardTakeoff) {
        this.cardTakeoff = cardTakeoff;
        this.date = date;
        this.currency = currency;
        this.sumn = sumn;
        this.cardDeposit = cardDeposit;

    }

    public History() {

    }
}
