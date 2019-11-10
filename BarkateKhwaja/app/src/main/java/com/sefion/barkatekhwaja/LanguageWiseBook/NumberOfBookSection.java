package com.sefion.barkatekhwaja.LanguageWiseBook;

/**
 * Created by Hafsa on 2/2/2018.
 */

public class NumberOfBookSection {
    String Sections, Book;

    public NumberOfBookSection() {
    }

    public NumberOfBookSection(String sections, String book) {
        Sections = sections;
        Book = book;
    }

    public String getSections() {
        return Sections;
    }

    public void setSections(String sections) {
        Sections = sections;
    }

    public String getBook() {
        return Book;
    }

    public void setBook(String book) {
        Book = book;
    }
}
