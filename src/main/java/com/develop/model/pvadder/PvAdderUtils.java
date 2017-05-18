package com.develop.model.pvadder;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * PV飙升器，提供了四种灵活强大的PV飙升选择 
 * @author ysc
 */
public class PvAdderUtils{
    private static final AtomicInteger TOTAL_SUCCESS = new AtomicInteger();
    private static final AtomicInteger TOTAL_FAIL = new AtomicInteger();
    private static final AtomicInteger TOTAL_ERROR = new AtomicInteger();
    private static final Random RANDOM = new Random();
    private static String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static String encoding = "gzip, deflate";
    private static String language = "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3";
    private static String connection = "keep-alive";
    private static String host = "blog.csdn.net";
    private static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0";
    private static int reportThreshold = 1000;
         
    /**
     * PV增加器（专用 --  For CSDN博客 -- For 多个用户的所有博文）
     * @param users CSDN博客用户ID列表
     * @param target 页面PV数，到此PV即停止
     * @param errorCount 当错误数达到指定的数errorCount后线程会随机停止[0-errorCount)秒
     */
    public static void pvAddForCSDNBlogForMultiUserAllPage(List<String> users, int target, int errorCount) {
        for(String user : users){
            List<String> ids = getBlogIdsForUser(user);
            pvAddForCSDNBlogForSingleUserMultiPage(user, ids, target, errorCount);
        }
    }
     
    /**
     * PV增加器（专用 --  For CSDN博客 -- For 单个用户的所有博文）
     * @param user CSDN博客用户ID
     * @param target 页面PV数，到此PV即停止
     * @param errorCount 当错误数达到指定的数errorCount后线程会随机停止[0-errorCount)秒
     */
    public static void pvAddForCSDNBlogForSingleUserAllPage(String user, int target, int errorCount) {
        List<String> ids = getBlogIdsForUser(user);
        pvAddForCSDNBlogForSingleUserMultiPage(user, ids, target, errorCount);
    }
    /**
     * PV增加器（专用 --  For CSDN博客 -- For 单个用户的单篇博文）
     * @param user CSDN博客用户ID
     * @param blogId 要增加PV的博文ID
     * @param target 页面PV数，到此PV即停止
     * @param errorCount 当错误数达到指定的数errorCount后线程会随机停止[0-errorCount)秒
     */
    public static void pvAddForCSDNBlogForSingleUserSinglePage(String user, String blogId, int target, int errorCount) {
        List<String> blogIds = new LinkedList<String>();
        blogIds.add(blogId);
        pvAddForCSDNBlogForSingleUserMultiPage(user, blogIds, target, errorCount);
    }
    /**
     * PV增加器（专用 --  For CSDN博客 -- For 单个用户的多个博文）
     * @param user CSDN博客用户ID
     * @param blogIds 要增加PV的博文ID列表
     * @param target 页面PV数，到此PV即停止
     * @param errorCount 当错误数达到指定的数errorCount后线程会随机停止[0-errorCount)秒
     */
    public static void pvAddForCSDNBlogForSingleUserMultiPage(String user, List<String> blogIds, int target, int errorCount) {
        for(String blogId : blogIds){
            //让每一次访问的URL都不一样，加上time参数，防止缓存
            String url = "http://blog.csdn.net/"+user+"/article/details/"+blogId+"?time="+System.nanoTime()+RANDOM.nextInt(Integer.MAX_VALUE)+RANDOM.nextInt(Integer.MAX_VALUE)+Thread.currentThread().getName();
           
            pvAddForCSDNBlogForSinglePage(url, target, errorCount);
        }
    }
    /**
     * PV增加器（专用 --  For CSDN博客 -- For 单个博文）
     * @param url 博客URL地址
     * @param target 页面PV数，到此PV即停止
     * @param errorCount 当错误数达到指定的数errorCount后线程会随机停止[0-errorCount)秒
     */
    public static void pvAddForCSDNBlogForSinglePage(String url, int target, int errorCount) {
        //以下的CSS PATH和TEXT是使用Fire Fox的插件Fire Bug获取到的
        String cssPath = "html body div#container div#body div#main div.main div#article_details.details div.article_manage span.link_view";
        String text = "人阅读";
        pvAdd(url, target, errorCount, cssPath, text);
    }
    /**
     * PV增加器（通用）
     * @param url 博客URL地址
     * @param target 页面PV数，到此PV即停止
     * @param errorCount 当错误数达到指定的数errorCount后线程会随机停止[0-errorCount)秒
     * @param cssPath xxx人阅读 文本块所在的CSS PATH，可用FireFox获取
     * @param text 和PV在一起并置的文本
     */
    public static void pvAdd(String url, int target, int errorCount, String cssPath, String text) {
        int error=0;
        int pv=0;        
        //如果不想PV整齐划一，这里可以加入一个随机数
//        target = RANDOM.nextInt(target);
        System.out.println("随机化target："+target); 
        while(pv < target){
            try{
                String count = Jsoup.connect(url)
                        .header("Accept", accept)
                        .header("Accept-Encoding", encoding)
                        .header("Accept-Language", language)
                        .header("Connection", connection)
                        .header("Host", host)
                        .header("User-Agent", userAgent)
                        .get()
                        .select(cssPath).text();
                System.out.println(count);
//                if("".equals(count.trim()) ){
//                    TOTAL_FAIL.incrementAndGet();
//                    System.out.println("提取PV文本块的CSS PATH不正确，使用FireFox的Fire Bug找到CSS Path: "+cssPath);
//                    //如果CSS PATH不正确，就退出程序
//                    break;
//                }
                //判断返回的页面是否是正常的博文页面
                if(count.contains(text)){
                    TOTAL_SUCCESS.incrementAndGet();
//                    int sleeptime=RANDOM.nextInt(60)*1000;
//                    System.out.println(url+" 休息"+sleeptime+"秒；");
                    Thread.sleep(30001);
                    //获得当前博文的PV，此值用于控制程序何时退出，一旦达到指定的PV，就可退出
                    pv = Integer.parseInt(count.replace(text, "").trim());
                }else{
                    TOTAL_FAIL.incrementAndGet();
                    System.out.println("和PV数并置的文本不正确，指定的文本："+text+"，实际包含PV的文本："+count);
                    //如果和PV数并置的文本不正确，就退出程序
//                    break;
                    Thread.sleep(60001);
                }
                //当达到一定的错误数，暂停一下，以减缓服务器的压力
                if(error > errorCount){
                    statusReport(url,pv);
                    error=0;
                    Thread.sleep(RANDOM.nextInt(errorCount)*1000);
                }
            //捕获所有异常，确保程序不会退出    
            }catch(Throwable e){                
                //下面的异常就不输出了，想调试的时候自己打开
                //e.printStackTrace();
                //处理已经被博主删除，但是列表页面还存在的页面
                if(e.toString().contains("Status=404")){
                    System.out.println("结束增加PV，页面不存在："+url);
                    return;
                } 
                try {
                    //一般是502错误、连接超时等，CSDN博客服务器相当健壮，随机暂停[50-100)毫秒即可继续
                    Thread.sleep((RANDOM.nextInt(5)+5)*10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                TOTAL_ERROR.incrementAndGet();
                error++;
            }
            //状态报告
            if(TOTAL_SUCCESS.get() % reportThreshold == (reportThreshold-1)){
                statusReport(url,pv);
            }
        }
        statusReport(url,pv);
    }
    private static void statusReport(String url, int pv){
        System.out.println("\n"+Thread.currentThread().getName()+" report for:"+url+"\n  成功数："+TOTAL_SUCCESS+"\n  PV: "+pv+"\n  解析内容失败数："+TOTAL_FAIL+"\n  总异常数："+TOTAL_ERROR);
    }
    /**
     * 获取用户所有页面的所有博文的ID列表
     * @param user 用户ID
     * @return 博文ID列表
     */
    public static List<String> getBlogIdsForUser(String user){
        List<String> ids = new LinkedList<String>();        
        //获取用户页面数
        int pageNumber = getPageNumberForUser(user);
        for(int i=1; i< pageNumber+1; i++){
            List<String> pageIds = getPageBlogIdsForUser(user,i);
            System.out.println("用户【"+user+"】第 "+i+" 页的博文数："+pageIds.size());
            System.out.println("用户【"+user+"】第 "+i+" 页的博文ID为："+pageIds);
            ids.addAll(pageIds);
        }
        System.out.println("用户【"+user+"】总的博文数： "+ids.size());
        System.out.println("用户【"+user+"】总的博文ID为："+ids);
        return ids;
    }
    /**
     * 获取用户某页的博文ID列表
     * @param user 用户ID
     * @param page 页面
     * @return 博文ID列表
     */
    public static List<String> getPageBlogIdsForUser(String user, int page){
        List<String> ids = new LinkedList<String>();
        String cssPath = "html body div#container div#body div#main div.main div.list div.list_item div.article_title h1 span.link_title a";
        String url = "http://blog.csdn.net/"+user+"/article/list/"+page;
        try {
            Elements elements = Jsoup.connect(url)
                    .header("Accept", accept)
                    .header("Accept-Encoding", encoding)
                    .header("Accept-Language", language)
                    .header("Connection", connection)
                    .header("Host", host)
                    .header("User-Agent", userAgent)
                    .get()
                    .select(cssPath);
            for(Element element : elements){
                try{
                    //href="/blogdevteam/article/details/16943989"
                    String href = element.attr("href");
                    //System.out.println(href);
                    String[] attrs = href.split("/");
                    //长度为5，取最后一位即是博文ID
                    //System.out.println(attrs.length);
                    ids.add(attrs[4]);
                } catch (Exception e) {
                    //某一篇文章的失败不要影响其他文章
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ids;
    }
    /**
     * 获取用户博文的页面数
     * @param user 用户ID
     * @return 博文页面数
     */
    public static int getPageNumberForUser(String user){
        String cssPath = "html body div#container div#body div#main div.main div#papelist.pagelist a";
        String url = "http://blog.csdn.net/"+user;
        int pageNumber = 1;
        try {
            Elements elements = Jsoup.connect(url)
                    .header("Accept", accept)
                    .header("Accept-Encoding", encoding)
                    .header("Accept-Language", language)
                    .header("Connection", connection)
                    .header("Host", host)
                    .header("User-Agent", userAgent)
                    .get()
                    .select(cssPath);
            for(Element element : elements){
                try{
                    String text = element.text();
                    if(!"尾页".equals(text.trim())){
                        continue;
                    }
                    //href="/blogdevteam/article/list/16"
                    String href = element.attr("href");
                    //System.out.println(href);
                    String[] attrs = href.split("/");
                    //长度为5，取最后一位即是最大页面
                    //System.out.println(attrs.length);
                    pageNumber = Integer.parseInt(attrs[4]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return pageNumber;
    }
    public static void setAccept(String accept) {
        PvAdderUtils.accept = accept;
    }
    public static void setEncoding(String encoding) {
        PvAdderUtils.encoding = encoding;
    }
    public static void setLanguage(String language) {
        PvAdderUtils.language = language;
    }
    public static void setConnection(String connection) {
        PvAdderUtils.connection = connection;
    }
    public static void setHost(String host) {
        PvAdderUtils.host = host;
    }
    public static void setUserAgent(String userAgent) {
        PvAdderUtils.userAgent = userAgent;
    }
    public static void setReportThreshold(int reportThreshold) {
        PvAdderUtils.reportThreshold = reportThreshold;
    }
    
    public static void main(String[] args) {
    	 int j=10;
    	 for(int i=0;i<j;i++){
    		 System.out.println(RANDOM.nextInt(60)*1000);
    	 }
    
	}
}