#!/usr/bin/env python3

import random
import time
from influxdb_client import InfluxDBClient, Point, WritePrecision
from influxdb_client.client.write_api import SYNCHRONOUS

def get_next_reading():
	BASE_READING = 36.8
	return BASE_READING + random.random()


org = "myorg"
bucket = "hellodb"
token = "mShoqCwmjN8x6i4LGWPosl_e8vZztuiHvwM4lu8hs5IsL7KkcAS74fzHsREoUGkqm8f2hoqbKtoOX0W5ZKyveQ=="
client = None

def generate():
    client = InfluxDBClient(url="http://localhost:8086", token=token)
    write_api = client.write_api(write_options=SYNCHRONOUS)

    try:
        while True:
            temperature =  get_next_reading()
            data_point = Point("temperature").field("reading", temperature)
            write_api.write(bucket, org, data_point)
            print(f"Data written successfully : {temperature}")
            time.sleep(5)
    except KeyboardInterrupt as e:
        print("Exiting...")
        client.close()
        raise e

# execute the generate function
generate()
