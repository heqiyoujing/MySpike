package spring.boot.com.configure;

import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MybatisMysql {
    public SqlSessionFactory masterPool;
    private List<SqlSessionFactory> slavePool = new ArrayList();

    public static MybatisMysql getInstance() {
        return new MybatisMysql();
    }

    public MybatisMysql() {
    }

    public SqlSessionFactory getMasterPool() {
        return this.masterPool;
    }

    public void setMasterPool(SqlSessionFactory pool) {
        if (this.masterPool == null) {
            this.masterPool = pool;
        }

    }

    public void setSlavePool(List<SqlSessionFactory> pool) {
        if (this.slavePool.size() <= 0) {
            this.slavePool = pool;
        }

    }

    public List<SqlSessionFactory> getSlavePoolList() {
        return this.slavePool;
    }

    public SqlSessionFactory getSlavePool() {
        int size = this.slavePool.size();
        return size > 0 ? (SqlSessionFactory)this.slavePool.get((new Random()).nextInt(size)) : null;
    }
}
