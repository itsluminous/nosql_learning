docker pull influxdb:2.7.5
docker pull telegraf:1.30.1
docker-compose up -d

# setup influx-cli
docker exec -it influxdb influx config create --config-name onboarding \
    --host-url "http://localhost:8086" \
    --org "fa413b16ea88e4dc" \
    --token "f_L_mcdW0wU14cxJlWNWkcLN-XIsk80kVvX6Vi4XSzr95ahyb-jDuUvqDlboQo98H9HT4DX2ryoA0lwwo-U1ng==" \
    --active

# query some data
docker exec -it influxdb influx query 'from(bucket:"mybucket") |> range(start:-30m)'

# aggregation
docker exec -it influxdb influx query 'from(bucket:"mybucket") |> range(start:-30m) |> mean()'

# create db
export INFLUX_TOKEN='mShoqCwmjN8x6i4LGWPosl_e8vZztuiHvwM4lu8hs5IsL7KkcAS74fzHsREoUGkqm8f2hoqbKtoOX0W5ZKyveQ=='
docker exec -it influxdb influx write --org myorg --bucket mybucket --token $INFLUX_TOKEN --precision=ns 'create database "hellodb"'

curl -X POST http://localhost:8086/api/v2/setup \
  -H "Authorization: Token $INFLUX_TOKEN" \
  -H "Content-type: application/json" \
  -d '{"buckets":[{"name":"mybucket","retentionPolicy":"72h0m0s","readSource":"influxdb","writePrecision":"ns"}],"org":"myorg"}'

