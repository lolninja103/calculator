package Root.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class HistoryDto {

    private int id;
    private BigDecimal sumn;
    private Currency currency;
    private Date date;
private  String cardDeposit;
private String getCardTakeOff;

    public int getId() {
        return id;
    }

    public BigDecimal getSumn() {
        return sumn;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Date getDate() {
        return date;
    }

    public String getCardDeposit() {
        return cardDeposit;
    }

    public String getGetCardTakeOff() {
        return getCardTakeOff;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSumn(BigDecimal sumn) {
        this.sumn = sumn;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCardDeposit(String cardDeposit) {
        this.cardDeposit = cardDeposit;
    }

    public void setGetCardTakeOff(String getCardTakeOff) {
        this.getCardTakeOff = getCardTakeOff;
    }

    public HistoryDto(int id, BigDecimal sumn, Currency currency, Date date, String cardDeposit, String getCardTakeOff) {
        this.id = id;
        this.sumn = sumn;
        this.currency = currency;
        this.date = date;
        this.cardDeposit = cardDeposit;
        this.getCardTakeOff = getCardTakeOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryDto that = (HistoryDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public HistoryDto() {

    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "id=" + id +
                ", sumn=" + sumn +
                ", currency=" + currency +
                ", date=" + date +
                ", cardDeposit='" + cardDeposit + '\'' +
                ", getCardTakeOff='" + getCardTakeOff + '\'' +
                '}';
    }
}


