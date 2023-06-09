# This is a sample Python script.
import os
import sys
from datetime import date, datetime
from pyspark.sql.functions import *

from pyspark import SparkConf, SparkContext
from pyspark.sql import SparkSession

import numpy as np
import pandas as pd
import pyarrow as pa
import pyarrow.parquet as pq
from pyarrow import fs
import pyarrow.parquet.encryption as pe

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
  os.environ['PYSPARK_PYTHON'] = sys.executable
  os.environ['PYSPARK_DRIVER_PYTHON'] = sys.executable


  # df = pd.DataFrame({
  #     'one': [-1, np.nan, 2.5],
  #     'two': ['foo', 'bar', 'baz'],
  #     'three': [True, False, True]},
  #     index=list('abc')
  #     )
  #
  # table = pa.Table.from_pandas(df)
  # pq.write_table(table, 'example.parquet')
  # pq.write_to_dataset(table, root_path='/c/dataset_name', partition_cols=['one', 'two'])
  # parquet_file = pq.ParquetFile( 'example.parquet')
  # print(parquet_file.metadata)
  # print(parquet_file.schema)
  #
  # metadata = pq.read_metadata('example.parquet')
  # # print(metadata.row_group[0].column[0])
  # table2 = pq.read_table( 'example.parquet', columns=['one', 'three'])
  #
  # print(table2.to_pandas())
  #
  # s3 = fs.S3FileSystem(region="us-west-1")
  # table = pq.read_table("bucket/object/key/table", filesystem=s3)

  spark = SparkSession.builder \
    .master('local[*]') \
    .appName("helloWorld") \
    .getOrCreate()

  print('spark version-=', spark.version)

  # 1. оставить только непустые значения для названий стран и цен
  # 2. найдем максимальную цену для каждой страны
  # 3. выведем топ-10 стран с самыми дорогими винами (country, max_price)

  winesDf = spark.read.csv(path='./winemag-data-130k-v2.csv', sep=',', schema='id String,country String, description String,designation String,points double,price double,province String, region_1 String, region_2 String, taster_name String,taster_twitter_handle String,title String,variety String,winery String')
  print(type(winesDf))

  winesDf \
    .filter(col('country').isNotNull()) \
    .filter(col('price').isNotNull()) \
    .select(col('country'), col('price')) \
    .groupBy(col('country')) \
    .agg(max(col('price')).alias("max_price")) \
    .orderBy(col('max_price').desc()).show(10)

  # sc = spark.sparkContext
  #
  # rdd = sc.parallelize([
  #     (1, 2., 'string1', date(2000, 1, 1), datetime(2000, 1, 1, 12, 0)),
  #     (2, 3., 'string2', date(2000, 2, 1), datetime(2000, 2, 1, 12, 0)),
  #     (3, 4., 'string3', date(2000, 3, 1), datetime(2000, 3, 1, 12, 0))
  # ])
  #
  # for r in rdd.take(2):
  #     print(r)
  #
  # df = spark.createDataFrame([
  #     (1, 2., 'string1', date(2000, 1, 1), datetime(2000, 1, 1, 12, 0)),
  #     (2, 3., 'string2', date(2000, 2, 1), datetime(2000, 2, 1, 12, 0)),
  #     (3, 4., 'string3', date(2000, 3, 1), datetime(2000, 3, 1, 12, 0))
  # ], schema='a long, b: double, c string, d date, e timestamp')
  #
  # df.show()
  # df.printSchema()
  #
  # # select c from df
  # df.select(col("c")).show()
  # df.withColumn('upper_c', upper(df.c)).show()
  # df.filter(df.a == 1).show()
  #
  df2 = spark.createDataFrame([
    ['red', 'banana', 1, 10], ['blue', 'banana', 2, 20], ['red', 'banana', 7, 70],
    ['red', 'grape', 4, 40], ['blue', 'grape', 9, 80],
    ['red', 'carrot', 3, 30], ['black', 'carrot', 5, 50], ['red', 'carrot', 6, 60],
  ], schema=['color', 'fruit', 'v1', 'v2'])
  #
  # df2.groupby('color').avg().show()
  #
  # df2.createOrReplaceTempView("table2")
  # spark.sql("select count(*) as count from table2").show()
  #
  # lines = spark.read.text('./text.txt')
  # print(type(lines))
  # counts = lines.withColumn("words", explode(split(col("value"), ' '))).groupby(col('words')).count()
  # print(type(counts))
  # output = counts.collect()
  # print(type(output))
  # for (word, count) in output:
  #     print("%s: %i" % (word, count))

  spark.stop()

