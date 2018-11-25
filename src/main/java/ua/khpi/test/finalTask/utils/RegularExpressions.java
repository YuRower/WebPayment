package ua.khpi.test.finalTask.utils;


public class RegularExpressions {

	private RegularExpressions() {}
	
	public static final String USERNAME = "^[\\p{L}0-9]{5,16}$";
	public static final String NAME = "^[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я-.\'\\s]{0,44}";
	public static final String ACCOUNT_NAME = "^[a-zA-Zа-яА-Я0-9][0-9a-zA-Zа-яА-Я-\\s]{0,44}";
	public static final String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
	public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
	public static final String DIGITS = "[0-9]+";
	public static final String PRICE = "^\\d{1,5}(\\.\\d{2})?$";
	public static final String LENGTH = ".{0,45}";

}
