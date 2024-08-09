drop database ClothShop;
drop table Order;
CREATE DATABASE ClothShop;
CREATE DATABASE IF NOT EXISTS ClothShop;
USE ClothShop;

CREATE TABLE User(
                     UserId VARCHAR(6) NOT NULL,
                     UserName VARCHAR(30) NOT NULL,
                     Contact VARCHAR (15),
                     CONSTRAINT PRIMARY KEY (UserId)
);

CREATE TABLE Employee(
                         EmpId VARCHAR(6) NOT NULL,
                         FirstName VARCHAR(30) NOT NULL,
                         LastName VARCHAR(30) NOT NULL,
                         EmpAddress VARCHAR(30),
                         DateOfJoing DATE,
                         Contact VARCHAR (15),
                         NIC VARCHAR(20),
                         UserId VARCHAR(6) NOT NULL,
                         CONSTRAINT PRIMARY KEY (EmpId),
                         CONSTRAINT FOREIGN KEY(UserId) REFERENCES User(UserId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE EmployeeAttend(
                               EmpAttendId VARCHAR(6),
                               Date DATE,
                               EmpId VARCHAR(6) NOT NULL,
                               CONSTRAINT PRIMARY KEY (EmpAttendId),
                               CONSTRAINT FOREIGN KEY(EmpId) REFERENCES Employee(EmpId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE EmployeeSalary(
                               EmpSalaryId VARCHAR(6),
                               Salary DECIMAL(6,2) NOT NULL,
                               EmpId VARCHAR(6) NOT NULL,
                               CONSTRAINT PRIMARY KEY (EmpSalaryId),
                               CONSTRAINT FOREIGN KEY(EmpId) REFERENCES Employee(EmpId) ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE Customer(
                         CustID VARCHAR(6) NOT NULL,
                         CustName VARCHAR(30) NOT NULL,
                         Contact VARCHAR (15),
                         UserId VARCHAR(6) NOT NULL,
                         CONSTRAINT PRIMARY KEY (CustID),
                         CONSTRAINT FOREIGN KEY(UserId) REFERENCES User(UserId) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Payment(
                        PaymentID VARCHAR(6) NOT NULL,
                        Date DATE,
                        Time TIME,
                        CustID VARCHAR(6) NOT NULL,
                        CONSTRAINT PRIMARY KEY (PaymentID),
                        CONSTRAINT FOREIGN KEY(CustID) REFERENCES Customer(CustID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Orders(
                       OrderId VARCHAR(6) NOT NULL,
                       OrderDate DATE,
                       Price DECIMAL(6,2) NOT NULL,
                       CustID VARCHAR(6) NOT NULL,
                       CONSTRAINT PRIMARY KEY (OrderId),
                       CONSTRAINT FOREIGN KEY (CustID) REFERENCES Customer(CustID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Item(
                     ItemID VARCHAR(6) NOT NULL,
                     ItemName VARCHAR(30) NOT NULL,
                     Quantity INT(11) NOT NULL,
                     ClothBrand VARCHAR(30) NOT NULL,
                     CONSTRAINT PRIMARY KEY (ItemID)
);

CREATE TABLE OrderDetail(
                            OrderId VARCHAR(6) NOT NULL,
                            ItemID VARCHAR(6) NOT NULL,
                            Amount DECIMAL(6,2) NOT NULL,
                            Quantity INT(11) NOT NULL,
                            CONSTRAINT PRIMARY KEY (OrderId,ItemID),
                            CONSTRAINT FOREIGN KEY (OrderId) REFERENCES Orders(OrderId) ON UPDATE CASCADE ON DELETE CASCADE,
                            CONSTRAINT FOREIGN KEY (ItemID) REFERENCES Item(ItemID) ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE Supplier(
                         SupplierId VARCHAR(6) NOT NULL,
                         SupplierName VARCHAR(30) NOT NULL,
                         Description VARCHAR(50) NOT NULL,
                         Contact VARCHAR (15),
                         CONSTRAINT PRIMARY KEY (SupplierId)
);

CREATE TABLE Give(
                     SupplierId VARCHAR(6) NOT NULL,
                     ItemID VARCHAR(6) NOT NULL,
                     Quantity INT(11) NOT NULL,
                     Date DATE,
                     Description VARCHAR(50) NOT NULL,
                     CONSTRAINT PRIMARY KEY (SupplierId,ItemID),
                     CONSTRAINT FOREIGN KEY (SupplierId) REFERENCES Supplier(SupplierId) ON UPDATE CASCADE ON DELETE CASCADE,
                     CONSTRAINT FOREIGN KEY (ItemID) REFERENCES Item(ItemID) ON UPDATE CASCADE ON DELETE CASCADE
);




