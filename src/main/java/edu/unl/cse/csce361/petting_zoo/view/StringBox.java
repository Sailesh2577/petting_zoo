package edu.unl.cse.csce361.petting_zoo.view;

/**
 * <p>A very simple terminal-based display. Can be used to place arbitrary strings in arbitrary locations in a terminal
 * window. StringBox will <i>not</i> redraw the screen. Instead, it will produce a string that, when printed, will fill
 * the screen, causing the screen's previous contents to scroll off of the screen.</p>
 *
 * <p>A typical usage is:</p>
 * <pre><code>
 *     StringBox stringBox = new StringBox();
 *     String screen = stringBox
 *             .placeString(...)
 *                  ⋮
 *             .placeString(...)
 *             .toString();
 *     System.out.println(screen);
 * </code></pre>
 *
 * <p>Note that StringBox is <i>not</i> robust to hidden characters, such as VT100 escape sequences.</p>
 */
@SuppressWarnings("unused")
public class StringBox {
    private int maximumWidth;
    private int maximumHeight;
    private int logicalHeight;

    private StringRow[] rows;

    /**
     * <p>Produces a StringBox suitably-sized for a standard 24×80 terminal. The StringBox will be 23×80; if the string
     * is printed with <code>System.out.println()</code> then it will leave the cursor on the 24th line, where the
     * user can enter their input without scrolling the top of the string off the screen.</p>
     *
     * <p>Alternatively, you might create a StringBox using <code>StringBox(24,80)</code>. If the string is printed
     * with <code>System.out.print()</code> then it will leave the cursor at the end of the 24th line without scrolling
     * the top of the string off of the screen. This particular style would be useful if you place the prompt on the
     * 24th line.</p>
     *
     * @see #StringBox(int, int)
     */
    public StringBox() {
        this(23, 80);    // standard terminal is 24×80, but leave room for the user's input
    }

    /**
     * <p>Produces an arbitrarily-sized StringBox. The StringBox should be no wider than your terminal and should be
     * at least one line shorter than your terminal. If the string is printed with <code>System.out.println()</code>
     * then it will leave the cursor on the last line, where the user can enter their input without scrolling the top
     * of the string off the screen.</p>
     *
     * <p>Alternatively, you might create a StringBox whose height is the height of your terminal. If the string is
     * printed with <code>System.out.print()</code> then it will leave the cursor at the end of the last line without
     * scrolling the top of the string off of the screen. This particular style would be useful if you place the
     * prompt on the last line.</p>
     *
     * @param boxHeight the number of rows in this StringBox
     * @param boxWidth  the number of columns in this StringBox
     */
    public StringBox(int boxHeight, int boxWidth) {
        if (boxHeight > 0 && boxWidth > 0) {
            this.maximumHeight = boxHeight;
            this.maximumWidth = boxWidth;
            rows = new StringRow[boxHeight];
            for (int i = 0; i < boxHeight; i++) {
                rows[i] = new StringRow(boxWidth);
            }
            logicalHeight = 0;
        } else {
            throw new IllegalArgumentException("String Box must be at least 1 character wide by 1 character tall. " +
                    "Dimensions " + boxWidth + "×" + boxHeight + " are too small.");
        }
    }

    /**
     * <p>Places a string in the StringBox with its upper-left corner in the specified location. If the string
     * contains multiple lines, each line after the first will be placed in the row subsequent to the previous line,
     * and the lines will be left-justified. Any portions of the string that would be placed outside the StringBox's
     * defined boundaries will be silently truncated.</p>
     *
     * <p>Equivalent to <code>placeStringAlignTopLeft(string, topRow, leftColumn)</code>.</p>
     *
     * @param string     the string to be placed in the StringBox
     * @param topRow     the row on which the first line of the string should be placed
     * @param leftColumn the column in which the first character of each row should be placed
     * @return the current StringBox object, suitable for chained calls
     * @see #placeStringAlignTopLeft(String, int, int)
     */
    public StringBox placeString(String string, int topRow, int leftColumn) {
        return placeStringAlignTopLeft(string, topRow, leftColumn);
    }

    /**
     * Places a string in the StringBox with its upper-left corner in the specified location. If the string
     * contains multiple lines, each line after the first will be placed in the row subsequent to the previous line,
     * and the lines will be left-justified. Any portions of the string that would be placed outside the StringBox's
     * defined boundaries will be silently truncated.
     *
     * @param string     the string to be placed in the StringBox
     * @param topRow     the row on which the first line of the string should be placed
     * @param leftColumn the column in which the first character of each row should be placed
     * @return the current StringBox object, suitable for chained calls
     */
    public StringBox placeStringAlignTopLeft(String string, int topRow, int leftColumn) {
        String[] strings = string.split("\n");
        int firstRow = Math.max(topRow, 0);
        int lastRow = Math.min(topRow + strings.length, maximumHeight);
        int offset = -topRow;
        for (int i = firstRow; i < lastRow; i++) {
            rows[i].placeSubstringAlignLeft(strings[i + offset], leftColumn);
        }
        logicalHeight = Math.max(logicalHeight, lastRow);
        return this;
    }

    /**
     * Places a string in the StringBox with its lower-left corner in the specified location. If the string
     * contains multiple lines, each line before the last will be placed in the row previous to the subsequent line,
     * and the lines will be left-justified. Any portions of the string that would be placed outside the StringBox's
     * defined boundaries will be silently truncated.
     *
     * @param string     the string to be placed in the StringBox
     * @param bottomRow  the row on which the last line of the string should be placed
     * @param leftColumn the column in which the first character of each row should be placed
     * @return the current StringBox object, suitable for chained calls
     */
    public StringBox placeStringAlignBottomLeft(String string, int bottomRow, int leftColumn) {
        String[] strings = string.split("\n");
        return placeStringAlignTopLeft(string, bottomRow - strings.length + 1, leftColumn);
    }

    /**
     * Places a string in the StringBox with its upper-right corner in the specified location. If the string
     * contains multiple lines, each line after the first will be placed in the row subsequent to the previous line,
     * and the lines will be right-justified. Any portions of the string that would be placed outside the StringBox's
     * defined boundaries will be silently truncated.
     *
     * @param string      the string to be placed in the StringBox
     * @param topRow      the row on which the first line of the string should be placed
     * @param rightColumn the column in which the last character of each row should be placed
     * @return the current StringBox object, suitable for chained calls
     */
    public StringBox placeStringAlignTopRight(String string, int topRow, int rightColumn) {
        String[] strings = string.split("\n");
        int firstRow = Math.max(topRow, 0);
        int lastRow = Math.min(topRow + strings.length, maximumHeight);
        int offset = -topRow;
        for (int i = firstRow; i < lastRow; i++) {
            rows[i].placeSubstringAlignRight(strings[i + offset], rightColumn);
        }
        logicalHeight = Math.max(logicalHeight, lastRow);
        return this;
    }

    /**
     * Places a string in the StringBox with its lower-right corner in the specified location. If the string
     * contains multiple lines, each line before the last will be placed in the row previous to the subsequent line,
     * and the lines will be right-justified. Any portions of the string that would be placed outside the StringBox's
     * defined boundaries will be silently truncated.
     *
     * @param string      the string to be placed in the StringBox
     * @param bottomRow   the row on which the last line of the string should be placed
     * @param rightColumn the column in which the first character of each row should be placed
     * @return the current StringBox object, suitable for chained calls
     */
    public StringBox placeStringAlignBottomRight(String string, int bottomRow, int rightColumn) {
        String[] strings = string.split("\n");
        return placeStringAlignTopRight(string, bottomRow - strings.length + 1, rightColumn);
    }

    /**
     * <p>Generates the string that the client code produced by calling {@link #placeString(String, int, int)} and
     * its related methods. Any unused lines between the last line of text and the bottom of the StringBox will be
     * filled with newLines so that when the string is printed, the previous string will fully scroll off of the screen.
     *
     * <p>Equivalent to <code>toString(true)</code>.</p>
     *
     * @return the string built by calls to {@link #placeString(String, int, int)} and its related methods
     * @see #toString(boolean)
     */
    @Override
    public String toString() {
        return toString(true);
    }

    /**
     * Generates the string that the client code produced by calling {@link #placeString(String, int, int)} and
     * its related methods. If this method's argument is <code>true</code>, then any unused lines between the last line
     * of text and the bottom of the StringBox will be filled with newLines so that when the string is printed, the
     * previous string will fully scroll off of the screen. If the argument is <code>false</code>, then the returned
     * string will stop after the last line of text.
     *
     * @param padToHeight indicates whether newlines should be placed after the last line of text
     * @return the string built by calls to {@link #placeString(String, int, int)} and its related methods
     */
    public String toString(boolean padToHeight) {
        StringBuilder returnString = new StringBuilder(maximumHeight * (maximumWidth + 1));
        if (logicalHeight > 0) {
            returnString.append(rows[0].toString());
        }
        for (int i = 1; i < logicalHeight; i++) {
            returnString.append("\n").append(rows[i].toString());
        }
        if (padToHeight) {
            for (int i = logicalHeight; i < maximumHeight; i++) {
                returnString.append("\n").append(rows[i].toString());
            }
        }
        return returnString.toString();
    }

    /**
     * A helper inner class. Client code should not directly access StringRow objects.
     */
    static class StringRow {
        private StringBuilder stringBuilder;
        private int maximumWidth;
        private int rightEdge;

        public StringRow(int maximumWidth) {
            this.maximumWidth = maximumWidth;
            stringBuilder = new StringBuilder(maximumWidth);
        }

        public StringRow placeSubstring(String string, int leftColumn) {
            return placeSubstringAlignLeft(string, leftColumn);
        }

        public StringRow placeSubstringAlignLeft(String string, int leftColumn) {
            String modifiedString = string
                    .replace("\t", "    ")
                    .replace("\r", "")
                    .replace("\n", "\\");
            int actualLeftColumn = leftColumn;
            if (actualLeftColumn > rightEdge) {
                int paddingSize = actualLeftColumn - rightEdge;
                StringBuilder padding = new StringBuilder(paddingSize);
                for (int i = 0; i < paddingSize; i++) {     // This is a job made for String.repeat() in Java 11
                    padding.append(" ");
                }
                modifiedString = padding.toString() + modifiedString;
                actualLeftColumn -= paddingSize;
            }
            if (actualLeftColumn < 0) {
                modifiedString = modifiedString.substring(-actualLeftColumn);
                actualLeftColumn = 0;
            }
            int stringLength = modifiedString.length();
            stringBuilder.replace(actualLeftColumn, actualLeftColumn + stringLength, modifiedString);
            rightEdge = Math.max(rightEdge, actualLeftColumn + stringLength);
            int overshoot = rightEdge - maximumWidth;
            if (overshoot > 0) {
                rightEdge -= overshoot;
                stringBuilder.setLength(rightEdge);
            }
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        public StringRow placeSubstringAlignRight(String string, int rightColumn) {
            String modifiedString = string
                    .replace("\t", "    ")
                    .replace("\r", "")
                    .replace("\n", "\\");
            int leftColumn = rightColumn - modifiedString.length();
            return placeSubstringAlignLeft(modifiedString, leftColumn);
        }

        @Override
        public String toString() {
            return stringBuilder.toString();
        }
    }
}
