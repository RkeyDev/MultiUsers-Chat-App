package util;

public enum TextDecorations {
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    CLEAR_LINE("\033[2K"),
    CURSOR_UP("\033[A"),
    RESET_COLOR("\033[0m");

    private final String decorationCode;

    TextDecorations(String decorationCode) {
        this.decorationCode = decorationCode;
    }

    public String getDecorationCode(){
        return this.decorationCode;
    }
}
