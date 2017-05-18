package com.develop.model.elasticsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.elasticsearch.index.query.TermFilterBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.indices.IndexMissingException;
import org.elasticsearch.script.ScriptService.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import com.develop.model.util.JSONUtil;
import com.develop.model.util.LogUtil;
import com.develop.model.util.StringUtil;

public class EsUtil {

	public static Object getValue(Map<String, SearchHitField> fields, String fieldName) {
		if (fields == null || fields.isEmpty() || StringUtil.isEmpty(fieldName)) {
			return null;
		}
		SearchHitField field = fields.get(fieldName);
		if (field != null) {
			return field.getValue();
		}
		return null;
	}

	public static List<Object> getValues(Map<String, SearchHitField> fields, String fieldName) {
		if (fields == null || fields.isEmpty() || StringUtil.isEmpty(fieldName)) {
			return null;
		}
		SearchHitField field = fields.get(fieldName);
		if (field != null) {
			return field.getValues();
		}
		return null;
	}

//	public static SearchResult getSearchResult(SearchResponse response) {
//		if (response == null) {
//			return null;
//		}
//		SearchResult result = new SearchResult();
//		result.setTotal(getTotal(response));
//		result.setData(getSearchData(response));
//		return result;
//	}

	public static int bulkIndex(Client client, String index, String type, Map<String, Object>[] data) {
		return bulkIndex(client, index, type, data, null);
	}

	public static int bulkIndex(Client client, String index, String type, Map<String, Object>[] map, String idKey) {
		if (client == null || StringUtil.isEmpty(index, type) || map == null || map.length == 0) {
			return 0;
		}
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (Map<String, Object> m : map) {
			Object id = null;
			if (!StringUtil.isEmpty(idKey)) {
				id = m.get(idKey);
				if (StringUtil.isEmpty(id)) {// idKey不为空，id不能为空
					LogUtil.ROOT.error("id is null,idKey=" + idKey + "," + m);
					continue;
				}
			}
			if (id == null) {
				bulkRequest.add(client.prepareIndex(index, type).setSource(m));
			} else {
				bulkRequest.add(client.prepareIndex(index, type, id.toString()).setSource(m));
			}
		}
		return executeBulkRequest(bulkRequest);
	}

//	public static int bulkIndex(Client client, String index, String type, IndexBean... beans) {
//		if (client == null || StringUtil.isEmpty(index) || beans == null || beans.length == 0) {
//			return 0;
//		}
//		BulkRequestBuilder bulkRequest = client.prepareBulk();
//
//		for (IndexBean bean : beans) {
//			if (bean == null) {
//				continue;
//			}
//			// 索引前统一处理
//			bean.preIndex();
//			if (StringUtil.isEmpty(type) && StringUtil.isEmpty(bean.getType())) {
//				continue;
//			}
//			if (StringUtil.isEmpty(bean.getType())) {
//				bean.setType(type);
//			}
//			// bean转换成map
//			// Map<String, Object> map = BeanUtil.toMap(bean);
//			// if (map == null || map.isEmpty()) {
//			// continue;
//			// }
//			String json = JSONUtil.bean2String(bean);
//			String id = bean.getEsId();
//			if (StringUtil.isEmpty(id)) {
//				id = bean.getId();
//			}
//			if (id == null) {
//				bulkRequest.add(client.prepareIndex(index, bean.getType()).setSource(json));
//			} else {
//				bulkRequest.add(client.prepareIndex(index, bean.getType(), id.toString()).setSource(json));
//			}
//		}
//		return executeBulkRequest(bulkRequest);
//	}

	/**
	 * 简单实现update
	 * 
	 * @param client
	 * @param index
	 * @param type
	 * @param beans
	 * @return
	 */
	public static int bulkUpdate(Client client, String index, String type, List<Map<String, Object>> beans) {
		if (client == null || StringUtil.isEmpty(index) || beans == null || beans.size() == 0) {
			return 0;
		}
		int ii = 0;
		for (Map<String, Object> bean : beans) {
			try {

				if (bean == null) {
					continue;
				}
				String _id = (String) bean.get("_id");
				bean.remove("_id");

				UpdateRequest updateRequest = new UpdateRequest(index, type, _id);
				updateRequest.doc(bean);
				UpdateResponse r = client.update(updateRequest).actionGet();
				LogUtil.ROOT.info("update:" + bean + "; version: " + r.getVersion());
				ii++;
			} catch (Exception e) {
				LogUtil.ROOT.error("UPDATE 错误");
				LogUtil.ROOT.error(e.toString());
				e.printStackTrace();
			}
		}
		return ii;
	}

	public static int bulkDelete(Client client, String index, String type, String... ids) {
		if (client == null || StringUtil.isEmpty(index, type) || ids == null || ids.length == 0) {
			return 0;
		}
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (String id : ids) {
			if (!StringUtil.isEmpty(id)) {
				bulkRequest.add(client.prepareDelete(index, type, id));
			}
		}
		return executeBulkRequest(bulkRequest);
	}

//	public static boolean bulkDeleteByQuery(Client client, String index, String type, IndexBean bean) {
//		if (client == null || StringUtil.isEmpty(index, type) || bean == null) {
//			return false;
//		}
//		String id = bean.getId();
//		if (StringUtil.isEmpty(id)) {
//			return false;
//		}
//		BoolFilterBuilder fb = FilterBuilders.boolFilter().must(FilterBuilders.termFilter("id", id));
//		Long esUpdateTime = bean.getEsUpdateTime();
//		if (esUpdateTime != null) {
//			fb.mustNot(FilterBuilders.termFilter("esUpdateTime", esUpdateTime));
//		}
//		// long start = System.currentTimeMillis();
//		client.prepareDeleteByQuery(index).setTypes(type).setQuery(QueryBuilders.filteredQuery(null, fb)).execute().actionGet();
//		// LogUtil.ROOT.info("删除完毕：" + index + "/" + type + "," + fb + "，耗时(ms):" + (System.currentTimeMillis() - start));
//		return true;
//	}

	public static boolean bulkDeleteAll(Client client, String index, String type) {
		if (client == null || StringUtil.isEmpty(index, type)) {
			return false;
		}
		BoolFilterBuilder fb = FilterBuilders.boolFilter().must(FilterBuilders.matchAllFilter());

		// long start = System.currentTimeMillis();
		try {
			client.prepareDeleteByQuery(index).setTypes(type).setQuery(QueryBuilders.filteredQuery(null, fb)).execute().actionGet();
			// LogUtil.ROOT.info("删除完毕：" + index + "/" + type + "," + fb + "，耗时(ms):" + (System.currentTimeMillis() - start));
		}catch (IndexMissingException e){
			LogUtil.ROOT.error("索引不存在。index:"+index+" type:"+type,e);
		}
		return true;
	}

	public static int executeBulkRequest(BulkRequestBuilder request) {
		if (request == null) {
			return 0;
		}
		if (request.numberOfActions() > 0) {
			BulkResponse response = request.execute().actionGet();
			if (response.hasFailures()) {
				LogUtil.ROOT.error("操作失败：" + response.buildFailureMessage());
			} else {
				BulkItemResponse[] items = response.getItems();
				return items != null ? items.length : 0;
			}
		} else {
			LogUtil.ROOT.error("无操作数！");
		}
		return 0;
	}

	public static long getTotal(SearchResponse response) {
		if (response == null) {
			return 0;
		}
		SearchHits searchHits = response.getHits();
		if (searchHits == null) {
			return 0;
		}
		return searchHits.getTotalHits();
	}

	public static String[] getIds(SearchResponse response) {
		if (response == null) {
			return null;
		}
		SearchHits searchHits = response.getHits();
		if (searchHits == null) {
			return null;
		}
		SearchHit[] hits = searchHits.getHits();
		if (hits == null || hits.length == 0) {
			return null;
		}
		List<String> result = null;
		for (SearchHit hit : hits) {
			if (result == null) {
				result = new ArrayList<String>();
			}
			result.add(hit.getId());
		}
		return result != null ? result.toArray(new String[result.size()]) : null;
	}

	public static List<Map<String, Object>> getSearchData(SearchResponse response) {
		if (response == null) {
			return null;
		}
		SearchHits searchHits = response.getHits();
		if (searchHits == null) {
			return null;
		}
		SearchHit[] hits = searchHits.getHits();
		if (hits == null || hits.length == 0) {
			return null;
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (SearchHit hit : hits) {
			Map<String, Object> source = hit.getSource();
			Map<String, SearchHitField> fields = hit.getFields();
			if (source != null && !source.isEmpty()) {
				result.add(source);
			} else if (fields != null && !fields.isEmpty()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (Entry<String, SearchHitField> entry : fields.entrySet()) {
					SearchHitField field = entry.getValue();
					List<Object> value = (List<Object>) field.getValues();
					String name = field.getName();
					if (value == null || value.isEmpty()) {
						continue;
					}
					if (value.size() == 1) {
						map.put(name, value.get(0));
					} else if (!value.isEmpty()) {
						map.put(name, value);
					}
				}
				result.add(map);
			}
		}
		return result;
	}

	public static String join(Text[] texts) {
		if (texts == null || texts.length == 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (Text text : texts) {
			builder.append(text.string());
		}
		return builder.toString();
	}

	public static List<Map<String, Object>> getSearchDataByField(SearchResponse response) {
		if (response == null) {
			return null;
		}
		SearchHits searchHits = response.getHits();
		SearchHit[] hits = searchHits.getHits();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (SearchHit hit : hits) {
			Map<String, Object> source = hit.getSource();
			if (source == null || source.isEmpty()) {
				continue;
			}
			result.add(source);
		}
		return result;
	}

	public static void setPage(SearchRequestBuilder builder, Integer from, Integer size) {
		if (builder == null) {
			return;
		}
		if (from == null || from < 0) {
			from = 0;
		}
		if (size == null || size < 0 || size > 9999) {
			size = 10;
		}
		builder.setFrom(from);
		builder.setSize(size);
	}

	public static void addSort(SearchRequestBuilder builder, Object sortField, SortOrder sort) {
		if (builder == null) {
			return;
		}
		if (!StringUtil.isEmpty(sortField)) {
			if (sort == null) {
				builder.addSort(sortField.toString(), SortOrder.ASC);
			} else {
				builder.addSort(sortField.toString(), sort);
			}
		}
	}

	// public static void addHighLightField(SearchRequestBuilder builder, HighLightField[] fields) {
	// if (fields != null && fields.length > 0) {
	// for (HighLightField field : fields) {
	// if (field == null || StringUtil.isEmpty(field.getField())) {
	// continue;
	// }
	// String preTag = field.getPreTag();
	// String postTag = field.getPostTag();
	// if (StringUtil.isEmpty(preTag)) {
	// preTag = "";
	// }
	// if (StringUtil.isEmpty(postTag)) {
	// postTag = "";
	// }
	// builder.addHighlightedField(field.getField()).setHighlighterPreTags(preTag).setHighlighterPostTags(postTag);
	// }
	// }
	// }

	public static BoolFilterBuilder mustNotTermFilter(BoolFilterBuilder bfb, Object name, Object[] source) {
		if (StringUtil.isEmpty(name) || source == null || source.length == 0) {
			return bfb;
		}
		for (Object value : source) {
			bfb = mustNot(bfb, getTermFilter(name.toString(), value.toString()));
		}
		return bfb;
	}

	public static BoolFilterBuilder mustNotTermFilter(BoolFilterBuilder bfb, Map<Object, Object> source) {
		if (source == null || source.isEmpty()) {
			return bfb;
		}
		for (Entry<Object, Object> entry : source.entrySet()) {
			bfb = mustNot(bfb, getTermFilter(entry.getKey(), entry.getValue()));
		}
		return bfb;
	}

	public static BoolFilterBuilder mustTermFilter(BoolFilterBuilder bfb, Map<Object, Object> source) {
		if (source == null || source.isEmpty()) {
			return bfb;
		}
		for (Entry<Object, Object> entry : source.entrySet()) {
			bfb = must(bfb, getTermFilter(entry.getKey(), entry.getValue()));
		}
		return bfb;
	}

	public static BoolFilterBuilder mustTermFilter(BoolFilterBuilder bfb, Object name, Object[] source) {
		if (StringUtil.isEmpty(name) || source == null || source.length == 0) {
			return bfb;
		}
		for (Object value : source) {
			bfb = must(bfb, getTermFilter(name.toString(), value.toString()));
		}
		return bfb;
	}

	public static BoolFilterBuilder shouldTermFilter(BoolFilterBuilder bfb, Map<Object, Object> source) {
		if (source == null || source.isEmpty()) {
			return bfb;
		}
		for (Entry<Object, Object> entry : source.entrySet()) {
			bfb = should(bfb, getTermFilter(entry.getKey(), entry.getValue()));
		}
		return bfb;
	}

	public static BoolFilterBuilder shouldTermFilter(BoolFilterBuilder bfb, Object name, Object[] source) {
		if (StringUtil.isEmpty(name) || source == null || source.length == 0) {
			return bfb;
		}
		for (Object value : source) {
			if (!StringUtil.isEmpty(value)) {
				bfb = should(bfb, getTermFilter(name.toString(), value.toString()));
			}
		}
		return bfb;
	}

	public static BoolFilterBuilder should(BoolFilterBuilder bfb, FilterBuilder fb) {
		if (fb == null) {
			return bfb;
		}
		if (bfb == null) {
			bfb = FilterBuilders.boolFilter();
		}
		return bfb.should(fb);
	}

	public static BoolFilterBuilder mustNot(BoolFilterBuilder bfb, FilterBuilder fb) {
		if (fb == null) {
			return bfb;
		}
		if (bfb == null) {
			bfb = FilterBuilders.boolFilter();
		}
		return bfb.mustNot(fb);
	}

	public static BoolFilterBuilder must(BoolFilterBuilder bfb, FilterBuilder fb) {
		if (fb == null) {
			return bfb;
		}
		if (bfb == null) {
			bfb = FilterBuilders.boolFilter();
		}
		return bfb.must(fb);
	}

	public static RangeFilterBuilder getRange(Object name, Object from, Object to) {
		if (StringUtil.isEmpty(name)) {
			return null;
		}
		if (from != null || to != null) {
			RangeFilterBuilder rfb = FilterBuilders.rangeFilter(name.toString());
			if (from != null) {
				rfb.from(from);
			}
			if (to != null) {
				rfb.to(to);
			}
			return rfb;
		}
		return null;
	}

	public static TermFilterBuilder getTermFilter(Object name, Object value) {
		if (StringUtil.isEmpty(name) || StringUtil.isEmpty(value)) {
			return null;
		}
		return new TermFilterBuilder(name.toString(), value);
	}

	public static BoolQueryBuilder should(BoolQueryBuilder bqb, QueryBuilder qb) {
		if (qb == null) {
			return bqb;
		}
		if (bqb == null) {
			bqb = QueryBuilders.boolQuery();
		}
		return bqb.should(qb);
	}

	public static BoolQueryBuilder mustNot(BoolQueryBuilder bqb, QueryBuilder qb) {
		if (qb == null) {
			return bqb;
		}
		if (bqb == null) {
			bqb = QueryBuilders.boolQuery();
		}
		return bqb.mustNot(qb);
	}

	public static BoolQueryBuilder must(BoolQueryBuilder bqb, QueryBuilder qb) {
		if (qb == null) {
			return bqb;
		}
		if (bqb == null) {
			bqb = QueryBuilders.boolQuery();
		}
		return bqb.must(qb);
	}

	public static TermQueryBuilder getTermQuery(Object name, Object value) {
		if (StringUtil.isEmpty(name) || StringUtil.isEmpty(value)) {
			return null;
		}
		return new TermQueryBuilder(name.toString(), value);
	}

	public static QueryStringQueryBuilder getQueryStringQuery(String string, Object... fields) {
		return getQueryStringQuery(string, null, fields);
	}

	public static QueryStringQueryBuilder getQueryStringQuery(String string, Operator operator, Object... fields) {
		return getQueryStringQuery(string, null, operator, fields);
	}

	public static QueryStringQueryBuilder getQueryStringQuery(String string, String analyzer, Operator operator, Object... fields) {
		if (StringUtil.isEmpty(string)) {
			return null;
		}
		QueryStringQueryBuilder qsqb = new QueryStringQueryBuilder(QueryParser.escape(string));
		if (fields != null && fields.length > 0) {
			for (Object field : fields) {
				if (field == null) {
					continue;
				}
				qsqb.field(field.toString());
			}
		}
		if (analyzer != null) {
			qsqb.analyzer(analyzer);
		}
		if (operator != null) {
			qsqb.defaultOperator(operator);
		}
		return qsqb;
	}

	public static BoolQueryBuilder shouldTermQuery(BoolQueryBuilder bfb, Object name, Object... source) {
		if (StringUtil.isEmpty(name) || source == null || source.length == 0) {
			return bfb;
		}
		for (Object value : source) {
			if (!StringUtil.isEmpty(value)) {
				bfb = should(bfb, getTermQuery(name.toString(), value.toString()));
			}
		}
		return bfb;
	}

	public static <T> List<T> getResponse(SearchResponse response, Class<T> _class) {
		if (response == null) {
			return null;
		}
		SearchHits searchHits = response.getHits();
		if (searchHits == null) {
			return null;
		}
		SearchHit[] hits = searchHits.getHits();
		if (hits == null || hits.length == 0) {
			return null;
		}
		List<T> list = null;
		for (SearchHit hit : hits) {
			T t = JSONUtil.string2Bean(hit.getSourceAsString(), _class);
			if (t == null) {
				continue;
			}
			if (list == null) {
				list = new ArrayList<T>();
			}
			list.add(t);
		}
		return list;
	}

	/**
	 * 简单script更新
	 * 
	 * @param client
	 * @param index
	 * @param type
	 * @param script
	 *            更新语句
	 * @param paramName
	 *            script语句中参数名
	 * @param paramValues
	 *            script语句中参数值，key为参数名，其中key值"_id"为ES中文档id
	 * @return 成功数
	 */
	public static int updateByScript(Client client, String index, String type, String script, String paramName, List<Map<String, Object>> paramValues) {
		if (client == null || StringUtil.isEmpty(index, type) || paramValues == null || paramValues.size() == 0) {
			return 0;
		}
		BulkRequestBuilder requestBuilder = client.prepareBulk();
		for (Map<String, Object> map : paramValues) {
			if (map == null || map.get("_id") == null || map.get(paramName) == null) {
				continue;
			}
			UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate().setScript(script, ScriptType.INLINE);
			updateRequestBuilder.setIndex(index);
			updateRequestBuilder.setType(type);
			updateRequestBuilder.setId((String) map.get("_id"));
			updateRequestBuilder.addScriptParam(paramName, map.get(paramName));
			updateRequestBuilder.setRetryOnConflict(3);
			requestBuilder.add(updateRequestBuilder);
		}

		return executeBulkRequest(requestBuilder);
	}

	/**
	 * 多参数script更新
	 * 
	 * @param client
	 * @param paramInfos
	 *            script语句中参数值，key为参数名，其中:"script"为更新语句，"index"为索引，"type"为类型，
	 *            key值"_id"为文档id
	 * @return 成功数
	 */
	public static int updateByScript(Client client, List<Map<String, Object>> paramInfos) {
		if (client == null || paramInfos == null || paramInfos.size() == 0) {
			return 0;
		}
		BulkRequestBuilder requestBuilder = client.prepareBulk();
		String script = null;
		String index = null;
		String type = null;
		String id = null;
		for (Map<String, Object> map : paramInfos) {
			script = (String) map.remove("script");
			index = (String) map.remove("index");
			type = (String) map.remove("type");
			id = (String) map.remove("_id");
			if (map == null || StringUtil.isEmpty(script, index, type, id)) {
				continue;
			}
			UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate().setScript(script, ScriptType.INLINE);
			updateRequestBuilder.setIndex(index);
			updateRequestBuilder.setType(type);
			updateRequestBuilder.setId(id);
			updateRequestBuilder.setScriptParams(map);
			updateRequestBuilder.setRetryOnConflict(3);
			requestBuilder.add(updateRequestBuilder);
		}

		return executeBulkRequest(requestBuilder);
	}

}
