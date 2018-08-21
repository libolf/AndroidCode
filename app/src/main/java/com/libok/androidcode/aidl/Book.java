package com.libok.androidcode.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author liboK  2018/08/08 下午 03:56
 */
public class Book implements Parcelable{
    private String bookName;
    private String bookAuthor;
    private int bookId;

    public Book(String bookName, String bookAuthor, int bookId) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookId = bookId;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public Book setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public Book setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public int getBookId() {
        return bookId;
    }

    public Book setBookId(int bookId) {
        this.bookId = bookId;
        return this;
    }

    protected Book(Parcel in) {
        bookName = in.readString();
        bookAuthor = in.readString();
        bookId = in.readInt();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeString(bookAuthor);
        dest.writeInt(bookId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookId=" + bookId +
                '}';
    }
}
