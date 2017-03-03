package com.develop.model.elasticsearch;

import javax.annotation.PreDestroy;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.develop.model.util.DataTypeUtil;
import com.develop.model.util.LogUtil;
import com.develop.model.util.StringUtil;

public class EsSupport {

	private String address;
	private String clusterName;

	protected TransportClient client;

	public synchronized void init() {
		if (client != null) {
			return;
		}
		if (StringUtil.isEmpty(address)) {
			return;
		}
		Builder builder = ImmutableSettings.settingsBuilder();
		if (!StringUtil.isEmpty(clusterName)) {
			builder.put("cluster.name", clusterName);
		} else {
			builder.put("client.transport.ignore_cluster_name", true);
		}
		builder.put("client.transport.sniff", true);
		long start = System.currentTimeMillis();
		client = new TransportClient(builder.build());
		LogUtil.ROOT.info("创建es客户端，耗时(ms):" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		String[] addresses = address.split("[;；]");
		for (String address : addresses) {
			String[] elements = address.split("[:：]");
			if (elements.length != 2) {
				continue;
			}
			String ip = elements[0];
			Integer port = DataTypeUtil.getInteger(elements[1]);
			if (port == null) {
				continue;
			}
			client.addTransportAddresses(new InetSocketTransportAddress(ip, port));
			LogUtil.ROOT.info("添加es地址：" + ip + ":" + port);
		}
	}

	@PreDestroy
	public void destroy() {
		if (client != null) {
			client.close();
		}
	}

	public TransportClient getClient() {
		if (client == null) {
			init();
		}
		return client;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

}
