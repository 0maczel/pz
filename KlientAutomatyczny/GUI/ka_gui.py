from log_entries import log_entries
from log_entry import log_entry


class ka_gui:
    MONITOR_ID_HEADER = "Monitor ID"
    HOST_ID_HEADER = "Host ID"

    def print_data(self, data, monitor_id=""):
        metrics_names = self.get_metrics_names(data)
        self.print_sorted_data(data, metrics_names[0], monitor_id)

    def print_sorted_data(self, data, metric_name, monitor_id=""):
        self.print_header(data)
        self.print_sorted_entries(data, metric_name, monitor_id)

    def get_metrics_names(self, data):
        metrics_names = []
        for monitor in data:
            for host in data[monitor]:
                for metric in data[monitor][host]:
                    if metric not in metrics_names:
                        metrics_names.append(metric)
        return metrics_names

    def print_header(self, data):
        metrics_names = self.get_metrics_names(data)
        longest = self.get_longest_name(data)
        self.print_column(self.MONITOR_ID_HEADER, longest)
        self.print_column(self.HOST_ID_HEADER, longest)
        for metric_name in metrics_names:
            self.print_column(metric_name, longest)
        print ""

    def print_column(self, name, longest):
        diff = longest - len(name)
        print name,
        for x in range(0, diff):
            print "",
        print "|",

    def get_longest_name(self, data):
        longest_length = 0
        for monitor in data:
            if longest_length < len(monitor):
                longest_length = len(monitor)
            for host in data[monitor]:
                if longest_length < len(host):
                    longest_length = len(host)
                for metric in data[monitor][host]:
                    if longest_length < len(str(metric)):
                        longest_length = len(metric)
        if longest_length < len(self.MONITOR_ID_HEADER):
            longest_length = len(self.MONITOR_ID_HEADER)
        if longest_length < len(self.HOST_ID_HEADER):
            longest_length = len(self.HOST_ID_HEADER)
        return longest_length

    def print_entries(self, data):
        metrics_names = self.get_metrics_names(data)
        longest = self.get_longest_name(data)
        entries = self.get_log_entries(data).get_entries()
        for entry in entries:
            self.print_entry(entry, metrics_names, longest)

    def print_sorted_entries(self, data, metric_name, monitor_id):
        metrics_names = self.get_metrics_names(data)
        longest = self.get_longest_name(data)
        entries_log = self.get_log_entries(data)
        entries_log.sort_entries(metric_name)
        for entry in entries_log.get_entries():
            if monitor_id != "":
                if entry.monitor_id == monitor_id:
                    self.print_entry(entry, metrics_names, longest)
            else:
                self.print_entry(entry, metrics_names, longest)

    def print_entry(self, entry, metrics_names, longest):
        self.print_column(entry.monitor_id, longest)
        self.print_column(entry.host_id, longest)
        for header in metrics_names:
            if header in entry.metrics:
                self.print_column(str(entry.metrics[header]), longest)
            else:
                self.print_column("-", longest)
        print ""

    def get_log_entries(self, data):
        entries = log_entries()
        for monitor in data:
            for host in data[monitor]:
                entry = log_entry(monitor, host)
                for metric in data[monitor][host]:
                    entry.add_metric(metric, data[monitor][host][metric])
                entries.add_entry(entry)
        return entries


