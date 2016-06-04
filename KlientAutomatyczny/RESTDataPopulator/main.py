import requests
import random
import json
from collections import OrderedDict


class ImitatedSensor(object):
    MONITOR_IP = ''
    SENSOR_ENDPOINT = ''

    def __init__(self, hostname, metric_name):
        self._hostname = hostname
        self._metric_name = metric_name
        pass


resources_url = 'http://127.0.0.1:8080/resources'
sensors_url = 'http://127.0.0.1:8080/sensors'
measurements_postfix = '/measurements'


def register_resources(resources_amount):
    if isinstance(resources_amount, int) and resources_amount > 0:
        resources_count = resources_amount
    else:
        resources_count = 10
    host_names = []
    for i in xrange(resources_count):
        host_names.append("hosts_ddd_%d" % i)

    resources_registration_data = []
    headers = {
        'Content-Type': 'application/json'
    }
    for hostname in host_names:
        requests.post(resources_url, headers=headers, data=json.dumps({'name': hostname}))


def get_all_hosts():
    get_resources_request = requests.get(resources_url)
    all_resources_list = get_resources_request.json()
    resources_mapping_dict = OrderedDict()
    for json_resource_repr in all_resources_list:
        resources_mapping_dict[json_resource_repr['id']] = json_resource_repr['name']
    return resources_mapping_dict


def register_sensors(host_mapping_dict):
    sensor_registration_cpu_metric_json = {
        "metricId": 1,  # id metryki 1 CPU, 2 MEM w testowej bazie
        "resourceId": 1,  # id hosta
    }
    sensor_registration_mem_metric_json = {
        "metricId": 2,  # id metryki 1 CPU, 2 MEM w testowej bazie
        "resourceId": 1,  # id hosta
    }
    for resource_id, resource_name in host_mapping_dict.iteritems():
        headers = {
            'Content-Type': 'application/json'
        }
        sensor_registration_cpu_metric_json['resourceId'] = resource_id
        sensor_registration_mem_metric_json['resourceId'] = resource_id
        requests.post(sensors_url, headers=headers, data=json.dumps(sensor_registration_cpu_metric_json))
        requests.post(sensors_url, headers=headers, data=json.dumps(sensor_registration_mem_metric_json))


def get_all_sensors():
    get_sensors_request = requests.get(sensors_url)
    all_sensors_list = get_sensors_request.json()
    sensors_ids = []
    for json_resource_repr in all_sensors_list:
        sensors_ids.append(json_resource_repr['id'])
    return sensors_ids


def register_new_measurements(sensors_ids):
    sensor_url_template = sensors_url + '/' + '%d' + measurements_postfix
    for sensor_id in sensors_ids:
        headers = {
            'Content-Type': 'application/json'
        }
        requests.post(sensor_url_template % sensor_id, headers=headers, data=json.dumps({'value': random.random()}))


if __name__ == '__main__':
    #
    # register_resources(25)
    # host_mapping_dict = get_all_hosts()
    # register_sensors(host_mapping_dict)

    sensors_ids_list = get_all_sensors()
    register_new_measurements(sensors_ids_list)
