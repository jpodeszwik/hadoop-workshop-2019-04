package pl.isa.hadoop;

import com.mapr.org.apache.hadoop.hbase.util.Bytes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

@SuppressWarnings("Duplicates")
public class Insert {
    public static void main(String[] args) throws IOException {
        String tablePath = args[0];
        TableName tableName = TableName.valueOf(tablePath);
        byte[] columnFamily = org.apache.hadoop.hbase.util.Bytes.toBytes("cf1");

        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);

        Table table = connection.getTable(tableName);

        List<Put> batch = new ArrayList<>();
        InputStream is = Insert.class.getResourceAsStream("/owners.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String name = parts[0];
            String account = parts[1];

            byte[] row = Bytes.toBytes(account);

            Put p = new Put(row);
            p.addImmutable(columnFamily, Bytes.toBytes("name"), com.mapr.org.apache.hadoop.hbase.util.Bytes.toBytes(name));
            p.addImmutable(columnFamily, Bytes.toBytes("account"), Bytes.toBytes(account));

            batch.add(p);
        }

        table.put(batch);
    }
}
