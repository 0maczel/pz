from config_loader import ConfigLoader
from measurements_retriever import MeasurementsRetriever
import datetime

if __name__ == '__main__':
    config_loader = ConfigLoader()
    config_loader.parse_config()
    monitors = config_loader.get_monitors()
    refresh_period = config_loader.get_refresh_period()

    measurements_retriever = MeasurementsRetriever(monitors)
    begin_timestamp = datetime.datetime.utcnow() - datetime.timedelta(0, refresh_period)
    measurements_dict = measurements_retriever.get_measurements(begin_timestamp)

