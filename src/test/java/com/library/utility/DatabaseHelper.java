package com.library.utility;


public class DatabaseHelper {


    public static String getBookByIdQuery(String bookID) {
        return "select * from books where id="+bookID;
    }

    public static String getCategoryIdQuery(String categoryName) {
        return "select id from book_categories where name='"+categoryName+"'";
    }

    public static String getUserByIdQuery(int id) {
        return "select full_name,email,user_group_id,status,start_date,end_date,address " +
                "from users where id="+id;
    }
}
