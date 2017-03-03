package com.develop.model.thread.temp;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;

import common.DateUtils;

public class Test {
	public static void main(String[] args) {
		MockHttpServletRequest request= new MockHttpServletRequest();
		request.setParameters(null);
		System.out.println(System.currentTimeMillis());
		System.out.println(DateUtils.getCurrentTimeAs14String());
	}
}