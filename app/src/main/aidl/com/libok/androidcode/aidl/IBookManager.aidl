// IBookManager.aidl
package com.libok.androidcode.aidl;

// Declare any non-default types here with import statements

import com.libok.androidcode.aidl.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
