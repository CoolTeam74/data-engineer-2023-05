import airflow
from airflow import DAG
from airflow.operators.python import PythonOperator
from airflow.operators.postgres_operator import PostgresOperator
from datetime import datetime
import requests

def get_data():
    url = 'http://api.open-notify.org/iss-now.json'
    response = requests.get(url)
    status_code = response.status_code
    if status_code != 200:
        raise ValueError('API connection error status code:', status_code)
    json_data = response.json()
    message = json_data['message']
    if message != 'success':
        raise ValueError('API connection error message:', message)

    latitude = json_data['iss_position']['latitude']
    longitude = json_data['iss_position']['longitude']
    timestamp = json_data['timestamp']
    result = {'ts': timestamp, 'latitude': latitude, 'longitude': longitude, 'message': message}
    return result

with DAG(dag_id="geo_data",
        start_date=datetime(2023, 6,16),
         schedule_interval="*/5 * * * *",
         catchup=False) as dag:

    task_get_data = PythonOperator(task_id='get_data', python_callable=get_data)

    task_connect_postgres = PostgresOperator(task_id='connect_postgres',
                                             postgres_conn_id='postgres_default',
                                             sql="SELECT 1;")

    task_save_postgres = PostgresOperator(task_id='save_postgres',
                                          postgres_conn_id='postgres_default',
                                          sql="""insert into geo_positions (ts, latitude, longitude, message)
                                          values(to_timestamp('{{ti.ecom.pull(key='return_value', task_id='get_data')['ts']}}'),
                                          '{{ti.ecom.pull(key='return_value', task_id='get_data'}['latitude']}}'
                                          '{{ti.ecom.pull(key='return_value', task_id='get_data'}['longitude']}}'
                                          '{{ti.ecom.pull(key='return_value', task_id='get_data'}['message']}}'
                                          """)

    task_get_data >> task_connect_postgres >> task_save_postgres

