package com.easyquick.common.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.easyquick.common.date.DateUtils;

public class DateUtilsTest {

	@Test
	public void testGetYear() throws Exception {
		System.out.println(DateUtils.getYear(DateUtils.getDate()));
		assertEquals(DateUtils.getYear(DateUtils.getDate()),Integer.valueOf(2014));
	}

	@Test
	public void testGetMonth() throws Exception {
		System.out.println(DateUtils.getMonth(DateUtils.getDate()));
		assertEquals(DateUtils.getMonth(DateUtils.getDate()),Integer.valueOf(6));
	}

	@Test
	public void testGetDay() throws Exception {
		System.out.println(DateUtils.getDay(DateUtils.getDate()));
		assertEquals(DateUtils.getDay(DateUtils.getDate()),Integer.valueOf(17));
	}

	@Test
	public void testGetDateWithDay() throws Exception {
		System.out.println(DateUtils.getDateWithDay(DateUtils.getDate()));
		assertEquals(DateUtils.getDateWithDay(DateUtils.getDate()),Integer.valueOf(20140617));
	}

	@Test
	public void testGetTimeWithSecond() throws Exception {
		System.out.println(DateUtils.getTimeWithSecond(DateUtils.getDate()));
	}

	@Test
	public void testGetTimeWithMillisecond() throws Exception {
		System.out.println(DateUtils.getTimeWithMillisecond(DateUtils.getDate()));
	}

	@Test
	public void testGetDateWithSecond() throws Exception {
		System.out.println(DateUtils.getDateWithSecond(DateUtils.getDate()));
	}

	@Test
	public void testGetDateWithMillisecond() throws Exception {
		System.out.println(DateUtils.getDateWithMillisecond(DateUtils.getDate()));

	}

	@Test
	public void testGetFormatDate() throws Exception {
		System.out.println(DateUtils.getFormatDate(DateUtils.getDate(), "yyyy-MM-dd HH:mm:ss.SSS"));
	}

	@Test
	public void testGetDate() throws Exception {
		System.out.println(DateUtils.getDate());
	}

}
