package com.cosmin.bookstoreback.services;

public interface SecurityService {

    void setKey(String myKey);
    String encrypt(String strToEncrypt, String secret);
    String decrypt(String strToDecrypt, String secret);

}
