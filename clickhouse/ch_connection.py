import requests
import pandas as pd
from io import StringIO

HOST = 'http://127.0.0.1:8123'


def get_clickhouse_data(query, host=HOST, connection_timeout=1500, **kwagrs):
    params = kwagrs

    r = requests.post(host, params=params, timeout=connection_timeout, data=query)

    if r.status_code == 200:
        return r.text
    else:
        raise ValueError(r.text)

def get_clickhouse_df(query, host=HOST, connection_timeout=1500):
    data = get_clickhouse_data(query, host, connection_timeout)
    df = pd.read_csv(StringIO(data), sep='\t', names=['a', 'b', 'c'])
    return df

query = '''
        SELECT * FROM tutorial.test WHERE a = 'user_5'
         '''


if __name__ == "__main__":
    dataframe = get_clickhouse_df(query, host=HOST, connection_timeout=900)
    print(dataframe.head())
    print(dataframe.shape)
