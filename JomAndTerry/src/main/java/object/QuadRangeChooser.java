package object;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuadRangeChooser {
    int[] indices = {0,1,2,3};
    private List<int[]> ranges;
    private int currentIndex;

    public QuadRangeChooser() {
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
