package com.tantuni.zamazon.networks;

public class URL {
    // private static final String ROOT_URL = "https://rest-api-zamazon.herokuapp.com/api";
    private static final String ROOT_URL = "http://192.168.1.21:8080/api";

    public static final String URL_REGISTER_CUSTOMER = ROOT_URL + "/auth/register/customer";
    public static final String URL_REGISTER_SELLER = ROOT_URL + "/auth/register/seller";
    public static final String URL_REGISTER_ADMIN = ROOT_URL + "/auth/register/admin";
    public static final String URL_LOGIN = ROOT_URL + "/auth/login";
    public static final String URL_USERS = ROOT_URL + "/users";
    public static final String URL_CUSTOMERS = ROOT_URL + "/customers";
    public static final String URL_PRODUCTS = ROOT_URL + "/products";
    public static final String URL_CATEGORIES = ROOT_URL + "/categories";
    public static final String URL_SUBCATEGORIES = ROOT_URL + "/subCategories";
    public static final String URL_FEATURES = ROOT_URL + "/features";
    public static final String URL_ORDERS = ROOT_URL + "/orders";

}
