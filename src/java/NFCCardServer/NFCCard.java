/*
 *JavaBean Class that represents a NFC Card 
 */
package NFCCardServer;

/**
 *
 * @author Trisha Tan
 */
public final class NFCCard {
    
    private String cardName; 
    private String cardNumber;
    
    //Default Constructor
    public NFCCard()
    {
    }
    
    //COnstructor to initialize instance variables
    public NFCCard(String cardName, String cardNumber)
    {
        setCardName(cardName);
        setCardNumber(cardNumber); 
        //Add type attribute when possible 
    }
    
    //Get & Sets
    public String getCardName() {
        return cardName; 
    }
    
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    
    public String getCardNumber() {
        return cardNumber; 
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getXMLString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<NFCCard>");
        buffer.append("<cardName>").append(getCardName()).append("</cardName>");
        buffer.append("<cardNumber>").append(getCardNumber()).append("</cardNumber>");
        buffer.append("</NFCCard>");
        return buffer.toString();
    }   
}
