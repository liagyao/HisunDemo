package com.hisuntech.entity;

/**
 * @Desciption :oracle数据库的配置实体类
 * @Author :liling
 * @Created : 2018.7.25
 */
public class DBConfig {

    private String url;                                     //数据库的URL
    private String username;                        //数据库连接的用户名
    private String password;                        //数据库连接密码
    private String databaseName;              //数据库名
    private String databaseBrand;              //数据库商（oracle,mysql,db2,sql server）

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseBrand() {
        return databaseBrand;
    }

    public void setDatabaseBrand(String databaseBrand) {
        this.databaseBrand = databaseBrand;
    }
}
