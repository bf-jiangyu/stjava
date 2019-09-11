package priv.bingfeng.stjava.common.support;

import java.util.HashMap;
import java.util.Map;

public class DaoQueryCriterion {

	public static final String FILTER = "filter";
	
	Map<String, Object> filterMap;
	
	private String groupField;// 分组
	
	private String[] sortFields = null;// 排序字段
	private int[] sortTypes = null;// 排序类型 0:升 1:降
	
	private int organId;
	private String tableSuffix = "";
	
	private int start = -1;
	private int limit = 0;
	
	public DaoQueryCriterion(Map<String, Object> filterMap) {
		this.initValue("", filterMap, "", null, null);
	}
	public DaoQueryCriterion(int suffixNum, Map<String, Object> filterMap) {
		this.initValue(suffixNum + "", filterMap, "", null, null);
	}
	public DaoQueryCriterion(String tableSuffix, Map<String, Object> filterMap) {
		this.initValue(tableSuffix, filterMap, "", null, null);
	}
	
	public DaoQueryCriterion(Map<String, Object> filterMap, String[] sortFields, int[] sortTypes) {
		this.initValue("", filterMap, "", sortFields, sortTypes);
	}
	public DaoQueryCriterion(int suffixNum, Map<String, Object> filterMap, String[] sortFields, int[] sortTypes) {
		this.initValue(suffixNum + "", filterMap, "", sortFields, sortTypes);
	}
	public DaoQueryCriterion(String tableSuffix, Map<String, Object> filterMap, String[] sortFields, int[] sortTypes) {
		this.initValue(tableSuffix, filterMap, "", sortFields, sortTypes);
	}
	
	public DaoQueryCriterion(Map<String, Object> filterMap, String groupField, String[] sortFields, int[] sortTypes) {
		this.initValue("", filterMap, groupField, sortFields, sortTypes);
	}
	public DaoQueryCriterion(int suffixNum, Map<String, Object> filterMap, String groupField, String[] sortFields, int[] sortTypes) {
		this.initValue(suffixNum + "", filterMap, groupField, sortFields, sortTypes);
	}
	public DaoQueryCriterion(String tableSuffix, Map<String, Object> filterMap, String groupField, String[] sortFields, int[] sortTypes) {
		this.initValue(tableSuffix, filterMap, groupField, sortFields, sortTypes);
	}
	
	private void initValue(String tableSuffix, Map<String, Object> filterMap, String groupField, String[] sortFields, int[] sortTypes) {
		this.tableSuffix = tableSuffix;
		
		if (filterMap == null) filterMap = new HashMap<String, Object>();
		this.filterMap = filterMap;
		if (filterMap.containsKey("tableSuffix")) {
			this.tableSuffix = (String) filterMap.get("tableSuffix");
		} else if (filterMap.containsKey("organId")) {
			this.organId = (Integer) filterMap.get("organId");
		}
		
		this.groupField = groupField;
		this.sortFields = sortFields;
		this.sortTypes = sortTypes;
	}
	
	/**
	 * 排序
	 */
	public boolean getIsSort() {
		return sortFields != null && sortTypes != null && sortFields.length != 0 && sortFields.length == sortTypes.length;
	}
	public String getSortSql() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sortFields.length; i++) {
			sb.append(", " + sortFields[i] + (sortTypes[i] == 0 ? " ASC" : " DESC"));
		}
		return sb.length() == 0 ? "" : sb.substring(1);
	}
	
	/**
	 * 分页查询
	 */
	public void setLimit(int start, int limit) {
		if (start < 0 || limit < 0)
			throw new IllegalArgumentException("起始与翻页参数必须>=0");
		this.start = start;
		this.limit = limit;
	}
	public boolean getIsLimit() {
		return start != -1 && limit != 0;
	}
	public String getLimitSql() {
		return start + ", " + limit;
	}
	
	public Map<String, Object> getFilterMap() {
		return filterMap;
	}

	public String getGroupField() {
		return groupField;
	}

	public String getTableSuffix() {
		return tableSuffix;
	}
	
	public int getOrganId() {
		return organId;
	}
	
}
