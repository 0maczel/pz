import json

from ka_gui import ka_gui

data = json.loads("""
{
    "mon1": {
        "host1": {
            "metric1": 0.4,
            "metric2": 0.2
        },
        "host2": {
            "metric1": 0.2
        }
    },
    "mon2": {
        "host1": {
            "metric1": 0.4,
            "metric2": 0.2
        },
        "host2": {
            "metric2": 0.5
        },
        "super dlugi host": {
            "sad_metric": 12.3,
            "metric2": 0.3
        }
    }
}
""")

if __name__ == '__main__':
    gui = ka_gui()

    print "=== Printing data sorted by default by first metric ==="
    gui.print_data(data)

    print "=== Printing data for specific monitor sorted by default first metric ==="
    gui.print_data(data, "mon2")

    print "=== Printing data sorted by chosen metric ==="
    gui.print_sorted_data(data, "metric2")

    print "=== Printing data for specific monitor sorted by chosen metric==="
    gui.print_sorted_data(data, "metric1", "mon1")
