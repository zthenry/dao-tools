package com.sw;

public class AliasCreator {
	private static int count = 0;
	//创建别名
	public static String createAlias(String table){
		return "alias_"+table+(count++);
	}
}
