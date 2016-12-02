package puzzlesolver.ui.fx_2d;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.arrays.PointsBuilder;
import puzzlesolver.constants.Constants;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.enums.Direction;
import puzzlesolver.side.Side;
import puzzlesolver.solver.Solver;

class PuzzleRenderer {

    private boolean reset = true;
    private Piece[][] lastSolution;
    protected boolean done;
    private double[] doubles = null;

    /**
     * Get the side length of a piece, according to the puzzle and window dimensions;
     *
     * @param puzzleWidth  width of puzzle, in pieces
     * @param puzzleHeight height of puzzle, in pieces
     * @param windowWidth  width of window, in pixels
     * @param windowHeight height of window, in pixels
     * @return side length
     */
    public static double getScaledPieceSideLength(int puzzleWidth, int puzzleHeight,
                                                  double windowWidth, double windowHeight) {
        final double scaledPadding = UIConstants.VISUAL_PIECE_PADDING;
        final boolean yConstraint = Double.compare(windowWidth, windowHeight) > 0;
        final double windowShortSide = yConstraint ? windowHeight : windowWidth;
        final double windowLongSide = yConstraint ? windowWidth : windowHeight;
        final double usableSpaceShortSide = windowShortSide - (scaledPadding * 2);
        final double usableSpaceLongSide = windowLongSide - (scaledPadding * 2);

        return Math.min(usableSpaceShortSide / (yConstraint ? puzzleHeight : puzzleWidth),
                        usableSpaceLongSide / (yConstraint ? puzzleWidth : puzzleHeight));
    }

    /**
     * Get a global point from a local point.
     *
     * @param localPoint   local point to calculate from
     * @param orientation  direction of parent side
     * @param pieceX       x-position in 2D points array
     * @param pieceY       y-position in 2D points array
     * @param windowWidth  width of window to render in
     * @param windowHeight width of window to render in
     * @return global point
     */
    static Point getGlobalPoint(Point localPoint, Direction orientation, int pieceX,
                                int pieceY, int puzzleWidth, int puzzleHeight,
                                double windowWidth, double windowHeight) {
        Objects.requireNonNull(localPoint);
        // May have to scale. For now, made a placeholder.
        final double scaledPadding = UIConstants.VISUAL_PIECE_PADDING;
        final boolean yConstraint = Double.compare(windowWidth, windowHeight) > 0;
        final double windowShortSide = yConstraint ? windowHeight : windowWidth;
        final double windowLongSide = yConstraint ? windowWidth : windowHeight;
        final double usableSpaceShortSide = windowShortSide - (scaledPadding * 2);
        final double usableSpaceLongSide = windowLongSide - (scaledPadding * 2);

        final double scaledPieceSideLength
            = Math.min(usableSpaceShortSide / (yConstraint ? puzzleHeight : puzzleWidth),
                       usableSpaceLongSide / (yConstraint ? puzzleWidth : puzzleHeight));

        final double centredMarginSizeX =
            (windowWidth - puzzleWidth * scaledPieceSideLength
             - scaledPieceSideLength / 2) / 2;
        final double centredMarginSizeY =
            (windowHeight - puzzleHeight * scaledPieceSideLength
             - scaledPieceSideLength / 2) / 2;

        final double scaleX = scaledPieceSideLength / UIConstants.VISUAL_PIECE_WIDTH;
        final double scaleY = scaledPieceSideLength / UIConstants.VISUAL_PIECE_HEIGHT;

        final double pieceGlobalX = scaledPadding + pieceX * scaledPieceSideLength;
        final double pieceGlobalY = scaledPadding + pieceY * scaledPieceSideLength;

        double pointGlobalX = pieceGlobalX + centredMarginSizeX;
        double pointGlobalY = pieceGlobalY + centredMarginSizeY;

        switch (orientation) {
            case NORTH:
                pointGlobalX += scaleX * localPoint.x;
                pointGlobalY -= scaleY * localPoint.y;
                break;
            case EAST:
                pointGlobalX += scaledPieceSideLength;
                pointGlobalX += scaleX * localPoint.y;
                pointGlobalY += scaleY * localPoint.x;
                break;
            case SOUTH:
                pointGlobalY += scaledPieceSideLength;
                pointGlobalX += scaleX * localPoint.x;
                pointGlobalY += scaleY * localPoint.y;
                break;
            case WEST:
                pointGlobalY += scaleY * localPoint.x;
                pointGlobalX -= scaleX * localPoint.y;
                break;
            default:
                throw new EnumConstantNotPresentException(Direction.class, orientation.name());
        }

        return new Point(pointGlobalX, pointGlobalY);
    }

    /**
     * Clears the canvas.
     *
     * @param gc canvas to clear
     */
    private void clearCanvas(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    /**
     * Reset {@link this} to a clear canvas. Calling {@link #update(GraphicsContext, Solver)} or
     * {@link #drawPuzzle(GraphicsContext, Solver)} after this will redraw the entire puzzle.
     *
     * @param gc {@link GraphicsContext} on which to draw
     */
    protected void reset(GraphicsContext gc) {
        clearCanvas(gc);
        lastSolution = null;
        done = false;
        reset = true;
    }

    /**
     * Redraws the puzzle, resetting it if the puzzle has changed (rotation, etc).
     *
     * @param gc     {@link GraphicsContext} on which to draw
     * @param solver to draw
     */
    void update(GraphicsContext gc, Solver solver) {
        Piece[][] solution = solver.getSolution();

        if (lastSolution == null || solution.length != lastSolution.length) {
            reset(gc);
            lastSolution = solution;
        }

        drawPuzzle(gc, solver);
    }

    private boolean[][] needsRedraw(Piece[][] solution) {
        final int width = solution.length;
        final int height = solution[0].length;
        final boolean[][] result = new boolean[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Re-draw a piece if it's not null and either it was null before, or we're
                // force re-drawing everything. Maybe we should compare hashcodes between each
                // piece? Slower, but if in the future a piece will every change after being
                // placed, it will support that.
                if (solution[x][y] != null && (reset || lastSolution[x][y] == null)) {
                    result[x][y] = true;
                }
            }
        }

        return result;
    }

    protected void drawPuzzle(GraphicsContext gc, Solver solver) {
        if (done) {
            return;
        }

        final Piece[][] solution = solver.getSolution();
        final int width = solution.length;
        final int height = solution[0].length;

        // If this is the first draw, or the puzzle was rotated, re-draw everything.
        if (lastSolution == null || lastSolution.length != solution.length) {
            reset = true;
        }
        final boolean[][] needsRedraw = needsRedraw(solution);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (needsRedraw[x][y]) {
                    drawPiece(gc, solution[x][y], x, y, width, height);
                }
            }
        }

        lastSolution = solution.clone();
    }

    void drawPiece(GraphicsContext gc, Piece piece, int x, int y,
                   int puzzleWidth, int puzzleHeight) {
        if (doubles == null) {
            doubles = Constants.RANDOM.doubles(puzzleWidth * puzzleHeight).toArray();
        }
    /*
    gc.setFill(
      FILL_COLORS[FILL_COLORS.length - 1 - piece.getPieceType().ordinal() % FILL_COLORS.length]);
    */
        double randomVariant = 0.1d * doubles[y * puzzleWidth + x];
        gc.setFill(
            new Color(1.0d - 0.4d * x / puzzleWidth - randomVariant,
                      0.5d + 0.4d * (y / puzzleHeight / 2 - x / puzzleWidth) + randomVariant,
                      0.9d * y / puzzleHeight + randomVariant, 1.0d));
        gc.setLineJoin(StrokeLineJoin.ROUND);
        gc.setLineCap(StrokeLineCap.ROUND);
        double windowWidth = gc.getCanvas().getWidth();
        double windowHeight = gc.getCanvas().getHeight();

        gc.setLineWidth(
            getScaledPieceSideLength(puzzleWidth, puzzleHeight, gc.getCanvas().getWidth(),
                                     gc.getCanvas().getHeight()) / 10);

        PointsBuilder xs = new PointsBuilder();
        PointsBuilder ys = new PointsBuilder();

        for (Direction direction : Direction.values()) {
            Side s = piece.getSide(direction);
            if (s != null) {
                gc.setStroke(
                    new Color(0.8d - 0.7d * x / puzzleWidth - randomVariant,
                              0.7d * x / puzzleWidth + randomVariant,
                              0.8d - 0.7d * y / puzzleHeight - randomVariant, 1.0d));
                Point[] points = s.getPoints();
                if (direction == Direction.SOUTH || direction == Direction.WEST) {
                    ArrayUtils.reverse(points);
                }
                for (Point point : points) {
                    Point globalPoint = getGlobalPoint(point, direction, x, y,
                                                       puzzleWidth, puzzleHeight,
                                                       windowWidth, windowHeight);
                    xs.add(globalPoint.x);
                    ys.add(globalPoint.y);
                }
            }
        }

        final double[] primitiveXs = ArrayUtils.toPrimitive(xs.toPoints());
        final double[] primitiveYs = ArrayUtils.toPrimitive(ys.toPoints());
        gc.setGlobalAlpha(1.0d);
        gc.fillPolygon(primitiveXs, primitiveYs, xs.size());
        gc.strokePolygon(primitiveXs, primitiveYs, xs.size());
    }
}
