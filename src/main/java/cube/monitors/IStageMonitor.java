package cube.monitors;

import java.awt.*;
import java.util.Map;

import cube.models.ICube;

/**
 * @author wenyu
 * @since 11/7/15
 */
public interface IStageMonitor {

    /**
     * Return cubes along with their position.
     * @return cubes with positions.
     */
    Map<Integer, Map<Integer, ICube>> getCubes();

    /**
     * Add cube into monitor.
     * @param cube the cube
     */
    void add(ICube cube);

    /**
     * Check each line and erase cubes if necessary.
     */
    void refresh(Graphics g);

    /**
     * Reset monitor.
     */
    void reset();
}
