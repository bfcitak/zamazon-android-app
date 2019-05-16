package com.tantuni.zamazon.networks;

public class URL {
    private static final String ROOT_URL = "https://rest-api-zamazon.herokuapp.com/api";
    private static final String ROOT_LOCALHOST = "http://192.168.1.26:8080/api";

    public static final String URL_REGISTER_CUSTOMER = ROOT_URL + "/auth/register/customer";
    public static final String URL_REGISTER_SELLER = ROOT_URL + "/auth/register/seller";
    public static final String URL_REGISTER_ADMIN = ROOT_URL + "/auth/register/admin";
    public static final String URL_LOGIN = ROOT_URL + "/auth/login";
    public static final String URL_USERS = ROOT_URL + "/users";
    public static final String URL_CUSTOMERS = ROOT_URL + "/customers";
    public static final String URL_PRODUCTS = ROOT_URL + "/products";

    public static final String URL_LREGISTER_CUSTOMER = ROOT_LOCALHOST + "/auth/register/customer";
    public static final String URL_LREGISTER_SELLER = ROOT_URL + "/auth/register/seller";
    public static final String URL_LREGISTER_ADMIN = ROOT_URL + "/auth/register/admin";
    public static final String URL_LLOGIN = ROOT_LOCALHOST + "/auth/login";
    public static final String URL_LUSERS = ROOT_LOCALHOST + "/users";
    public static final String URL_LCUSTOMERS = ROOT_URL + "/customers";
    public static final String URL_LPRODUCTS = ROOT_LOCALHOST + "/products";
}
