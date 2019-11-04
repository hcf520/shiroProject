package com.shiroDemo.shiroProject.exceptions;

public class PrincipalIdNullException extends RuntimeException  {

    /** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -3566721065721845856L;
	private static final String MESSAGE = "Principal Id shouldn't be null!";

    public PrincipalIdNullException(Class clazz, String idMethodName) {
        super(clazz + " id field: " +  idMethodName + ", value is null\n" + MESSAGE);
    }
}