package object;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuadRangeChooser {
    int[] indices = {0,1,2,3};
    private List<int[]> ranges;
    private int currentIndex;

    public QuadRangeChooser() {
        // {min_row, max_row, min_col, max_col}
        int[][] quadRanges = {
            {3, 7, 3, 9}, // quad 0
            {3, 7, 10, 18}, // quad 1
            {8, 14, 3, 9}, // quad 2
            {8, 14, 10, 18} // quad 3
        };
        Collections.shuffle(Arrays.asList(indices));
        ranges = Arrays.asList(quadRanges);
        currentIndex = 0;
    }
    /**
     * This is a helper method that shuffles of the quadrants to help reduce the clumping of objects dropped.
     * 
     * @return the range of indices for col and row for the chosen quadrant
     */

    public int[] getNextRange() {
        if (currentIndex == 4) {
            Collections.shuffle(Arrays.asList(indices));
            currentIndex = 0;
        }
        int[] range = ranges.get(indices[currentIndex]);
        currentIndex++;
        return range;
    }
}
