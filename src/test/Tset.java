package test;

import net.paoding.rose.jade.annotation.SQL;

public interface Tset {
	public static final int aa = 1;
	
}
interface C extends Tset{
	@SQL(Tset.aa+"")
	public void aa();
}
