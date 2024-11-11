package com.abc.books.data

import com.abc.books.data.models.Book
import com.abc.books.data.models.book.AccessInfo
import com.abc.books.data.models.book.BookFormat
import com.abc.books.data.models.book.ImageLinks
import com.abc.books.data.models.book.MaturityRating
import com.abc.books.data.models.book.VolumeInfo

object DefaultDataProvider {

    val book = Book(
        id = "ASDQWEWsdsdsfc",
        selfLink = "",
        volumeInfo = VolumeInfo(
            title = "Some Book's name",
            authors = listOf("Aaa", "BbB"),
            publishedDate = "12-24-2000",
            description = "Some very long description WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
            categories = listOf("Book", "Name", "Unknown"),
            maturityRating = MaturityRating.MATURE,
            imageLinks = ImageLinks(thumbnail = "http://images.unsplash.com/photo-1541963463532-d68292c34b19?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
            language = "ru"
        ),
        accessInfo = AccessInfo(
            epub = BookFormat(isAvailable = false),
            pdf = BookFormat(isAvailable = false),
            webReaderLink = ""
        )
    )

    enum class GeneralISBN(val code: String) {
        ANTIQUES_AND_COLLECTIBLES("ANT000000"),
        ARCHITECTURE("ARC000000"),
        ART("ART000000"),
        BIBLES("BIB000000"),
        BIOGRAPHY_AND_AUTOBIOGRAPHY("BIO000000"),
        BODY_MIND_AND_SPIRIT("OCC000000"),
        BUSINESS_AND_ECONOMICS("BUS000000"),
        COMICS_AND_GRAPHIC_NOVELS("CGN000000"),
        COMPUTERS("COM000000"),
        COOKING("CKB000000"),
        CRAFTS_AND_HOBBIES("CRA000000"),
        DESIGN("DES000000"),
        DRAMA("DRA000000"),
        EDUCATION("EDU000000"),
        FAMILY_AND_RELATIONSHIPS("FAM000000"),
        FICTION("FIC000000"),
        FOREIGN_LANGUAGE_STUDY("FOR000000"),
        GAMES_AND_ACTIVITIES("GAM000000"),
        GARDENING("GAR000000"),
        HEALTH_AND_FITNESS("HEA000000"),
        HISTORY("HIS000000"),
        HOUSE_AND_HOME("HOM000000"),
        HUMOR("HUM000000"),
        JUVENILE_FICTION("JUV000000"),
        JUVENILE_NONFICTION("JNF000000"),
        LANGUAGE_ARTS_AND_DISCIPLINES("LAN000000"),
        LAW("LAW000000"),
        LITERARY_COLLECTIONS("LCO000000"),
        LITERARY_CRITICISM("LIT000000"),
        MATHEMATICS("MAT000000"),
        MEDICAL("MED000000"),
        MUSIC("MUS000000"),
        NATURE("NAT000000"),
        PERFORMING_ARTS("PER000000"),
        PETS("PET000000"),
        PHILOSOPHY("PHI000000"),
        PHOTOGRAPHY("PHO000000"),
        POETRY("POE000000"),
        POLITICAL_SCIENCE("POL000000"),
        PSYCHOLOGY("PSY000000"),
        REFERENCE("REF000000"),
        RELIGION("REL000000"),
        SCIENCE("SCI000000"),
        SELF_HELP("SEL000000"),
        SOCIAL_SCIENCE("SOC000000"),
        SPORTS_AND_RECREATION("SPO000000"),
        STUDY_AIDS("STU000000"),
        TECHNOLOGY_AND_ENGINEERING("TEC000000"),
        TRANSPORTATION("TRA000000"),
        TRAVEL("TRV000000"),
        TRUE_CRIME("TRU000000"),
        YOUNG_ADULT_FICTION("YAF000000"),
        YOUNG_ADULT_NONFICTION("YAN000000")
    }
}