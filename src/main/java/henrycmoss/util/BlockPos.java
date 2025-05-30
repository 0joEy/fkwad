package henrycmoss.util;

import com.google.common.collect.AbstractIterator;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

public class BlockPos {

    private int x, y, z;

    public BlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPos(Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public BlockPos offset(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? this : new BlockPos(this.x + x, this.y + y, this.z + z);
    }

    public BlockPos offset(Vector v) {
        return offset(v.getBlockX(), v.getBlockY(), v.getBlockZ());
    }

    public BlockPos add(Vector v) {
        return this.fromVector(this.toVector().add(v));
    }
    public BlockPos subtract(Vector v) {
        return this.fromVector(this.toVector().subtract(v));
    }
    public BlockPos multiply(Vector v) {
        return this.fromVector(this.toVector().multiply(v));
    }
    public BlockPos divide(Vector v) {
        return this.fromVector(this.toVector().divide(v));
    }

    public Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }

    public BlockPos fromVector(Vector v) {
        return new BlockPos(v.getBlockX(), v.getBlockY(), v.getBlockZ());
    }

    public Iterable<BlockPos> betweenClosed(int x1, int y1, int z1, int x2, int y2, int z2) {
        int i = x2 - x1 + 1;
        int j = y2 - y1 + 1;
        int k = z2 - z1 + 1;
        int l = i * j * k;

        return () -> new AbstractIterator<>() {

            BlockPos.MutableBlockPos cursor = new MutableBlockPos(0, 0, 0);

            private int index;
            @Nullable
            @Override
            protected BlockPos computeNext() {
                if(index >= l) this.endOfData();

                int i1 = index % i;
                int j1 = index / i;
                int k1 = j1 % j;
                int l1 = j1 / j;
                ++index;

                return cursor.set(x1 + i1, y1 + k1, z1 + l1);
            }
        };
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Iterable<BlockPos> betweenClosed(Location loc1, Location loc2) {
        return this.betweenClosed(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
    }

    public class MutableBlockPos extends BlockPos{

        public MutableBlockPos(int x, int y, int z) {
            super(x, y, z);
        }

        public BlockPos.MutableBlockPos set(int x, int y, int z) {
            setX(x);
            setY(y);
            setZ(z);
            return this;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
