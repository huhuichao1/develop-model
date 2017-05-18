package com.develop.model.elasticsearch;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.develop.model.enumu.ResourceType;
import com.develop.model.util.StringUtil;


public class SearchLogic {
	private static EsSupport esSupport;
	private static SearchRequestBuilder builder;
	static{
		esSupport=new EsSupport();
		esSupport.setAddress("10.213.129.68:11300;10.213.129.69:11300;10.213.129.70:11300");
//		esSupport.setAddress("10.209.240.63:11300;10.209.240.64:11300");
		esSupport.setClusterName("");
    	esSupport.init();
    	builder = esSupport.getClient().prepareSearch("search-product").setTypes("store");
	}
	  /**
     * 纯中文搜索
     * @return
     */
    public List<Map> chineseSearch(String key,Integer cityId) throws Exception{
        DisMaxQueryBuilder  disMaxQueryBuilder=QueryBuilders.disMaxQuery();
        //以关键字开头(优先级最高)
        MatchQueryBuilder q1=QueryBuilders.matchQuery("words",key).analyzer("ngramSearchAnalyzer").boost(5);        
        //完整包含经过分析过的关键字
//         boolean  whitespace=key.contains(" ");
//         int slop=whitespace?50:5;
        QueryBuilder q2=QueryBuilders.matchQuery("words.IKS", key).analyzer("ikSearchAnalyzer").minimumShouldMatch("100%");
        disMaxQueryBuilder.add(q1);
        disMaxQueryBuilder.add(q2);
//        SearchQuery searchQuery=builderQuery(cityId,disMaxQueryBuilder);
        return  null;
    }
    
    /**
     * 最大分查询
     * @param key
     * @return
     */
    public static QueryBuilder getMaxKeywordBuilder(String key){
        DisMaxQueryBuilder  disMaxQueryBuilder=QueryBuilders.disMaxQuery();
        disMaxQueryBuilder.add(QueryBuilders.multiMatchQuery(key, "title.ik").minimumShouldMatch("67%"));
        disMaxQueryBuilder.add(QueryBuilders.termQuery("categorys.name", key));
        return  disMaxQueryBuilder;
    }
    
    /**
     * @param 
     * @return
     */
    public static QueryBuilder getScoreQueryKeywordBuilder(String keyword){
        return  QueryBuilders.constantScoreQuery(QueryBuilders.multiMatchQuery(keyword, "title.ik").minimumShouldMatch("67%"));
    }
    
    
    public static QueryBuilder getKeywordQueryBuilder(String keyword, String pinyin, String indextype) {

    	BoolQueryBuilder bqb = null;
		if (keyword!=null&&!"".equals(keyword)) {
		    keyword = keyword.toLowerCase();
			bqb = QueryBuilders.boolQuery();
			//2016/6/7  去除name.ik
			bqb.should(QueryBuilders.multiMatchQuery(keyword, "title.ik").minimumShouldMatch("67%"));
//			bqb.should(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("geoTags.keywordLowercase", keyword).boost(0.8f)).must(QueryBuilders.termQuery("_type", ResourceType.store.toString())));
//			bqb.should(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("categoryTags.keywordLowercase", keyword).boost(0.8f)).must(QueryBuilders.termQuery("_type", ResourceType.store.toString())));
			if(indextype.equalsIgnoreCase(ResourceType.store.name())){
                bqb.should(QueryBuilders.termQuery("storeBizCategorys.name", keyword));  //门店类型
            }
            else{
                bqb.should(QueryBuilders.termQuery("categorys.name", keyword));  //门店类型
            }
//			bqb.should(QueryBuilders.termQuery("typeName", keyword).boost(0.8f));  //门店类型
//			bqb.should(QueryBuilders.termQuery("engName", keyword).boost(0.9f));
//			bqb.should(QueryBuilders.termQuery("jianpin", keyword).boost(0.5f));
			if (!StringUtil.isEmpty(pinyin)) {
				bqb.should(QueryBuilders.termQuery("pinyin", pinyin));
			}
		}
		return bqb;
    }
    private	static FilterBuilder getKeywordNavigationFilterBuilder(String cityId) {		
		BoolFilterBuilder builder = null;
        if (cityId != null) {
            builder = EsUtil.must(builder, FilterBuilders.boolFilter().should(FilterBuilders.termFilter("stores.cityId", cityId), FilterBuilders.termFilter("cityId", cityId)));
        }
        return builder;
	}
    private static void ffanSearchLogic(){
    	
    	QueryBuilder qb =getKeywordQueryBuilder("汉拿山测试", "", "store");
    	FilterBuilder fb =getKeywordNavigationFilterBuilder("310100");
    	 if (fb != null) {
             qb = QueryBuilders.filteredQuery(qb, fb);
         }
         if (qb != null) {
             builder.setQuery(qb);
         }
         SearchResponse response= builder.execute().actionGet();
         List list=EsUtil.getSearchData(response);
         System.out.println(builder);
    }
    
    public static void main(String[] args) {
//    	QueryBuilder qb =getScoreQueryKeywordBuilder("北京万达广场");
//    	FilterBuilder fb =getKeywordNavigationFilterBuilder("110100");
//    	 if (fb != null) {
//             qb = QueryBuilders.filteredQuery(qb, fb);
//         }
//         if (qb != null) {
//             builder.setQuery(qb);
//         }
//         SearchResponse response= builder.execute().actionGet();
//         List list=EsUtil.getSearchData(response);
//         System.out.println(builder);
//         System.out.println("-----");
         ffanSearchLogic();
//    	SearchAnalyzerService.getIkAnalyzerStringTerm("”“111我爱北京kendeji天安门肯德基肯", "ikIndex", "search-product", esSupport);
//    	SearchAnalyzerService.getIkAnalyzerStringTerm("”“111我爱北京kendeji天安门肯德基肯", "ikSearch", "search-product", esSupport);

	}

}
