package fi.arcusys.koku.tiva.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 16, 2011
 */
public class AuthorizationQuery {
    private int startNum;
    private int maxNum;
    
    private AuthorizationCriteria criteria;

    public AuthorizationQuery() {
    }
    
    public AuthorizationQuery(int startNum, int maxNum) {
        this.startNum = startNum;
        this.maxNum = maxNum;
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

    /**
     * @return the criteria
     */
    public AuthorizationCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(AuthorizationCriteria criteria) {
        this.criteria = criteria;
    }
}
