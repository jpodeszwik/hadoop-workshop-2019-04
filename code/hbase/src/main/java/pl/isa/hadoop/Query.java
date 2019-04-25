package pl.isa.hadoop;

import com.mapr.org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;

@SuppressWarnings("Duplicates")
public class Query {
    public static void main(String[] args) throws IOException {
        String tablePath = args[0];
        TableName tableName = TableName.valueOf(tablePath);
        byte[] columnFamily = org.apache.hadoop.hbase.util.Bytes.toBytes("cf1");

        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);

        Table table = connection.getTable(tableName);

        Filter filter = new SingleColumnValueFilter(columnFamily, Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("Bogdan"));

        Scan scan = new Scan(Bytes.toBytes(""));
        scan.setFilter(filter);

        for (Result result : table.getScanner(scan)) {
            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                System.out.println(Bytes.toString(cell.getValue()));
            }
        }
    }

}
