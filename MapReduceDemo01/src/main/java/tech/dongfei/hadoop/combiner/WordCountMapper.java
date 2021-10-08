package tech.dongfei.hadoop.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private Text outK = new Text();
    private IntWritable outV = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取一行
        String line = value.toString();
        // 切割
        String[] words = line.split(" ");
        // 循环写出
        for (String word : words) {
            outK.set(word);
            context.write(outK, outV);
        }
    }
}
