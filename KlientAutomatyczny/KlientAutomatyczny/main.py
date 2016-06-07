import os
from time import sleep

from GUI.ka_gui import ka_gui
from config_loader import ConfigLoader
from measurements_retriever import MeasurementsRetriever

import datetime
import pprint
import argparse
import json

if __name__ == '__main__':
    config_loader = ConfigLoader()
    config_loader.parse_config()
    monitors = config_loader.get_monitors()
    refresh_period = config_loader.get_refresh_period()

    parser = argparse.ArgumentParser(description="Wyswietla dane z monitorow")
    parser.add_argument("--sort", help="Nazwa metryki po ktorej maja byc sortowane wyniki", type=str)
    parser.add_argument("--monitor", help="Nazwa monitora dla ktorego maja byc wyswietlone wyniki", type=str)
    args = parser.parse_args()

    measurements_retriever = MeasurementsRetriever(monitors)
    gui = ka_gui()
    while 1:
        begin_timestamp = datetime.datetime.utcnow() - datetime.timedelta(0, refresh_period)
        measurements_dict = measurements_retriever.get_measurements(begin_timestamp)
        monitor = ""
        if args.monitor:
            monitor = args.monitor
        if args.sort:
            gui.print_sorted_data(measurements_dict, args.sort, monitor)
        else:
            gui.print_data(measurements_dict, monitor)
        sleep(refresh_period)
        os.system('cls' if os.name=="nt" else "clear")

