CREATE TABLE IF NOT EXISTS Role_Users (
                                          ID_role SERIAL PRIMARY KEY,
                                          name VARCHAR(30) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Users (
                                     ID_users SERIAL PRIMARY KEY,
                                     surname VARCHAR(30) NOT NULL,
    name VARCHAR(30) NOT NULL,
    middle_name VARCHAR(30) NOT NULL,
    login VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role_ID INTEGER REFERENCES Role_Users(ID_role)
    );

CREATE TABLE IF NOT EXISTS Type_pay (
                                        ID_type_pay SERIAL PRIMARY KEY,
                                        name VARCHAR(30) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Pay (
                                   ID_pay SERIAL PRIMARY KEY,
                                   type_pay_ID INTEGER REFERENCES Type_pay(ID_type_pay),
    buyers_ID INTEGER REFERENCES Users(ID_users),
    price DECIMAL(8,2) NOT NULL CHECK (price > 0)
    );

CREATE TABLE IF NOT EXISTS Type_of_lumber (
                                              ID_type_of_lumber SERIAL PRIMARY KEY,
                                              name VARCHAR(30) NOT NULL,
    area_of_speciesion VARCHAR(80) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Tree_species (
                                            ID_tree_species SERIAL PRIMARY KEY,
                                            name VARCHAR(30) NOT NULL,
    characteristics VARCHAR(80) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Lumber (
                                      ID_lumber SERIAL PRIMARY KEY,
                                      type_of_lumber_ID INTEGER REFERENCES Type_of_lumber(ID_type_of_lumber),
    tree_species_ID INTEGER REFERENCES Tree_species(ID_tree_species),
    volume DECIMAL(8,2) NOT NULL CHECK (volume > 0)
    );

CREATE TABLE IF NOT EXISTS Sales (
                                     ID_sales SERIAL PRIMARY KEY,
                                     employee_ID INTEGER REFERENCES Users(ID_users),
    buyers_ID INTEGER REFERENCES Users(ID_users),
    pay_ID INTEGER REFERENCES Pay(ID_pay),
    lumber_ID INTEGER REFERENCES Lumber(ID_lumber)
    );

CREATE INDEX IF NOT EXISTS idx_users_role ON Users(role_ID);
CREATE INDEX IF NOT EXISTS idx_pay_type ON Pay(type_pay_ID);
CREATE INDEX IF NOT EXISTS idx_pay_buyer ON Pay(buyers_ID);
CREATE INDEX IF NOT EXISTS idx_lumber_type ON Lumber(type_of_lumber_ID);
CREATE INDEX IF NOT EXISTS idx_lumber_species ON Lumber(tree_species_ID);
CREATE INDEX IF NOT EXISTS idx_sales_employee ON Sales(employee_ID);
CREATE INDEX IF NOT EXISTS idx_sales_buyer ON Sales(buyers_ID);
CREATE INDEX IF NOT EXISTS idx_sales_pay ON Sales(pay_ID);
CREATE INDEX IF NOT EXISTS idx_sales_lumber ON Sales(lumber_ID);