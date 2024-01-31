package main.util;

public enum TextEnums {
    FILE("File"),
    ABOUT("About"),
    EXIT("Exit"),
    GOTO("Go to"),
    HELP("Help"),
    NEW("New"),
    NEXT("Next"),
    OPEN("Open"),
    PAGENR("Page number?"),
    PAGENOTFOUND("Page not found!"),
    PREV("Prev"),
    SAVE("Save"),
    VIEW("View"),
    JAB_VERSION("Jabberpoint 1.6 - OU version"),
    SHOWTITLE("showtitle"),
    SLIDETITLE("title"),
    SLIDE("slide"),
    ITEM("item"),
    LEVEL("level"),
    KIND("kind"),
    TEXT("text"),
    IMAGE("image"),
    FONT_NAME("Helvetica"),
    JAB_TITLE("JabberPoint"),
    WIDTH(1200),
    HEIGHT(900);

    private String name;
    private int value;

    TextEnums(String name) {
        this.name = name;
    }

    TextEnums(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }
    public int getValue() {
        return value;
    }
}
