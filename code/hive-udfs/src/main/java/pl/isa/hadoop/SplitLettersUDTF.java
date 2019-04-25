package pl.isa.hadoop;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;

public class SplitLettersUDTF extends GenericUDTF {
    StringObjectInspector oi;
    StringObjectInspector oi2;

    @Override
    public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {
        oi = (StringObjectInspector) argOIs[0];
        oi2 = (StringObjectInspector) argOIs[1];

        List<ObjectInspector> ois = Lists.newArrayList();
        ois.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        ois.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        List<String> fields = Lists.newArrayList("letter", "field");

        return ObjectInspectorFactory.getStandardStructObjectInspector(fields, ois);
    }

    @Override
    public void process(Object[] objects) throws HiveException {
        String val = oi.getPrimitiveJavaObject(objects[0]);
        String val2 = oi.getPrimitiveJavaObject(objects[1]);

        for (char c : val.toCharArray()) {
            forward(new Object[]{String.valueOf(c), val2});
        }
    }

    @Override
    public void close() throws HiveException {

    }
}
