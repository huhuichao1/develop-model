package com.develop.model.elasticsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import com.develop.model.enumu.KeywordEnum;
import com.develop.model.enumu.ResourceType;
import com.develop.model.util.CharacterUtil;

@Service
public class SearchAnalyzerService {

	/**
	 * 分词返回切词字符串集合
	 * @param keyword
	 * @param Analyzer_type
	 * @param index
	 * @param esSupport
	 * @return
	 */
	public static List<String> getIkAnalyzerStringTerm(String keyword,String Analyzer_type,String index,EsSupport esSupport){
		List<String> terms=new ArrayList<String>();
		AnalyzeResponse response=esSupport.getClient().admin().indices().prepareAnalyze(index, keyword).setAnalyzer(Analyzer_type).execute().actionGet();
		List<AnalyzeToken> tokens=response.getTokens();
		for(AnalyzeToken t:tokens){
			String term =t.getTerm();
			System.out.println("type:"+t.getType()+": term:"+t.getTerm());
			if(!StringUtils.isEmpty(term)){
				terms.add(term);
			}
		}
		System.out.println(terms);
		return terms;
	}
	
	/**
	 * 分词返回切词token
	 * @param keyword
	 * @param Analyzer_type
	 * @param index
	 * @param esSupport
	 * @return
	 */
	public static List<AnalyzeToken> getIkAnalyzerTokenTerm(String keyword,String Analyzer_type,String index,EsSupport esSupport){
		return esSupport.getClient().admin().indices().prepareAnalyze(index, keyword).setAnalyzer(Analyzer_type).execute().actionGet().getTokens();
	}
	
	/**
	 * 先用iksmart切
	 * 
	 * @param keyword
	 * @param Analyzer_type
	 * @param index
	 * @param esSupport
	 */
	public static QueryBuilder logicProductAnalyzerProcessor(String keyword,String Analyzer_type,String index,String indextype,EsSupport esSupport){
		QueryBuilder builer=null;
		List<AnalyzeToken> list=getIkAnalyzerTokenTerm(keyword, Analyzer_type, index, esSupport);
		if(list.size()==1){
			//分词为一个词单元处理逻辑
			builer=singleTermHander(list,indextype);
		}else if(list.size()==2){
			//分词为二个词单元处理逻辑
			builer=dobuleTermHander(keyword,list,indextype);
		}else {
			builer=dobuleTermHander(keyword,list,indextype);
		}
		return builer;
	}

	/**
	 * title必须全部匹配
	 * @param list
	 * @param indextype
	 * @return
	 */
	private static QueryBuilder dobuleTermHander(String keyword,List<AnalyzeToken> list, String indextype) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param list
	 */
	private static QueryBuilder singleTermHander(List<AnalyzeToken> list,String indextype) {
		AnalyzeToken token=list.get(0);
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();
		String term=token.getTerm();
		//中文
		/**
		 * 如果为中文，不管有几个字 都检索title.
		 */
		if(token.getType().startsWith("CN_")){
			//如果2个字以上，则搜索ik和其他类目
			if(term.length()>1){
				bqb.should(QueryBuilders.termQuery("title.ik", token.getTerm()));			
				if(indextype.equalsIgnoreCase(ResourceType.store.name())){
					bqb.should(QueryBuilders.termQuery("storeBizCategorys.name", term).boost(0.8f));  //门店类型
					bqb.should(QueryBuilders.termQuery("typeName", term).boost(0.8f));  //门店类型
					//标签去掉
//					bqb.should(QueryBuilders.termQuery("geoTags.keywordLowercase", term).boost(0.8f));
//					bqb.should(QueryBuilders.termQuery("categoryTags.keywordLowercase", term).boost(0.8f));
				}else{
					bqb.should(QueryBuilders.termQuery("categorys.name", term).boost(0.8f));  //门店类型
				}
				if(Arrays.asList(KeywordEnum.terms).contains(term)&&!StringUtils.isEmpty(KeywordEnum.map.get(term))){
					bqb.should(QueryBuilders.termQuery("_type", KeywordEnum.map.get(term)).boost(0.1f));  //数据类型
				}
				bqb.should(QueryBuilders.termQuery("pinyin", CharacterUtil.getPinyin(term)));
			}
			bqb.should(QueryBuilders.termQuery("title.ngram", token.getTerm()).boost(0.3f));
//		}else if(token.getType().equals("ENGLISH")||token.getType().equals("ARABIC")){
		}else{
			bqb.should(QueryBuilders.termQuery("title.ik", token.getTerm()));
			bqb.should(QueryBuilders.termQuery("title.ngram", token.getTerm()).boost(0.3f));
			bqb.should(QueryBuilders.termQuery("pinyin", CharacterUtil.getPinyin(term)));
			bqb.should(QueryBuilders.termQuery("engName", term).boost(0.9f));
			bqb.should(QueryBuilders.termQuery("jianpin", term).boost(0.5f));
		}
		return bqb;
	}
	
	
}
