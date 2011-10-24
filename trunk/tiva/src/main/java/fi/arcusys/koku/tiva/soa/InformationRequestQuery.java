package fi.arcusys.koku.tiva.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
public class InformationRequestQuery {
    private int startNum;
    private int maxNum;
    private InformationRequestCriteria criteria;
    
    public InformationRequestQuery() {
    }
    
    public InformationRequestQuery(final int startNum, final int maxNum) {
        this.startNum = startNum;
        this.maxNum = maxNum;
    }
    
    /**
     * @return the criteria
     */
    public InformationRequestCriteria getCriteria() {
        return criteria;
    }
    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(InformationRequestCriteria criteria) {
        this.criteria = criteria;
    }
    /**
     * @return the startNum
     */
    public int getStartNum() {
        return startNum;
    }
    /**
     * @param startNum the startNum to set
     */
    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }
    /**
     * @return the maxNum
     */
    public int getMaxNum() {
        return maxNum;
    }
    /**
     * @param maxNum the maxNum to set
     */
    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }
    
    
}
