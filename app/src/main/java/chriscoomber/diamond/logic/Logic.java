package chriscoomber.diamond.logic;

import android.support.annotation.NonNull;
import android.util.Log;

import chriscoomber.diamond.ui.OutputInterface;

/**
 * This is where the logic of this App is centralized for this assignment.
 * <p>
 * The assignments are designed this way to simplify your early
 * Android interactions.  Designing the assignments this way allows
 * you to first learn key 'Java' features without having to beforehand
 * learn the complexities of Android.
 */
public class Logic implements LogicInterface {
    /**
     * This is a String to be used in Logging (if/when you decide you
     * need it for debugging).
     */
    @NonNull
    public static final String TAG = Logic.class.getName();

    /**
     * This is the variable that stores our OutputInterface instance.
     * <p>
     * This is how we will interact with the User Interface [MainActivity.java].
     * <p>
     * It is called 'out' because it is where we 'out-put' our
     * results. (It is also the 'in-put' from where we get values
     * from, but it only needs 1 name, and 'out' is good enough).
     */
    @NonNull
    private final OutputInterface mOut;

    /**
     * This is the constructor of this class.
     * <p>
     * It assigns the passed in [MainActivity] instance (which
     * implements [OutputInterface]) to 'out'.
     */
    public Logic(@NonNull OutputInterface out){
        mOut = out;
    }

    @Override
    public void process(int size) {
        // Calculate the size of the image in rows/columns. The center row is (totalRows-1)/2, which
        // happens to be equal to the size.
        int totalRows = 2*size + 1;
        int totalColumns = 2*size + 2;
        Log.d(TAG, String.format("Drawing a diamond of size %d: " +
                "totalRows==%d; totalColumns==%d; centerRow==%d",
                size, totalRows, totalColumns, size));

        // Iterate over the rows to draw, drawing rows of characters.
        for (int i = 0; i <= totalRows - 1; i++) {
            if (i == 0 || i == totalRows - 1) {
                // First or last row; draw the frame end
                drawFrameRow(totalColumns);
            } else {
                // Any other row, this is a regular diamond piece
                // The diamond body uses a '=' character on odd rows.
                boolean isDoubleDashed = i % 2 == 1;

                // Recall that the center row has index equal to size
                if (i < size) {
                    // Top half; draw a normal body row.
                    // The width of the diamond at this point is is 2*i.
                    drawDiamondRow(totalColumns, 2*i, isDoubleDashed, true);
                } else if (i == size) {
                    // Center row; draw the center
                    drawDiamondCenterRow(totalColumns, isDoubleDashed);
                } else {
                    // Bottom half; draw a normal body row.
                    // The width of the diamond at this point is 2*((totalRows-1)-i).
                    drawDiamondRow(totalColumns, 2*((totalRows-1)-i), isDoubleDashed, false);
                }
            }

            // Move to the next row
            mOut.print("\n");
        }
    }

    /**
     * Draw one of the frames rows of the picture. E.g. if totalColumns == 6 draw "+----+"
     * @param totalColumns the number of columns to fill; must be even
     */
    private void drawFrameRow(int totalColumns) {
        // Iterate over the columns to draw, drawing characters.
        for (int j = 0; j <= totalColumns - 1; j++){
            if (j == 0 || j == totalColumns - 1){
                mOut.print("+");
            } else {
                mOut.print ("-");
            }
        }
    }

    /**
     * Draw the center row of the diamond.
     * @param totalColumns the number of columns in the whole picture; must be even
     * @param isDoubleDashed whether this row should use '=' characters instead of '-' characters.
     */
    private void drawDiamondCenterRow(int totalColumns, boolean isDoubleDashed){
        // Iterate over the columns to draw, drawing characters.
        for (int j = 0; j <= totalColumns - 1; j++){
            if (j == 0 || j == totalColumns - 1){
                // Print the edge of the frame
                mOut.print("|");
            } else if (j == 1) {
                // Print the left diamond tip
                mOut.print("<");
            } else if (j == totalColumns - 2){
                // Print the right diamond tip
                mOut.print(">");
            } else {
                mOut.print(isDoubleDashed ? "=" : "-");
            }
        }
    }

    /**
     * Draw a non-center row of the diamond, e.g. "|  /--\  |"
     *
     * The number of non-diamond characters is totalColumns-width.
     * This means on each side the diamond starts (totalColumns-width)/2 places in.
     *
     * @param totalColumns the number of columns in the whole picture; must be even
     * @param width the width of the diamond in this row; must be even
     * @param isDoubleDashed whether this row should use '=' characters instead of '-' characters.
     * @param isUpper whether this row is in the upper half of the diamond or not
     */
    private void drawDiamondRow(int totalColumns,
                                int width,
                                boolean isDoubleDashed,
                                boolean isUpper) {
        // The diamond is centred in the middle. Calculate the indent on both sides. This
        // calculation is done with no rounding, since the integers involved are even.
        int indent = (totalColumns - width)/2;

        // Iterate over the columns to draw, drawing characters.
        for (int j = 0; j <= totalColumns - 1; j++) {
            if (j == 0 || j == totalColumns - 1){
                // Print the edge of the frame
                mOut.print("|");
            } else if (j < indent || j > totalColumns - 1 - indent) {
                // Print a space.
                mOut.print(" ");
            } else if (j == indent) {
                // Print the left of the diamond
                mOut.print(isUpper ? "/" : "\\");
            } else if (j == totalColumns - 1 - indent) {
                // Print the right of the diamond
                mOut.print(isUpper ? "\\" : "/");
            } else {
                // Print the middle of the diamond
                mOut.print(isDoubleDashed ? "=" : "-");
            }
        }
    }
}
