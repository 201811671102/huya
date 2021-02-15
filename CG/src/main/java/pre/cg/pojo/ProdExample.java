package pre.cg.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProdExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProdExample() {
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

        public Criteria andProdIdIsNull() {
            addCriterion("prod_id is null");
            return (Criteria) this;
        }

        public Criteria andProdIdIsNotNull() {
            addCriterion("prod_id is not null");
            return (Criteria) this;
        }

        public Criteria andProdIdEqualTo(Integer value) {
            addCriterion("prod_id =", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdNotEqualTo(Integer value) {
            addCriterion("prod_id <>", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdGreaterThan(Integer value) {
            addCriterion("prod_id >", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("prod_id >=", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdLessThan(Integer value) {
            addCriterion("prod_id <", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdLessThanOrEqualTo(Integer value) {
            addCriterion("prod_id <=", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdIn(List<Integer> values) {
            addCriterion("prod_id in", values, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdNotIn(List<Integer> values) {
            addCriterion("prod_id not in", values, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdBetween(Integer value1, Integer value2) {
            addCriterion("prod_id between", value1, value2, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdNotBetween(Integer value1, Integer value2) {
            addCriterion("prod_id not between", value1, value2, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdPnameIsNull() {
            addCriterion("prod_pname is null");
            return (Criteria) this;
        }

        public Criteria andProdPnameIsNotNull() {
            addCriterion("prod_pname is not null");
            return (Criteria) this;
        }

        public Criteria andProdPnameEqualTo(String value) {
            addCriterion("prod_pname =", value, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameNotEqualTo(String value) {
            addCriterion("prod_pname <>", value, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameGreaterThan(String value) {
            addCriterion("prod_pname >", value, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameGreaterThanOrEqualTo(String value) {
            addCriterion("prod_pname >=", value, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameLessThan(String value) {
            addCriterion("prod_pname <", value, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameLessThanOrEqualTo(String value) {
            addCriterion("prod_pname <=", value, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameLike(String value) {
            addCriterion("prod_pname like", value, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameNotLike(String value) {
            addCriterion("prod_pname not like", value, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameIn(List<String> values) {
            addCriterion("prod_pname in", values, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameNotIn(List<String> values) {
            addCriterion("prod_pname not in", values, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameBetween(String value1, String value2) {
            addCriterion("prod_pname between", value1, value2, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPnameNotBetween(String value1, String value2) {
            addCriterion("prod_pname not between", value1, value2, "prodPname");
            return (Criteria) this;
        }

        public Criteria andProdPriceIsNull() {
            addCriterion("prod_price is null");
            return (Criteria) this;
        }

        public Criteria andProdPriceIsNotNull() {
            addCriterion("prod_price is not null");
            return (Criteria) this;
        }

        public Criteria andProdPriceEqualTo(Double value) {
            addCriterion("prod_price =", value, "prodPrice");
            return (Criteria) this;
        }

        public Criteria andProdPriceNotEqualTo(Double value) {
            addCriterion("prod_price <>", value, "prodPrice");
            return (Criteria) this;
        }

        public Criteria andProdPriceGreaterThan(Double value) {
            addCriterion("prod_price >", value, "prodPrice");
            return (Criteria) this;
        }

        public Criteria andProdPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("prod_price >=", value, "prodPrice");
            return (Criteria) this;
        }

        public Criteria andProdPriceLessThan(Double value) {
            addCriterion("prod_price <", value, "prodPrice");
            return (Criteria) this;
        }

        public Criteria andProdPriceLessThanOrEqualTo(Double value) {
            addCriterion("prod_price <=", value, "prodPrice");
            return (Criteria) this;
        }

        public Criteria andProdPriceIn(List<Double> values) {
            addCriterion("prod_price in", values, "prodPrice");
            return (Criteria) this;
        }

        public Criteria andProdPriceNotIn(List<Double> values) {
            addCriterion("prod_price not in", values, "prodPrice");
            return (Criteria) this;
        }

        public Criteria andProdPriceBetween(Double value1, Double value2) {
            addCriterion("prod_price between", value1, value2, "prodPrice");
            return (Criteria) this;
        }

        public Criteria andProdPriceNotBetween(Double value1, Double value2) {
            addCriterion("prod_price not between", value1, value2, "prodPrice");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionIsNull() {
            addCriterion("prod_description is null");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionIsNotNull() {
            addCriterion("prod_description is not null");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionEqualTo(String value) {
            addCriterion("prod_description =", value, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionNotEqualTo(String value) {
            addCriterion("prod_description <>", value, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionGreaterThan(String value) {
            addCriterion("prod_description >", value, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("prod_description >=", value, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionLessThan(String value) {
            addCriterion("prod_description <", value, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionLessThanOrEqualTo(String value) {
            addCriterion("prod_description <=", value, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionLike(String value) {
            addCriterion("prod_description like", value, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionNotLike(String value) {
            addCriterion("prod_description not like", value, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionIn(List<String> values) {
            addCriterion("prod_description in", values, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionNotIn(List<String> values) {
            addCriterion("prod_description not in", values, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionBetween(String value1, String value2) {
            addCriterion("prod_description between", value1, value2, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andProdDescriptionNotBetween(String value1, String value2) {
            addCriterion("prod_description not between", value1, value2, "prodDescription");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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