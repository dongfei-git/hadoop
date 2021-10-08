package tech.dongfei.hadoop.outputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class LogRecordWriter extends RecordWriter<Text, NullWritable> {

    private FSDataOutputStream baiduOut;
    private FSDataOutputStream otherOut;

    public LogRecordWriter(TaskAttemptContext job) {
        try {
            FileSystem fs = FileSystem.get(job.getConfiguration());
            baiduOut = fs.create(new Path("MapReduceDemo01/output10/baidu.log"));
            otherOut = fs.create(new Path("MapReduceDemo01/output10/other.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        String string = key.toString();
        if (string.contains("baidu")) {
            baiduOut.writeBytes(string + "\n");
        } else {
            otherOut.writeBytes(string + "\n");
        }
    }

    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        IOUtils.closeStream(baiduOut);
        IOUtils.closeStream(otherOut);
    }
}
