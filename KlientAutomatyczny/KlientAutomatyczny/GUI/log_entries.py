from itertools import islice


class log_entries:
    def __init__(self):
        self.log_entries = []

    def add_entry(self, entry):
        self.log_entries.append(entry)

    def get_entries(self):
        return islice(self.log_entries, 10)

    def sort_entries(self, metric_name):
        self.log_entries = reversed(sorted(self.log_entries, key=lambda e: e.metrics[metric_name] if metric_name in e.metrics else 0))

