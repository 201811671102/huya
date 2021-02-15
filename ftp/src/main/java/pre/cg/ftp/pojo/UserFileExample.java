package pre.cg.ftp.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserFileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserFileExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andFidIsNull() {
            addCriterion("fid is null");
            return (Criteria) this;
        }

        public Criteria andFidIsNotNull() {
            addCriterion("fid is not null");
            return (Criteria) this;
        }

        public Criteria andFidEqualTo(Integer value) {
            addCriterion("fid =", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidNotEqualTo(Integer value) {
            addCriterion("fid <>", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidGreaterThan(Integer value) {
            addCriterion("fid >", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidGreaterThanOrEqualTo(Integer value) {
            addCriterion("fid >=", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidLessThan(Integer value) {
            addCriterion("fid <", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidLessThanOrEqualTo(Integer value) {
            addCriterion("fid <=", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidIn(List<Integer> values) {
            addCriterion("fid in", values, "fid");
            return (Criteria) this;
        }

        public Criteria andFidNotIn(List<Integer> values) {
            addCriterion("fid not in", values, "fid");
            return (Criteria) this;
        }

        public Criteria andFidBetween(Integer value1, Integer value2) {
            addCriterion("fid between", value1, value2, "fid");
            return (Criteria) this;
        }

        public Criteria andFidNotBetween(Integer value1, Integer value2) {
            addCriterion("fid not between", value1, value2, "fid");
            return (Criteria) this;
        }

        public Criteria andFilepathIsNull() {
            addCriterion("filePath is null");
            return (Criteria) this;
        }

        public Criteria andFilepathIsNotNull() {
            addCriterion("filePath is not null");
            return (Criteria) this;
        }

        public Criteria andFilepathEqualTo(String value) {
            addCriterion("filePath =", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathNotEqualTo(String value) {
            addCriterion("filePath <>", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathGreaterThan(String value) {
            addCriterion("filePath >", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathGreaterThanOrEqualTo(String value) {
            addCriterion("filePath >=", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathLessThan(String value) {
            addCriterion("filePath <", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathLessThanOrEqualTo(String value) {
            addCriterion("filePath <=", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathLike(String value) {
            addCriterion("filePath like", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathNotLike(String value) {
            addCriterion("filePath not like", value, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathIn(List<String> values) {
            addCriterion("filePath in", values, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathNotIn(List<String> values) {
            addCriterion("filePath not in", values, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathBetween(String value1, String value2) {
            addCriterion("filePath between", value1, value2, "filepath");
            return (Criteria) this;
        }

        public Criteria andFilepathNotBetween(String value1, String value2) {
            addCriterion("filePath not between", value1, value2, "filepath");
            return (Criteria) this;
        }

        public Criteria andFiletimeIsNull() {
            addCriterion("fileTime is null");
            return (Criteria) this;
        }

        public Criteria andFiletimeIsNotNull() {
            addCriterion("fileTime is not null");
            return (Criteria) this;
        }

        public Criteria andFiletimeEqualTo(Date value) {
            addCriterion("fileTime =", value, "filetime");
            return (Criteria) this;
        }

        public Criteria andFiletimeNotEqualTo(Date value) {
            addCriterion("fileTime <>", value, "filetime");
            return (Criteria) this;
        }

        public Criteria andFiletimeGreaterThan(Date value) {
            addCriterion("fileTime >", value, "filetime");
            return (Criteria) this;
        }

        public Criteria andFiletimeGreaterThanOrEqualTo(Date value) {
            addCriterion("fileTime >=", value, "filetime");
            return (Criteria) this;
        }

        public Criteria andFiletimeLessThan(Date value) {
            addCriterion("fileTime <", value, "filetime");
            return (Criteria) this;
        }

        public Criteria andFiletimeLessThanOrEqualTo(Date value) {
            addCriterion("fileTime <=", value, "filetime");
            return (Criteria) this;
        }

        public Criteria andFiletimeIn(List<Date> values) {
            addCriterion("fileTime in", values, "filetime");
            return (Criteria) this;
        }

        public Criteria andFiletimeNotIn(List<Date> values) {
            addCriterion("fileTime not in", values, "filetime");
            return (Criteria) this;
        }

        public Criteria andFiletimeBetween(Date value1, Date value2) {
            addCriterion("fileTime between", value1, value2, "filetime");
            return (Criteria) this;
        }

        public Criteria andFiletimeNotBetween(Date value1, Date value2) {
            addCriterion("fileTime not between", value1, value2, "filetime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}