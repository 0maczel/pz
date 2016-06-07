from itertools import islice


class log_entries:
    def __init__(self):
        self._log_entries = []

    def add_entry(self, entry):
        self._log_entries.append(entry)

    def get_entries(self, given_monitor_id, limit=10):
        if given_monitor_id == '':
            return islice(self._log_entries, limit)
        else:
            return islice([entry for entry in self._log_entries if entry.monitor_id.upper() == given_monitor_id.upper()], limit)

    def sort_entries(self, metric_name):
        self._log_entries = reversed(sorted(self._log_entries, key=lambda e: e.metrics[metric_name] if metric_name in e.metrics else 0))
