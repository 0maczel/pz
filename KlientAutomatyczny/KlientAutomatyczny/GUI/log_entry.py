class log_entry:
    def __init__(self, monitor, host):
        self.monitor_id = monitor
        self.host_id = host
        self.metrics = {}

    def add_metric(self, metric, value):
        self.metrics[metric] = value