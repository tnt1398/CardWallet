/*
 * @Singleton EJB that holds the list of NFC Cards 
 */
package NFCCardServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 *
 * @author Trisha Tan 
 */

@Singleton
public class CardBean {
    
    private List<NFCCard> cards; 
    
    @PostConstruct 
    public void initialBookingColelction()
    {
        cards = new ArrayList<>();
        addCard("AUT", "1111");
        addCard("Testing", "2222");
    }
    
    public void addCard(String cardName, String cardNumber)
    {
        cards.add(new NFCCard(cardName, cardNumber)); 
    }
    
    public boolean removeCard (String cardName, String cardNumber)
    {
        for (NFCCard card : cards)
        {
            if(card.getCardName().equals(cardName) 
                && card.getCardNumber().equals(cardNumber))
            {
                cards.remove(card);
                return true;
            }
        }
        return false; 
    }
    
    public Collection<NFCCard> getCards()
    {
        return cards;
    }
}
