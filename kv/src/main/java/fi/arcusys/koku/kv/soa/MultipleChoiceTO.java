package fi.arcusys.koku.kv.soa;

import javax.xml.bind.annotation.XmlType;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about single possible answer to multiple choice question.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
@XmlType (name = "multipleChoice", namespace = "http://soa.kv.koku.arcusys.fi/")
public class MultipleChoiceTO {
    private int questionNumber;
    private int number;
    private String description;
    /**
     * @return the questionNumber
     */
    public int getQuestionNumber() {
        return questionNumber;
    }
    /**
     * @param questionNumber the questionNumber to set
     */
    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }
    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
