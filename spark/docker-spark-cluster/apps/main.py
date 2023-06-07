# This is a sample Python script.
import os
import sys
from datetime import date, datetime
from pyspark.sql.functions import *

from pyspark import SparkConf, SparkContext
from pyspark.sql import SparkSession

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
  os.environ['PYSPARK_PYTHON'] = sys.executable
  os.environ['PYSPARK_DRIVER_PYTHON'] = sys.executable

  spark = SparkSession.builder \
    .master('local[*]') \
    .appName("helloWorld") \
    .getOrCreate()

  print('spark version-=', spark.version)

  sc = spark.sparkContext

  rdd = sc.parallelize([
    (1, 2., 'string1', date(2000, 1, 1), datetime(2000, 1, 1, 12, 0)),
    (2, 3., 'string2', date(2000, 2, 1), datetime(2000, 2, 1, 12, 0)),
    (3, 4., 'string3', date(2000, 3, 1), datetime(2000, 3, 1, 12, 0))
  ])

  for r in rdd.take(2):
    print(r)

  df = spark.createDataFrame([
    (1, 2., 'string1', date(2000, 1, 1), datetime(2000, 1, 1, 12, 0)),
    (2, 3., 'string2', date(2000, 2, 1), datetime(2000, 2, 1, 12, 0)),
    (3, 4., 'string3', date(2000, 3, 1), datetime(2000, 3, 1, 12, 0))
  ], schema='a long, b: double, c string, d date, e timestamp')

  df.show()
  df.printSchema()

  # select c from df
  df.select(col("c")).show()
  df.withColumn('upper_c', upper(df.c)).show()
  df.filter(df.a == 1).show()

  df2 = spark.createDataFrame([
    ['red', 'banana', 1, 10], ['blue', 'banana', 2, 20], ['red', 'banana', 7, 70],
    ['red', 'grape', 4, 40], ['blue', 'grape', 9, 80],
    ['red', 'carrot', 3, 30], ['black', 'carrot', 5, 50], ['red', 'carrot', 6, 60],
  ], schema=['color', 'fruit', 'v1', 'v2'])

  df2.groupby('color').avg().show()

  df2.createOrReplaceTempView("table2")
  spark.sql("select count(*) as count from table2").show()

  lines = spark.read.text('./text.txt')
  print(type(lines))
  counts = lines.withColumn("words", explode(split(col("value"), ' '))).groupby(col('words')).count()
  print(type(counts))
  output = counts.collect()
  print(type(output))
  for (word, count) in output:
    print("%s: %i" % (word, count))

  spark.stop()

