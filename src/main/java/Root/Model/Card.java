package Root.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Card {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String number;
private String CVV;
private Date date;
private BigDecimal balance;
@Enumerated(EnumType.STRING)
private Currency currency;
@ManyToOne
private Client client;

    public int getId() {
        return id;
    }
    public BigDecimal getBalance(){
        return balance;
}
    public void setId(int id) {
        this.id = id;
    }
public void setBalance(BigDecimal balance){
   this.balance=balance;
}
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
public void setCurrency(Currency currency){
        this.currency=currency;
}
public Currency getCurrency (){
   return  currency;
}
public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String toString() {
        return id+""+number+""+CVV+""+date+""+client+""+balance+""+currency;

    }
}
