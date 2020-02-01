package lllr.test.breast.dao.mapperUtil;

import java.util.ArrayList;
import java.util.List;

public class AutoAnswerTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AutoAnswerTemplateExample() {
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

        public Criteria andConsultIdIsNull() {
            addCriterion("consult_id is null");
            return (Criteria) this;
        }

        public Criteria andConsultIdIsNotNull() {
            addCriterion("consult_id is not null");
            return (Criteria) this;
        }

        public Criteria andConsultIdEqualTo(Integer value) {
            addCriterion("consult_id =", value, "consultId");
            return (Criteria) this;
        }

        public Criteria andConsultIdNotEqualTo(Integer value) {
            addCriterion("consult_id <>", value, "consultId");
            return (Criteria) this;
        }

        public Criteria andConsultIdGreaterThan(Integer value) {
            addCriterion("consult_id >", value, "consultId");
            return (Criteria) this;
        }

        public Criteria andConsultIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("consult_id >=", value, "consultId");
            return (Criteria) this;
        }

        public Criteria andConsultIdLessThan(Integer value) {
            addCriterion("consult_id <", value, "consultId");
            return (Criteria) this;
        }

        public Criteria andConsultIdLessThanOrEqualTo(Integer value) {
            addCriterion("consult_id <=", value, "consultId");
            return (Criteria) this;
        }

        public Criteria andConsultIdIn(List<Integer> values) {
            addCriterion("consult_id in", values, "consultId");
            return (Criteria) this;
        }

        public Criteria andConsultIdNotIn(List<Integer> values) {
            addCriterion("consult_id not in", values, "consultId");
            return (Criteria) this;
        }

        public Criteria andConsultIdBetween(Integer value1, Integer value2) {
            addCriterion("consult_id between", value1, value2, "consultId");
            return (Criteria) this;
        }

        public Criteria andConsultIdNotBetween(Integer value1, Integer value2) {
            addCriterion("consult_id not between", value1, value2, "consultId");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyIsNull() {
            addCriterion("question_key is null");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyIsNotNull() {
            addCriterion("question_key is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyEqualTo(String value) {
            addCriterion("question_key =", value, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyNotEqualTo(String value) {
            addCriterion("question_key <>", value, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyGreaterThan(String value) {
            addCriterion("question_key >", value, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyGreaterThanOrEqualTo(String value) {
            addCriterion("question_key >=", value, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyLessThan(String value) {
            addCriterion("question_key <", value, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyLessThanOrEqualTo(String value) {
            addCriterion("question_key <=", value, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyLike(String value) {
            addCriterion("question_key like", value, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyNotLike(String value) {
            addCriterion("question_key not like", value, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyIn(List<String> values) {
            addCriterion("question_key in", values, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyNotIn(List<String> values) {
            addCriterion("question_key not in", values, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyBetween(String value1, String value2) {
            addCriterion("question_key between", value1, value2, "questionKey");
            return (Criteria) this;
        }

        public Criteria andQuestionKeyNotBetween(String value1, String value2) {
            addCriterion("question_key not between", value1, value2, "questionKey");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateIsNull() {
            addCriterion("answer_template is null");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateIsNotNull() {
            addCriterion("answer_template is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateEqualTo(String value) {
            addCriterion("answer_template =", value, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateNotEqualTo(String value) {
            addCriterion("answer_template <>", value, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateGreaterThan(String value) {
            addCriterion("answer_template >", value, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateGreaterThanOrEqualTo(String value) {
            addCriterion("answer_template >=", value, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateLessThan(String value) {
            addCriterion("answer_template <", value, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateLessThanOrEqualTo(String value) {
            addCriterion("answer_template <=", value, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateLike(String value) {
            addCriterion("answer_template like", value, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateNotLike(String value) {
            addCriterion("answer_template not like", value, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateIn(List<String> values) {
            addCriterion("answer_template in", values, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateNotIn(List<String> values) {
            addCriterion("answer_template not in", values, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateBetween(String value1, String value2) {
            addCriterion("answer_template between", value1, value2, "answerTemplate");
            return (Criteria) this;
        }

        public Criteria andAnswerTemplateNotBetween(String value1, String value2) {
            addCriterion("answer_template not between", value1, value2, "answerTemplate");
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