package puzzlesolver.generator;

import java.util.LinkedList;
import java.util.List;

import puzzlesolver.Funcs;
import puzzlesolver.Point;
import puzzlesolver.constants.Constants;
import puzzlesolver.side.Side;
import puzzlesolver.side.SimpleSide;

public class CurveGenerator extends RotationGenerator {

    private double minInputXDiff = Constants.SIDE_LENGTH;
    private double maxInputXDiff = Constants.SIDE_LENGTH;
    private double outputXDiff = Constants.SIDE_LENGTH;

    private static final double MIN_Y_DEVIATION = Constants.SIDE_LENGTH * 0.02d;
    private static final double MAX_Y_DEVIATION = Constants.SIDE_LENGTH * 0.1d;

    /**
     * Constructs a new PolypointGenerator with default values for x diff factors. Default values
     * are 0.1 for minimum diff and 0.2 for maximum diff.
     */
    public CurveGenerator() {
        this(0.1d, 0.2d, 0.01d);
    }

    /**
     * Constructs a new PolypointGenerator with the given factors for x point spacing.
     *
     * @param minInputXDiffFactor the factor to multiply {@link Constants#SIDE_LENGTH} by to create
     *                            the minimum x spacing between the points used to generate the
     *                            spline curve, (0, 1], <= {@param maxXDiffFactor}
     * @param maxInputXDiffFactor the factor to multiply {@link Constants#SIDE_LENGTH} by to create
     *                            the maximum x spacing between the points used to generate the
     *                            spline curve, (0, 1], <= {@param maxXDiffFactor}
     * @param outputXDiffFactor   the factor to multiply {@link Constants#SIDE_LENGTH} by to create
     *                            the x spacing between points taken from the spline curve. (0, 1]
     * @throws IllegalArgumentException if any parameter is out of bounds, or {@code minXDiffFactor
     *                                  > maxXDiffFactor}
     */
    public CurveGenerator(double minInputXDiffFactor, double maxInputXDiffFactor, double
        outputXDiffFactor) {
        if (minInputXDiffFactor <= 0 || minInputXDiffFactor > 1 || maxInputXDiffFactor <= 0 ||
            maxInputXDiffFactor > 1 || outputXDiffFactor <= 0 || outputXDiffFactor > 1) {
            throw new IllegalArgumentException("Diff factors must be (0, 1]");
        }
        if (minInputXDiffFactor > maxInputXDiffFactor) {
            throw new IllegalArgumentException("min X factor must be <= max X factor");
        }
        minInputXDiff *= minInputXDiffFactor;
        maxInputXDiff *= maxInputXDiffFactor;
        outputXDiff *= outputXDiffFactor;
    }

    @Override
    public Side generateSide(boolean flat) {
        final List<Point> points = new LinkedList<>();

        if (!flat) {
            // Add midpoints
            for (double x = Funcs.randomInRange(random(), minInputXDiff, maxInputXDiff);
                 x < Constants.SIDE_LENGTH - minInputXDiff;
                 x += Funcs.randomInRange(random(), maxInputXDiff, maxInputXDiff)) {
                points.add(new Point(x, Funcs.randomInRangeNegate(random(),
                                                                  MIN_Y_DEVIATION,
                                                                  MAX_Y_DEVIATION)));
            }
        }

        points.add(0, new Point(0d, 0d)); // Add first corner
        points.add(new Point(Constants.SIDE_LENGTH, 0d)); // Add second corner
        return new SimpleSide(points);
    }
}
