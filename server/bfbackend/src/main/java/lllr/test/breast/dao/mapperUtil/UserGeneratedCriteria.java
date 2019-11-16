package lllr.test.breast.dao.mapperUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

//条件查询的抽象类
public abstract class UserGeneratedCriteria {
    protected List<Criterion> criteria;

    protected UserGeneratedCriteria() {
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

    //添加条件字段
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

    protected void addCriterionForJDBCDate(String condition, Date value, String property) {
        if (value == null) {
            throw new RuntimeException("Value for " + property + " cannot be null");
        }
        addCriterion(condition, new java.sql.Date(value.getTime()), property);
    }

    protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
        if (values == null || values.size() == 0) {
            throw new RuntimeException("Value list for " + property + " cannot be null or empty");
        }
        List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
        Iterator<Date> iter = values.iterator();
        while (iter.hasNext()) {
            dateList.add(new java.sql.Date(iter.next().getTime()));
        }
        addCriterion(condition, dateList, property);
    }

    protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + property + " cannot be null");
        }
        addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
    }

    //user_id为空
    public UserExample.Criteria andUserIdIsNull() {
        addCriterion("user_id is null");
        return (UserExample.Criteria) this;
    }

    //user_id不为空
    public UserExample.Criteria andUserIdIsNotNull() {
        addCriterion("user_id is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserIdEqualTo(Integer value) {
        addCriterion("user_id =", value, "userId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserIdNotEqualTo(Integer value) {
        addCriterion("user_id <>", value, "userId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserIdGreaterThan(Integer value) {
        addCriterion("user_id >", value, "userId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
        addCriterion("user_id >=", value, "userId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserIdLessThan(Integer value) {
        addCriterion("user_id <", value, "userId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserIdLessThanOrEqualTo(Integer value) {
        addCriterion("user_id <=", value, "userId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserIdIn(List<Integer> values) {
        addCriterion("user_id in", values, "userId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserIdNotIn(List<Integer> values) {
        addCriterion("user_id not in", values, "userId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserIdBetween(Integer value1, Integer value2) {
        addCriterion("user_id between", value1, value2, "userId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserIdNotBetween(Integer value1, Integer value2) {
        addCriterion("user_id not between", value1, value2, "userId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeIsNull() {
        addCriterion("age is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeIsNotNull() {
        addCriterion("age is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeEqualTo(Integer value) {
        addCriterion("age =", value, "age");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeNotEqualTo(Integer value) {
        addCriterion("age <>", value, "age");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeGreaterThan(Integer value) {
        addCriterion("age >", value, "age");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeGreaterThanOrEqualTo(Integer value) {
        addCriterion("age >=", value, "age");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeLessThan(Integer value) {
        addCriterion("age <", value, "age");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeLessThanOrEqualTo(Integer value) {
        addCriterion("age <=", value, "age");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeIn(List<Integer> values) {
        addCriterion("age in", values, "age");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeNotIn(List<Integer> values) {
        addCriterion("age not in", values, "age");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeBetween(Integer value1, Integer value2) {
        addCriterion("age between", value1, value2, "age");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andAgeNotBetween(Integer value1, Integer value2) {
        addCriterion("age not between", value1, value2, "age");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdIsNull() {
        addCriterion("credit_id is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdIsNotNull() {
        addCriterion("credit_id is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdEqualTo(String value) {
        addCriterion("credit_id =", value, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdNotEqualTo(String value) {
        addCriterion("credit_id <>", value, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdGreaterThan(String value) {
        addCriterion("credit_id >", value, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdGreaterThanOrEqualTo(String value) {
        addCriterion("credit_id >=", value, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdLessThan(String value) {
        addCriterion("credit_id <", value, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdLessThanOrEqualTo(String value) {
        addCriterion("credit_id <=", value, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdLike(String value) {
        addCriterion("credit_id like", value, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdNotLike(String value) {
        addCriterion("credit_id not like", value, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdIn(List<String> values) {
        addCriterion("credit_id in", values, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdNotIn(List<String> values) {
        addCriterion("credit_id not in", values, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdBetween(String value1, String value2) {
        addCriterion("credit_id between", value1, value2, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andCreditIdNotBetween(String value1, String value2) {
        addCriterion("credit_id not between", value1, value2, "creditId");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeIsNull() {
        addCriterion("pregnant_type is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeIsNotNull() {
        addCriterion("pregnant_type is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeEqualTo(Integer value) {
        addCriterion("pregnant_type =", value, "pregnantType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeNotEqualTo(Integer value) {
        addCriterion("pregnant_type <>", value, "pregnantType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeGreaterThan(Integer value) {
        addCriterion("pregnant_type >", value, "pregnantType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeGreaterThanOrEqualTo(Integer value) {
        addCriterion("pregnant_type >=", value, "pregnantType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeLessThan(Integer value) {
        addCriterion("pregnant_type <", value, "pregnantType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeLessThanOrEqualTo(Integer value) {
        addCriterion("pregnant_type <=", value, "pregnantType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeIn(List<Integer> values) {
        addCriterion("pregnant_type in", values, "pregnantType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeNotIn(List<Integer> values) {
        addCriterion("pregnant_type not in", values, "pregnantType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeBetween(Integer value1, Integer value2) {
        addCriterion("pregnant_type between", value1, value2, "pregnantType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantTypeNotBetween(Integer value1, Integer value2) {
        addCriterion("pregnant_type not between", value1, value2, "pregnantType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekIsNull() {
        addCriterion("pregnant_week is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekIsNotNull() {
        addCriterion("pregnant_week is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekEqualTo(String value) {
        addCriterion("pregnant_week =", value, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekNotEqualTo(String value) {
        addCriterion("pregnant_week <>", value, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekGreaterThan(String value) {
        addCriterion("pregnant_week >", value, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekGreaterThanOrEqualTo(String value) {
        addCriterion("pregnant_week >=", value, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekLessThan(String value) {
        addCriterion("pregnant_week <", value, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekLessThanOrEqualTo(String value) {
        addCriterion("pregnant_week <=", value, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekLike(String value) {
        addCriterion("pregnant_week like", value, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekNotLike(String value) {
        addCriterion("pregnant_week not like", value, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekIn(List<String> values) {
        addCriterion("pregnant_week in", values, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekNotIn(List<String> values) {
        addCriterion("pregnant_week not in", values, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekBetween(String value1, String value2) {
        addCriterion("pregnant_week between", value1, value2, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andPregnantWeekNotBetween(String value1, String value2) {
        addCriterion("pregnant_week not between", value1, value2, "pregnantWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobIsNull() {
        addCriterion("job is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobIsNotNull() {
        addCriterion("job is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobEqualTo(String value) {
        addCriterion("job =", value, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobNotEqualTo(String value) {
        addCriterion("job <>", value, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobGreaterThan(String value) {
        addCriterion("job >", value, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobGreaterThanOrEqualTo(String value) {
        addCriterion("job >=", value, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobLessThan(String value) {
        addCriterion("job <", value, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobLessThanOrEqualTo(String value) {
        addCriterion("job <=", value, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobLike(String value) {
        addCriterion("job like", value, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobNotLike(String value) {
        addCriterion("job not like", value, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobIn(List<String> values) {
        addCriterion("job in", values, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobNotIn(List<String> values) {
        addCriterion("job not in", values, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobBetween(String value1, String value2) {
        addCriterion("job between", value1, value2, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andJobNotBetween(String value1, String value2) {
        addCriterion("job not between", value1, value2, "job");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateIsNull() {
        addCriterion("confinement_date is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateIsNotNull() {
        addCriterion("confinement_date is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateEqualTo(Date value) {
        addCriterionForJDBCDate("confinement_date =", value, "confinementDate");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateNotEqualTo(Date value) {
        addCriterionForJDBCDate("confinement_date <>", value, "confinementDate");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateGreaterThan(Date value) {
        addCriterionForJDBCDate("confinement_date >", value, "confinementDate");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateGreaterThanOrEqualTo(Date value) {
        addCriterionForJDBCDate("confinement_date >=", value, "confinementDate");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateLessThan(Date value) {
        addCriterionForJDBCDate("confinement_date <", value, "confinementDate");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateLessThanOrEqualTo(Date value) {
        addCriterionForJDBCDate("confinement_date <=", value, "confinementDate");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateIn(List<Date> values) {
        addCriterionForJDBCDate("confinement_date in", values, "confinementDate");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateNotIn(List<Date> values) {
        addCriterionForJDBCDate("confinement_date not in", values, "confinementDate");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateBetween(Date value1, Date value2) {
        addCriterionForJDBCDate("confinement_date between", value1, value2, "confinementDate");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementDateNotBetween(Date value1, Date value2) {
        addCriterionForJDBCDate("confinement_date not between", value1, value2, "confinementDate");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekIsNull() {
        addCriterion("confinement_week is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekIsNotNull() {
        addCriterion("confinement_week is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekEqualTo(Integer value) {
        addCriterion("confinement_week =", value, "confinementWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekNotEqualTo(Integer value) {
        addCriterion("confinement_week <>", value, "confinementWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekGreaterThan(Integer value) {
        addCriterion("confinement_week >", value, "confinementWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekGreaterThanOrEqualTo(Integer value) {
        addCriterion("confinement_week >=", value, "confinementWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekLessThan(Integer value) {
        addCriterion("confinement_week <", value, "confinementWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekLessThanOrEqualTo(Integer value) {
        addCriterion("confinement_week <=", value, "confinementWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekIn(List<Integer> values) {
        addCriterion("confinement_week in", values, "confinementWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekNotIn(List<Integer> values) {
        addCriterion("confinement_week not in", values, "confinementWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekBetween(Integer value1, Integer value2) {
        addCriterion("confinement_week between", value1, value2, "confinementWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementWeekNotBetween(Integer value1, Integer value2) {
        addCriterion("confinement_week not between", value1, value2, "confinementWeek");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeIsNull() {
        addCriterion("confinement_type is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeIsNotNull() {
        addCriterion("confinement_type is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeEqualTo(Integer value) {
        addCriterion("confinement_type =", value, "confinementType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeNotEqualTo(Integer value) {
        addCriterion("confinement_type <>", value, "confinementType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeGreaterThan(Integer value) {
        addCriterion("confinement_type >", value, "confinementType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeGreaterThanOrEqualTo(Integer value) {
        addCriterion("confinement_type >=", value, "confinementType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeLessThan(Integer value) {
        addCriterion("confinement_type <", value, "confinementType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeLessThanOrEqualTo(Integer value) {
        addCriterion("confinement_type <=", value, "confinementType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeIn(List<Integer> values) {
        addCriterion("confinement_type in", values, "confinementType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeNotIn(List<Integer> values) {
        addCriterion("confinement_type not in", values, "confinementType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeBetween(Integer value1, Integer value2) {
        addCriterion("confinement_type between", value1, value2, "confinementType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andConfinementTypeNotBetween(Integer value1, Integer value2) {
        addCriterion("confinement_type not between", value1, value2, "confinementType");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameIsNull() {
        addCriterion("user_name is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameIsNotNull() {
        addCriterion("user_name is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameEqualTo(String value) {
        addCriterion("user_name =", value, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameNotEqualTo(String value) {
        addCriterion("user_name <>", value, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameGreaterThan(String value) {
        addCriterion("user_name >", value, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameGreaterThanOrEqualTo(String value) {
        addCriterion("user_name >=", value, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameLessThan(String value) {
        addCriterion("user_name <", value, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameLessThanOrEqualTo(String value) {
        addCriterion("user_name <=", value, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameLike(String value) {
        addCriterion("user_name like", value, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameNotLike(String value) {
        addCriterion("user_name not like", value, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameIn(List<String> values) {
        addCriterion("user_name in", values, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameNotIn(List<String> values) {
        addCriterion("user_name not in", values, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameBetween(String value1, String value2) {
        addCriterion("user_name between", value1, value2, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserNameNotBetween(String value1, String value2) {
        addCriterion("user_name not between", value1, value2, "userName");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordIsNull() {
        addCriterion("user_password is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordIsNotNull() {
        addCriterion("user_password is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordEqualTo(String value) {
        addCriterion("user_password =", value, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordNotEqualTo(String value) {
        addCriterion("user_password <>", value, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordGreaterThan(String value) {
        addCriterion("user_password >", value, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordGreaterThanOrEqualTo(String value) {
        addCriterion("user_password >=", value, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordLessThan(String value) {
        addCriterion("user_password <", value, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordLessThanOrEqualTo(String value) {
        addCriterion("user_password <=", value, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordLike(String value) {
        addCriterion("user_password like", value, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordNotLike(String value) {
        addCriterion("user_password not like", value, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordIn(List<String> values) {
        addCriterion("user_password in", values, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordNotIn(List<String> values) {
        addCriterion("user_password not in", values, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordBetween(String value1, String value2) {
        addCriterion("user_password between", value1, value2, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserPasswordNotBetween(String value1, String value2) {
        addCriterion("user_password not between", value1, value2, "userPassword");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenIsNull() {
        addCriterion("user_token is null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenIsNotNull() {
        addCriterion("user_token is not null");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenEqualTo(String value) {
        addCriterion("user_token =", value, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenNotEqualTo(String value) {
        addCriterion("user_token <>", value, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenGreaterThan(String value) {
        addCriterion("user_token >", value, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenGreaterThanOrEqualTo(String value) {
        addCriterion("user_token >=", value, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenLessThan(String value) {
        addCriterion("user_token <", value, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenLessThanOrEqualTo(String value) {
        addCriterion("user_token <=", value, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenLike(String value) {
        addCriterion("user_token like", value, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenNotLike(String value) {
        addCriterion("user_token not like", value, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenIn(List<String> values) {
        addCriterion("user_token in", values, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenNotIn(List<String> values) {
        addCriterion("user_token not in", values, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenBetween(String value1, String value2) {
        addCriterion("user_token between", value1, value2, "userToken");
        return (UserExample.Criteria) this;
    }

    public UserExample.Criteria andUserTokenNotBetween(String value1, String value2) {
        addCriterion("user_token not between", value1, value2, "userToken");
        return (UserExample.Criteria) this;
    }
    public class Criterion {
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

        public Criterion(String condition) {
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

        public Criterion(String condition, Object value) {
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

        public Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }

}