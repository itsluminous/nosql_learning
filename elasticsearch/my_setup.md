# elasticsearch
docker pull docker.elastic.co/elasticsearch/elasticsearch:8.13.2
docker network create elk
docker run -d --name elasticsearch --net elk -p 9200:9200 -m 1GB -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:8.13.2
### below two steps not needed for http
docker exec -it elasticsearch sh
bin/elasticsearch-reset-password -u elastic -i  # set password to "search"

# kibana
docker pull docker.elastic.co/kibana/kibana:8.13.2
docker run -d --name kibana --net elk -p 5601:5601 -e "SERVER_SSL_ENABLED=false" docker.elastic.co/kibana/kibana:8.13.2
### below step not needed for http
docker exec -it es /usr/share/elasticsearch/bin/elasticsearch-create-enrollment-token -s kibana

# logstash
docker pull docker.elastic.co/logstash/logstash:8.13.2
docker run -d --name logstash --net elk -v /Users/prakashkumar/repo/rdbms_2_nosql/elasticsearch/logstash.conf:/usr/share/logstash/pipeline/logstash.conf -v /Users/prakashkumar/repo/rdbms_2_nosql/datasets/movielens/movielens/:/data/ -v /Users/prakashkumar/repo/rdbms_2_nosql/elasticsearch/ufo/:/ufo/ docker.elastic.co/logstash/logstash:8.13.2