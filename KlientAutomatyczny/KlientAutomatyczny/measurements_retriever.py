import requests
from collections import OrderedDict


class MeasurementsRetriever(object):
    MEASUREMENTS_POSTFIX = '/measurements'
    RESOURCES_POSTFIX = '/resources'
    METRICS_POSTFIX = '/metrics'

    def __init__(self, monitors_dict):
        self._monitors_names = monitors_dict.keys()
        self._monitors_ips = monitors_dict.values()

    def get_measurements(self, begin_measurements_datetime):
        begin_timestamp_str = begin_measurements_datetime.strftime('%Y-%m-%d %H:%M:%S GMT')
        tmp_measurements = self._retrieve_last_measurements(begin_timestamp_str)
        tmp_metrics = self._retrieve_metrics()
        tmp_resources = self._retrieve_resources()
        

    def _retrieve_last_measurements(self, begin_timestamp_str):
        temporary_measurements_dict = OrderedDict()
        for i, monitor_ip in enumerate(self._monitors_ips):
            measurements_request = requests.get(monitor_ip + self.MEASUREMENTS_POSTFIX, params={'from-date': begin_timestamp_str})
            temporary_measurements_dict[self._monitors_names[i]] = measurements_request.json()
        return temporary_measurements_dict

    def _retrieve_metrics(self):
        temporary_metrics_dict = OrderedDict()
        for i, monitor_ip in enumerate(self._monitors_ips):
            measurements_request = requests.get(monitor_ip + self.METRICS_POSTFIX)
            temporary_metrics_dict[self._monitors_names[i]] = OrderedDict()
            metrics_definition_list = measurements_request.json()
            for metric_definition in metrics_definition_list:
                temporary_metrics_dict[self._monitors_names[i]][metric_definition['id']] = metric_definition['name']
        return temporary_metrics_dict

    def _retrieve_resources(self):
        temporary_resources_dict = OrderedDict()
        for i, monitor_ip in enumerate(self._monitors_ips):
            resources_request = requests.get(monitor_ip + self.RESOURCES_POSTFIX)
            temporary_resources_dict[self._monitors_names[i]] = OrderedDict()
            resources_definition_list = resources_request.json()
            for resource_definition in resources_definition_list:
                temporary_resources_dict[self._monitors_names[i]][resource_definition['id']] = resource_definition['name']
        return temporary_resources_dict