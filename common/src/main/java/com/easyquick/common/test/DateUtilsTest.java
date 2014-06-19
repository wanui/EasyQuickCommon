package com.easyquick.common.test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;

import javax.swing.text.StyledEditorKit.ForegroundAction;

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
	
	@Test
	public void testGetMonthLastDay() throws Exception {
		System.out.println(DateUtils.getMonthLastDay(201402));
	}

	@Test
	public void testGetBetweenMonth() throws Exception {
		Collection<Integer> s= DateUtils.getBetweenMonth(201501, 201411);
		for(Integer i : s){
			System.out.println(i);
		}
		
	}
	
	@Test
	public void testGetBetweenMonths() throws Exception {
		int s= DateUtils.getBetweenMonths(201501, 201411);
		System.out.println(s);
		
	}
	
	@Test
	public void testGetBetweenDate() throws Exception {
		Collection<Date> s= DateUtils.getBetweenDate(
				DateUtils.convertYYYYMMDDStringToDate("20140101"),
				DateUtils.convertYYYYMMDDStringToDate("20140201"));
		for(Date i : s){
			System.out.println(i);
		}
		
	}
	
	@Test
	public void testGetBetweenDays() throws Exception {
		int s= DateUtils.getBetweenDateDays(
				DateUtils.convertYYYYMMDDStringToDate("20140101"),
				DateUtils.convertYYYYMMDDStringToDate("20140201"));
		System.out.println(s);
		
	}
}
