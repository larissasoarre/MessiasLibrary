# Messias Library
This project is an Java application that was developed aiming to provide a solution for the efficient management of micro and small library businesses to manage their daily operations. Users are able to create, read, update and delete books and loan records. The software's data is also able to store all the information in a database to facilitate data integrity and enable efficient information retrieval. It also contains a registration page with password encryption to protect user accounts and personal information and guarantee data security. The access to the software's features is granted via a login page.


## ✨ Features
The software contains the following features:

* **Inventory management**: Users can easily add new books to the system, modify existing book information, review the complete book inventory and remove specific books from the system when necessary.
* **Loan management**: The system makes it possible to generate loans for books that are available in stock, and makes it possible to manage all current loans via a complete dashboard.
* **Login page**: Provides a secure gateway for registered users to access their accounts. It employs the [BCrypt](https://www.npmjs.com/package/bcrypt) algorithm to compare and verify passwords, increasing the overall security of the login process and data storage.
* **Login management**: Enables users who have administrator-level access to create new logins for new employees. These employees can have librarian or administrator access types.


## 👥 User Stories
As a **librarian** user:
- I can add new books to the library by providing their relevant details, including
*ISBN*, *title*, *author*, *genre*, *publisher*, *price*, *number of books available* and *edition*.
- I can update the book records whenever necessary.
- I can see clearly and intuitively all the books available in the inventory.
- I can remove books that are no longer available or are considered unsuitable from the library software.
- I can make loans of books from the library's inventory, with the ability to enter essential information such as *book ISBN*, customer data such as *name*, *email*, *phone number* and *address*, *loan date* and *loan duration*.
- I can access a clear and intuitive interface to keep track of all my loans.
- I can mark book loans as "returned" to keep complete track of the library's loans.

As an **admin**:
- I have access to the same functionalities as a librarian.
- I can create accounts for employees, giving them credentials to access the library's management resources.

As the **owner**:
- I prioritize the secure storage of employee passwords in the system, using robust *encryption* algorithms to protect confidential information.


## 🖥 Technologies
To run this software, please ensure that the following requirements are met:

*  Java version 20 or later
*  [BCrypt](https://www.npmjs.com/package/bcrypt) version 0.4.3, which can be found in the lib folder.
*  If you're using Oracle, the **ojdbc.jar dependency** for database connection, which can also be found in the lib folder.

By meeting these requirements, you will be able to successfully run the software.


## ⚙️ Installation
To install in developer mode, follow the instructions bellow:

1. Clone the repository on your local directory.

    ```
        git clone <repository_url>
    ```

2. Execute the provided SQL script [here](#sql-script) to create the necessary tables in your database.

    Note: If you decide to rename the tables or attributes, be sure to update the corresponding SQL script in the *BookSaleDAO.java*, *UserLoginDAO.java* and *RentalDAO.java* files located in the dao package inside the src folder. This ensures that the software reflects the renamed information accurately.

3. Configure database connection details. Open the **DBManager.java** file located in the dao package inside the src folder, and replace the existing code with your own database connection details.

    ```
        connection = DriverManager.getConnection("url", "username", "password");
    ```

By following these steps, you can successfully install and configure the application for your specific environment and database.


## 📜 SQL Script
To ensure the smooth operation of the application, please execute the following **SQL script** to create the necessary tables and attributes in your database.

```sql
-- Table: T_PBL_USERLOGIN
CREATE TABLE T_PBL_USERLOGIN (
ID_USERLOGIN NUMBER(10) NOT NULL,
NM_USERNAME VARCHAR2(20) NOT NULL,
DS_PASSWORD VARCHAR2(100) NOT NULL,
TEMP_PASSWORD NUMBER(2,0) NOT NULL,
IS_ADMIN CHAR(1) NOT NULL,
CONSTRAINT PK_USERLOGIN PRIMARY KEY (ID_USERLOGIN),
CONSTRAINT UQ_USERLOGIN_USERNAME UNIQUE (NM_USERNAME)
);

-- Generate Generic User
INSERT INTO T_PBL_USERLOGIN (ID_USERLOGIN, NM_USERNAME, DS_PASSWORD) VALUES ('999', 'firstUser', '1234');

-- Table: T_PBL_ORDER_BOOK
CREATE TABLE T_PBL_ORDER_BOOK (
ID_ORDER_BK NUMBER(10) NOT NULL,
CD_ORDER_ISBN NUMBER(13,0) NOT NULL,
NM_ORDER_TITLE VARCHAR2(50) NOT NULL,
DS_GENRE VARCHAR2(30) NOT NULL,
DS_PUBLISHER VARCHAR2(30) NOT NULL,
VL_SALE NUMBER(5,2) NOT NULL,
QT_ORDER_AVAILABLE NUMBER(3,0) NOT NULL,
NM_AUTHOR VARCHAR2(50) NOT NULL,
DS_EDITION NUMBER(2,0),
CONSTRAINT PK_ORDER_BOOK PRIMARY KEY (ID_ORDER_BK)
);

-- Table: T_PBL_RENTAL
CREATE TABLE T_PBL_RENTAL (
ID_RENTAL NUMBER NOT NULL,
ISBN_BOOK NUMBER NOT NULL,
DT_RENTAL DATE NOT NULL,
DS_RENTAL_DURATION NUMBER NOT NULL,
DS_RENTAL_PRICE NUMBER(5,2) NOT NULL,
NM_CLIENT VARCHAR2(50 BYTE) NOT NULL,
DS_EMAIL VARCHAR2(50 BYTE) NOT NULL,
NR_PHONE NUMBER(11,0) NOT NULL,
NM_STREET VARCHAR2(50 BYTE) NOT NULL,
NR_HOUSE VARCHAR2(5 BYTE) NOT NULL,
NR_CEP NUMBER(8,0) NOT NULL,
NM_DISTRIC VARCHAR2(30 BYTE) NOT NULL,
NM_CITY VARCHAR2(30 BYTE) NOT NULL,
NM_STATE VARCHAR2(20 BYTE) NOT NULL,
DT_RETURN DATE NOT NULL,
CONSTRAINT PK_RENTAL PRIMARY KEY (ID_RENTAL)
);
```


## 🕹 How to Run
Once you have completed the [installation](#⚙️-installation) and [configuration](#sql-script-🖥) process, you can start using the application. To get started, follow these steps:

1. Export the project as an **executable .jar** file and designate `LoginPage.java` as the startup page.
2. Open your command line interpreter and run the following code to start the application:

    ```
        java -jar fileName.jar
    ```

    **Note:** make sure you navigate to the directory where the file is saved and replace `"fileName"` with the actual name of the file.

3. For the first login to the software, use the username and password generic bellow. For security reasons, you **must** change your password to a new one after your first login.

    ```
        username: firstUser
        password: 1234
    ```


## ❓ Q&A
**1. How can I reset my password if I forget it?**

The password recovery feature isn't available yet but will be released in the next version of the software. It is recommended that you create a new account.


**2. When I mark a loan as returned, can I access the loan data afterwards?**

Unfortunately not. Once a loan is marked as returned, you will no longer have access to the loan data in question.


## ⭐ Fun Facts

- This challenge was developed as part of an orientation program at the [FIAP](https://www.fiap.com.br) University in collaboration with [Palo Alto Networks](https://www.paloaltonetworks.com.br).

- You can checkout our project at the offial challange website [goDigital Code](https://godigital-code.vercel.app).

## 🧾 License
MIT © Larissa Soares.
