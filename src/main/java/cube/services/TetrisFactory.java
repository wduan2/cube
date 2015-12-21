package cube.services;

import cube.aop.TraceUtils;
import cube.configs.CubeConfig;
import cube.configs.StageConfig;
import cube.models.Cube;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;
import cube.models.Rotator;
import cube.models.Tetris;
import cube.models.TetrisType;

import com.google.common.collect.ImmutableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

/**
 * @author wenyu
 * @since 10/24/15
 */
public class TetrisFactory implements Factory<ITetris> {
    private final static TetrisFactory FACTORY = new TetrisFactory();
    private final static Logger LOG = LogManager.getLogger(TraceUtils.ACTION_LOGGER);

    private final CubeConfig cubeConfig;
    private final StageConfig stageConfig;
    private final Random rand;

    /**
     * The start point of tetris.
     * start-point = cube.width * ( max # of cubes in row / 2 )
     */
    private final Integer startPoint;

    private TetrisFactory() {
        cubeConfig = CubeConfig.getInstance();
        stageConfig = StageConfig.getInstance();

        rand = new Random(cubeConfig.getTetrisRollingSeed());

        startPoint = cubeConfig.getWidth() * stageConfig.getXMonitorSize() / 2;
    }

    public static TetrisFactory getInstance() {
        return FACTORY;
    }

    /**
     * Build random tetris.
     *
     * O - 0, S - 1, Z - 2, L - 3, J - 4, T - 5, I - 6
     *
     * @return the tetirs
     */
    @Override
    public ITetris build() {
        return build(cubeConfig.getTetrisIdBound());
    }

    private ITetris build(final int bound) {
        ITetris tetris;
        int id = rand.nextInt(bound);

        // TODO: Move position into the enum ?
        LOG.debug("Building new tetirs, id: {}, name: {}.", id, TetrisType.fromId(id));

        switch (id) {
            case 0:  tetris = O(); break;
            case 1:  tetris = S(); break;
            case 2:  tetris = Z(); break;
            case 3:  tetris = L(); break;
            case 4:  tetris = J(); break;
            case 5:  tetris = T(); break;
            default: tetris = I(); break;
        }

        return tetris;
    }

    private ITetris build(ICube center, List<ICube> rim, TetrisType type) {
        if (null != center) {
            center.setColor(cubeConfig.getCenterColor());
        }

        Rotator rotator = new Rotator(center, rim);

        return new Tetris(center, rim, rotator, type);
    }

    private ITetris O() {
        ICube cube1 = new Cube(new Position(startPoint, 0));
        ICube cube2 = new Cube(new Position(startPoint + cubeConfig.getWidth(), 0));
        ICube cube3 = new Cube(new Position(startPoint, cubeConfig.getHeight()));
        ICube cube4 = new Cube(new Position(startPoint + cubeConfig.getWidth(), cubeConfig.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube2, cube3, cube4);

        return build(null, rim, TetrisType.O);
    }

    private ITetris S() {
        ICube cube1 = new Cube(new Position(startPoint + cubeConfig.getWidth(), 0));
        ICube cube2 = new Cube(new Position(startPoint, cubeConfig.getHeight()));
        ICube cube3 = new Cube(new Position(startPoint + cubeConfig.getWidth(), cubeConfig.getHeight()));
        ICube cube4 = new Cube(new Position(startPoint, 2 * cubeConfig.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube2, cube4);

        return build(cube3, rim, TetrisType.S);
    }

    private ITetris Z() {
        ICube cube1 = new Cube(new Position(startPoint, 0));
        ICube cube2 = new Cube(new Position(startPoint, cubeConfig.getHeight()));
        ICube cube3 = new Cube(new Position(startPoint + cubeConfig.getWidth(), cubeConfig.getHeight()));
        ICube cube4 = new Cube(new Position(startPoint + cubeConfig.getWidth(), 2 * cubeConfig.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube3, cube4);

        return build(cube2, rim, TetrisType.Z);
    }

    private ITetris L() {
        ICube cube1 = new Cube(new Position(startPoint, 0));
        ICube cube2 = new Cube(new Position(startPoint, cubeConfig.getHeight()));
        ICube cube3 = new Cube(new Position(startPoint, 2 * cubeConfig.getHeight()));
        ICube cube4 = new Cube(new Position(startPoint + cubeConfig.getWidth(), 2 * cubeConfig.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube3, cube4);

        return build(cube2, rim, TetrisType.L);
    }

    private ITetris J() {
        ICube cube1 = new Cube(new Position(startPoint + cubeConfig.getWidth(), 0));
        ICube cube2 = new Cube(new Position(startPoint + cubeConfig.getWidth(), cubeConfig.getHeight()));
        ICube cube3 = new Cube(new Position(startPoint + cubeConfig.getWidth(), 2 * cubeConfig.getHeight()));
        ICube cube4 = new Cube(new Position(startPoint, 2 * cubeConfig.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube3, cube4);

        return build(cube2, rim, TetrisType.J);
    }

    private ITetris T() {
        ICube cube1 = new Cube(new Position(startPoint + cubeConfig.getWidth(), 0));
        ICube cube2 = new Cube(new Position(startPoint, cubeConfig.getHeight()));
        ICube cube3 = new Cube(new Position(startPoint + cubeConfig.getWidth(), cubeConfig.getHeight()));
        ICube cube4 = new Cube(new Position(startPoint + 2 * cubeConfig.getWidth(), cubeConfig.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube2, cube4);

        return build(cube3, rim, TetrisType.T);
    }

    private ITetris I() {
        ICube cube1 = new Cube(new Position(startPoint, 0));
        ICube cube2 = new Cube(new Position(startPoint, cubeConfig.getHeight()));
        ICube cube3 = new Cube(new Position(startPoint, 2 * cubeConfig.getHeight()));
        ICube cube4 = new Cube(new Position(startPoint, 3 * cubeConfig.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube2, cube4);

        return build(cube3, rim, TetrisType.I);
    }
}
