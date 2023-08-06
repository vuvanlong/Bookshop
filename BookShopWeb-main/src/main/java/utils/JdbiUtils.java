package utils;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class JdbiUtils {
    public static Jdbi createInstance() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(ConstantUtils.SERVER_NAME);
        dataSource.setPortNumber(ConstantUtils.DB_PORT);
        dataSource.setDatabaseName(ConstantUtils.DB_NAME);
        dataSource.setUser(ConstantUtils.DB_USERNAME);
        dataSource.setPassword(ConstantUtils.DB_PASSWORD);
        // Thêm thuộc tính vào URL kết nối
        String additionalProperties = ";encrypt=true;trustServerCertificate=true";
        dataSource.setURL(dataSource.getURL() + additionalProperties);


        Jdbi jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }
}
