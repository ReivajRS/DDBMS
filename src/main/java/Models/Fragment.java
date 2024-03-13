package Models;

public class Fragment {
    private Integer idFragment;
    private String distributedTable, DBMS, DB, URI, accessUser, accessPassword, criteria, criteriaValue, attributesString;
    private String[] attributes;
    private Boolean active;

    public Fragment(Integer idFragment, String distributedTable, String DBMS, String DB, String URI, String accessUser,
                    String accessPassword, String criteria, String criteriaValue, String attributesString) {
        this.idFragment = idFragment;
        this.distributedTable = distributedTable;
        this.DBMS = DBMS;
        this.DB = DB;
        this.URI = URI;
        this.accessUser = accessUser;
        this.accessPassword = accessPassword;
        this.criteria = criteria;
        this.criteriaValue = criteriaValue;
        this.attributesString = attributesString;
        this.attributes = attributesString.split(",");
    }

    public Integer getIdFragment() {
        return idFragment;
    }

    public String getDistributedTable() {
        return distributedTable;
    }

    public String getDBMS() {
        return DBMS;
    }

    public String getDB() {
        return DB;
    }

    public String getURI() {
        return URI;
    }

    public String getAccessUser() {
        return accessUser;
    }

    public String getAccessPassword() {
        return accessPassword;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getCriteriaValue() {
        return criteriaValue;
    }

    public String getAttributesString() {
        return attributesString;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
