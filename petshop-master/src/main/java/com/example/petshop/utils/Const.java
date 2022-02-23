package com.example.petshop.utils;

public class Const {
    public static final String LOGIN_KEY = "login-user";

    public static enum UserType {
        ADMIN("管理员", "00"),
        NORMAL("普通用户", "01");

        private String name;
        private String key;

        private UserType(String name, String key) {
            this.name = name;
            this.key = key;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
