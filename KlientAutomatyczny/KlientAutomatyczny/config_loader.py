import ConfigParser
import os

class ConfigLoader(object):
    SECTION = 'KlientAutomatyczny'
    CONFIG_FILENAME = 'ka_config.ini'
    def __init__(self):
        self._config_parser = None
        self._monitors = {}
        self._refresh_period = 5

    def parse_config(self):
        self._config_parser = ConfigParser.ConfigParser()
        try:
            self._config_parser.readfp(open(self.CONFIG_FILENAME, "r"))
            refresh_period = self._config_parser.get(self.SECTION, 'Odswiezanie')
            try:
                refresh_period = int(refresh_period)
            except Exception, e:
                pass
            if isinstance(refresh_period, int) and refresh_period > 5:
                self._refresh_period = refresh_period
            for ka_option in self._config_parser.options(self.SECTION):
                option_uc = ka_option.upper()
                if option_uc.startswith('MONITOR'):
                    self._monitors[option_uc] = self._config_parser.get(self.SECTION, ka_option)
        except IOError, io_e:
            print "Nie mozna znalezc pliku `%s`" % self.CONFIG_FILENAME

    def get_monitors(self):
        return self._monitors

    def get_refresh_period(self):
        return self._refresh_period