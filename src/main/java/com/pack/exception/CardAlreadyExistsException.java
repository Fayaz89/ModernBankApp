package com.pack.exception;

public class CardAlreadyExistsException extends Exception {

  String msg;
  public CardAlreadyExistsException(String msg)
  {
	  this.msg=msg;
  }
}
